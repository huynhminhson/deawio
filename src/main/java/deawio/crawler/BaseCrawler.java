package deawio.crawler;

import deawio.mapper.DealModelMapper;
import deawio.mapper.MainMapper;
import deawio.mapper.ProductModelMapper;
import deawio.mapper.StoreModelMapper;
import deawio.model.*;
import deawio.util.MyBatisUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

  public void crawlHtml(HtmlCrawler htmlCrawler, String url, Set<String> processed) {
    // OPEN NEW DB SESSION
    SqlSession session = MyBatisUtil.sqlSessionFactory.openSession(true);

    // INSERT OR UPDATE NEW STORE
    StoreModelExample storeModelExample = new StoreModelExample();
    storeModelExample.or().andNameEqualTo(htmlCrawler.storeName());

    List<StoreModel> storeModels =
        session.getMapper(StoreModelMapper.class).selectByExample(storeModelExample);

    // GET STORE ID
    Integer storeId = null;
    if (storeModels.size() == 0) {
      StoreModel storeModel = new StoreModel();
      storeModel.setName(htmlCrawler.storeName());
      storeModel.setCurrency(htmlCrawler.storeCurrency());

      session.getMapper(StoreModelMapper.class).insertSelective(storeModel);
      storeId = session.getMapper(MainMapper.class).selectLastVal();
    } else {
      storeId = storeModels.get(0).getStoreId();
    }

    // FETCH REMOTE HTML
    String html = null;

    // HTTP CLIENT
    HttpClient httpClient = HttpClientBuilder.create().build();

    // FETCH FIRST PAGE
    HttpGet listingPage = new HttpGet(url);
    listingPage.setHeader(
        "User-Agent",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");

    try {
      html = EntityUtils.toString(httpClient.execute(listingPage).getEntity());
    } catch (IOException e) {
      // RETURN ON FETCH ERROR
      return;
    }

    Elements containers = htmlCrawler.containers(html);
    for (Element container : containers) {
      String productName = htmlCrawler.productName(container);
      String productImageUrl = htmlCrawler.productImageUrl(container, url);

      try {
        Validate.notBlank(productName);
        Validate.notBlank(productImageUrl);
      } catch (Exception e) {
        // MOVE TO NEXT CONTAINER
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

      // INSERT OR UPDATE PRODUCT
      ProductModelExample productModelExample = new ProductModelExample();
      productModelExample.or().andCodeEqualTo(productCode);

      List<ProductModel> productModels =
          session.getMapper(ProductModelMapper.class).selectByExample(productModelExample);

      // GET PRODUCT ID
      Integer productId = null;
      if (productModels.size() == 0) {
        ProductModel productModel = new ProductModel();
        productModel.setName(productName);
        productModel.setCode(productCode);
        productModel.setImageUrl(productImageUrl);

        try {
          session.getMapper(ProductModelMapper.class).insertSelective(productModel);
          productId = session.getMapper(MainMapper.class).selectLastVal();
        } catch (PersistenceException e) {
          // MOVE TO NEXT CONTAINER
          continue;
        }
      } else {
        ProductModel productModel = productModels.get(0);
        productModel.setName(productName);
        productModel.setCode(productCode);
        productModel.setImageUrl(productImageUrl);

        session.getMapper(ProductModelMapper.class).updateByPrimaryKeySelective(productModel);
        productId = productModels.get(0).getProductId();
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
      } else {
        continue;
      }

      // INSERT OR UPDATE DEAL
      DealModelExample dealModelExample = new DealModelExample();
      dealModelExample.or().andProductIdEqualTo(productId).andStoreIdEqualTo(storeId);
      dealModelExample.or().andUrlEqualTo(dealUrl);

      List<DealModel> dealModels =
          session.getMapper(DealModelMapper.class).selectByExample(dealModelExample);

      if (dealModels.isEmpty()) {
        DealModel dealModel = new DealModel();
        dealModel.setUrl(dealUrl);
        dealModel.setLowPrice(dealLowPrice);
        dealModel.setHighPrice(dealHighPrice);
        dealModel.setDiscount(dealDiscount);
        dealModel.setProductId(productId);
        dealModel.setStoreId(storeId);

        session.getMapper(DealModelMapper.class).insertSelective(dealModel);
      } else {
        DealModel dealModel = dealModels.get(0);
        dealModel.setUrl(dealUrl);
        dealModel.setLowPrice(dealLowPrice);
        dealModel.setHighPrice(dealHighPrice);
        dealModel.setDiscount(dealDiscount);
        dealModel.setProductId(productId);
        dealModel.setStoreId(storeId);

        session.getMapper(DealModelMapper.class).updateByPrimaryKeySelective(dealModel);
      }
    }

    // CLOSE DATABASE SESSION
    session.close();

    List<String> paginationUrls = htmlCrawler.paginationUrls(html, url);
    processed.add(url);

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
