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

public interface HtmlCrawler {
  public String storeName();

  public String storeCurrency();

  public List<String> startUrls();

  public Elements containers(String html);

  public List<String> paginationUrls(String html, String baseUrl);

  public String productName(Element container);

  public String productImageUrl(Element container, String baseUrl);

  public String dealUrl(Element container);

  public Double dealHighPrice(Element container);

  public Double dealLowPrice(Element container);

  public Double dealDiscount(Element container);

  public default void crawl(String url, Set<String> processed) {
    // OPEN NEW DB SESSION
    SqlSession session = MyBatisUtil.sqlSessionFactory.openSession(true);

    // INSERT OR UPDATE NEW STORE
    StoreModelExample storeModelExample = new StoreModelExample();
    storeModelExample.or().andNameEqualTo(storeName());

    List<StoreModel> storeModels =
        session.getMapper(StoreModelMapper.class).selectByExample(storeModelExample);

    // GET STORE ID
    Integer storeId = null;
    if (storeModels.size() == 0) {
      StoreModel storeModel = new StoreModel();
      storeModel.setName(storeName());
      storeModel.setCurrency(storeCurrency());

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

    Elements containers = containers(html);
    for (Element container : containers) {
      String productName = productName(container);
      String productImageUrl = productImageUrl(container, url);

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
          break;
        }
      } else {
        ProductModel productModel = productModels.get(0);
        productModel.setName(productName);
        productModel.setCode(productCode);
        productModel.setImageUrl(productImageUrl);

        session.getMapper(ProductModelMapper.class).updateByPrimaryKeySelective(productModel);
        productId = productModels.get(0).getProductId();
      }

      String dealUrl = dealUrl(container);
      Double dealLowPrice = dealLowPrice(container);
      Double dealHighPrice = dealHighPrice(container);
      Double dealDiscount = dealDiscount(container);

      if (dealLowPrice != null) {
        if (dealHighPrice != null) {
          dealDiscount = BaseCrawler.calcDiscount(dealLowPrice, dealHighPrice);
        } else {
          dealHighPrice = BaseCrawler.calcHighPrice(dealDiscount, dealLowPrice);
        }
      } else {
        dealLowPrice = BaseCrawler.calcLowPrice(dealDiscount, dealHighPrice);
      }

      // INSERT OR UPDATE DEAL
      DealModelExample dealModelExample = new DealModelExample();
      dealModelExample.or().andUrlEqualTo(dealUrl);

      List<DealModel> dealModels =
          session.getMapper(DealModelMapper.class).selectByExample(dealModelExample);

      if (dealModels.size() == 0) {
        DealModel dealModel = new DealModel();
        dealModel.setUrl(dealUrl);
        dealModel.setLowPrice(dealLowPrice);
        dealModel.setHighPrice(dealHighPrice);
        dealModel.setDiscount(dealDiscount);

        try {
          session.getMapper(DealModelMapper.class).insertSelective(dealModel);
        } catch (PersistenceException e) {
        }
      } else {
        DealModel dealModel = dealModels.get(0);
        dealModel.setLowPrice(dealLowPrice);
        dealModel.setHighPrice(dealHighPrice);
        dealModel.setDiscount(dealDiscount);

        session.getMapper(DealModelMapper.class).updateByPrimaryKeySelective(dealModel);
      }
    }

    // CLOSE DATABASE SESSION
    session.close();

    List<String> paginationUrls = paginationUrls(html, url);
    processed.add(url);

    List<String> nextUrls = new ArrayList<>();
    for (String paginationUrl : paginationUrls) {
      if (!processed.contains(paginationUrl)) {
        nextUrls.add(paginationUrl);
      }
    }
    if (nextUrls.size() > 0) {
      crawl(nextUrls.get(0), processed);
    }
  }
}
