package deawio.crawler;

import deawio.base.BrowserUtil;
import deawio.base.MyBatisUtil;
import deawio.mapper.DealModelMapper;
import deawio.mapper.MainMapper;
import deawio.mapper.ProductModelMapper;
import deawio.mapper.StoreModelMapper;
import deawio.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class BaseCrawler {
  public Double extractPrice(String priceString) {
    if (priceString.toLowerCase().contains("free")) {
      return (double) 0;
    } else {
      for (char ch : priceString.toCharArray()) {
        if (ch == '.' || ch == ',' || Character.isDigit(ch)) {
        } else {
          priceString = priceString.replace(Character.toString(ch), "");
        }
      }
      if (priceString.isEmpty()) {
        return null;
      } else {
        if (priceString.indexOf(",") < priceString.indexOf(".")) {
          priceString = priceString.replace(",", "");
        } else {
          priceString = priceString.replace(".", "");
          priceString = priceString.replace(",", ".");
        }
        try {
          return Double.valueOf(priceString);
        } catch (Exception e) {
          return null;
        }
      }
    }
  }

  public Double extractDiscount(String discountString) {
    for (char ch : discountString.toCharArray()) {
      if (ch == '.' || ch == ',' || Character.isDigit(ch)) {
      } else {
        discountString = discountString.replace(Character.toString(ch), "");
      }
    }
    if (discountString.isEmpty()) {
      return null;
    } else {
      return Double.valueOf(discountString) / 100;
    }
  }

  public Double calcDiscount(double low, double high) {
    if (low > high) {
      return null;
    } else {
      if (high == 0) {
        return (double) 1;
      } else {
        return 1 - (low / high);
      }
    }
  }

  public Double calcHighPrice(double discount, double low) {
    if (discount == 1) {
      if (low == 0) {
        return (double) 0;
      } else {
        return null;
      }
    } else {
      return low / (1 - discount);
    }
  }

  public double calcLowPrice(double discount, double high) {
    return (1 - discount) * high;
  }

  public Integer upsertStore(StoreModel storeModel) {
    // OPEN NEW DB SESSION
    SqlSession session = MyBatisUtil.sqlSessionFactory.openSession(true);

    // INSERT OR UPDATE NEW STORE
    StoreModelExample storeModelExample = new StoreModelExample();
    storeModelExample.or().andNameEqualTo(storeModel.getName());

    List<StoreModel> storeModels =
        session.getMapper(StoreModelMapper.class).selectByExample(storeModelExample);

    // GET STORE ID
    Integer storeId = null;

    if (storeModels.isEmpty()) {
      StoreModel record = new StoreModel();
      record.setName(storeModel.getName());
      record.setCurrency(storeModel.getCurrency());

      try {
        session.getMapper(StoreModelMapper.class).insertSelective(record);
        storeId = session.getMapper(MainMapper.class).selectLastVal();
      } catch (PersistenceException e) {
      }
    } else {
      storeId = storeModels.get(0).getStoreId();
    }

    // CLOSE SESSION
    session.close();

    return storeId;
  }

  public Integer upsertProduct(ProductModel productModel) {
    // DB SESSION
    SqlSession session = MyBatisUtil.sqlSessionFactory.openSession(true);

    // INSERT OR UPDATE PRODUCT
    ProductModelExample productModelExample = new ProductModelExample();
    productModelExample.or().andCodeEqualTo(productModel.getCode());

    List<ProductModel> productModels =
        session.getMapper(ProductModelMapper.class).selectByExample(productModelExample);

    // PRODUCT ID
    Integer productId = null;

    if (productModels.isEmpty()) {
      ProductModel record = new ProductModel();
      record.setName(productModel.getName());
      record.setCode(productModel.getCode());
      record.setImageUrl(productModel.getImageUrl());

      try {
        session.getMapper(ProductModelMapper.class).insertSelective(record);
        productId = session.getMapper(MainMapper.class).selectLastVal();
      } catch (PersistenceException e) {
      }
    } else {
      ProductModel record = productModels.get(0);
      record.setName(productModel.getName());
      record.setCode(productModel.getCode());
      record.setImageUrl(productModel.getImageUrl());

      session.getMapper(ProductModelMapper.class).updateByPrimaryKeySelective(record);
      productId = productModels.get(0).getProductId();
    }

    // CLOSE SESSION
    session.close();

    return productId;
  }

  public void upsertDeal(DealModel dealModel) {
    // DB SESSION
    SqlSession session = MyBatisUtil.sqlSessionFactory.openSession(true);

    // INSERT OR UPDATE DEAL
    DealModelExample dealModelExample = new DealModelExample();
    dealModelExample
        .or()
        .andProductIdEqualTo(dealModel.getProductId())
        .andStoreIdEqualTo(dealModel.getStoreId());
    dealModelExample.or().andUrlEqualTo(dealModel.getUrl());

    List<DealModel> dealModels =
        session.getMapper(DealModelMapper.class).selectByExample(dealModelExample);

    if (dealModels.isEmpty()) {
      DealModel record = new DealModel();
      record.setUrl(dealModel.getUrl());
      record.setLowPrice(dealModel.getLowPrice());
      record.setHighPrice(dealModel.getHighPrice());
      record.setDiscount(dealModel.getDiscount());
      record.setProductId(dealModel.getProductId());
      record.setStoreId(dealModel.getStoreId());

      session.getMapper(DealModelMapper.class).insertSelective(dealModel);
    } else {
      DealModel record = dealModels.get(0);
      record.setUrl(dealModel.getUrl());
      record.setLowPrice(dealModel.getLowPrice());
      record.setHighPrice(dealModel.getHighPrice());
      record.setDiscount(dealModel.getDiscount());
      record.setProductId(dealModel.getProductId());
      record.setStoreId(dealModel.getStoreId());

      session.getMapper(DealModelMapper.class).updateByPrimaryKeySelective(dealModel);
    }

    // CLOSE DB SESSION
    session.close();
  }

  public void crawlHtml(HtmlCrawler htmlCrawler, String url, Set<String> processed) {
    StoreModel storeModel = new StoreModel();
    storeModel.setName(htmlCrawler.storeName());
    storeModel.setCurrency(htmlCrawler.storeCurrency());

    Integer storeId = upsertStore(storeModel);
    if (storeId == null) {
      return;
    }

    // FETCH REMOTE HTML
    WebDriver webDriver = BrowserUtil.webDriver;
    webDriver.get(url);
    String html = webDriver.getPageSource();

    if (html == null || html.isEmpty()) {
      return;
    }

    Elements containers = htmlCrawler.containers(html);
    if (containers == null || containers.isEmpty()) {
      return;
    }

    for (Element container : containers) {
      String productName = htmlCrawler.productName(container);
      String productImageUrl = htmlCrawler.productImageUrl(container, url);

      try {
        Validate.notBlank(productName);
        Validate.notBlank(productImageUrl);
      } catch (Exception e) {
        continue;
      }

      // GENERATE PRODUCT CODE
      String productCode = productName.toLowerCase();
      for (char ch : productCode.toCharArray()) {
        if (Character.isAlphabetic(ch) || Character.isDigit(ch)) {
        } else {
          productCode = productCode.replace(Character.toString(ch), "");
        }
      }

      ProductModel productModel = new ProductModel();
      productModel.setName(productName);
      productModel.setCode(productCode);
      productModel.setImageUrl(productImageUrl);

      Integer productId = upsertProduct(productModel);
      if (productId == null) {
        continue;
      }

      String dealUrl = htmlCrawler.dealUrl(container, url);
      Double dealLowPrice = htmlCrawler.dealLowPrice(container);
      Double dealHighPrice = htmlCrawler.dealHighPrice(container);
      Double dealDiscount = htmlCrawler.dealDiscount(container);

      if (dealLowPrice == null) {
        if (dealHighPrice != null && dealDiscount != null) {
          dealLowPrice = calcLowPrice(dealDiscount, dealHighPrice);
        } else {
          continue;
        }
      } else if (dealHighPrice == null) {
        if (dealDiscount != null && dealLowPrice != null) {
          dealHighPrice = calcHighPrice(dealDiscount, dealLowPrice);
        } else {
          continue;
        }
      } else if (dealDiscount == null) {
        if (dealLowPrice != null && dealHighPrice != null) {
          dealDiscount = calcDiscount(dealLowPrice, dealHighPrice);
        } else {
          continue;
        }
      }

      DealModel dealModel = new DealModel();
      dealModel.setUrl(dealUrl);
      dealModel.setLowPrice(dealLowPrice);
      dealModel.setHighPrice(dealHighPrice);
      dealModel.setDiscount(dealDiscount);
      dealModel.setProductId(productId);
      dealModel.setStoreId(storeId);

      upsertDeal(dealModel);
    }

    processed.add(url);

    List<String> paginationUrls = htmlCrawler.paginationUrls(html, url);
    List<String> nextUrls = new ArrayList<>();
    for (String paginationUrl : paginationUrls) {
      if (!processed.contains(paginationUrl)) {
        nextUrls.add(paginationUrl);
      }
    }
    if (!nextUrls.isEmpty()) {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
      }
      crawlHtml(htmlCrawler, nextUrls.get(0), processed);
    }
  }
}
