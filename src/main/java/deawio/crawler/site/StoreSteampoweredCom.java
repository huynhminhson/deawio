package deawio.crawler.site;

import deawio.crawler.BaseCrawler;
import deawio.crawler.HtmlCrawler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StoreSteampoweredCom implements HtmlCrawler, Runnable {
  @Autowired private ApplicationContext applicationContext;
  @Autowired private BaseCrawler baseCrawler;

  public String storeName() {
    return "Steam";
  }

  public String storeCurrency() {
    return "GBP";
  }

  public List<String> startUrls() {
    return Arrays.asList("http://store.steampowered.com/search/?tags=-1&category1=998");
  }

  public List<String> paginationUrls(String html, String baseUrl) {
    List<String> paginationUrls = new ArrayList<>();
    Elements elements = Jsoup.parse(html).select("div.search_pagination_right a");
    if (elements != null) {
      for (Element element : elements) {
        String href = element.attr("href");
        String joined = null;
        try {
          joined = new URL(new URL(baseUrl), href).toString();
        } catch (MalformedURLException e) {
        }
        try {
          Validate.notBlank(joined);
          if (!paginationUrls.contains(joined)) {
            paginationUrls.add(joined);
          }
        } catch (Exception e) {
        }
      }
    }
    return paginationUrls;
  }

  public Elements containers(String html) {
    return Jsoup.parse(html).select("a.search_result_row");
  }

  public String productName(Element container) {
    return container.select("span.title").first().text();
  }

  public String productImageUrl(Element container, String baseUrl) {
    String url = container.select("img").first().attr("src");
    URL mergedURL = null;
    try {
      return new URL(new URL(baseUrl), url).toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public String dealUrl(Element container, String baseUrl) {
    return container.attr("href");
  }

  public Double dealHighPrice(Element container) {
    String discountString =
        container.select("div.col.search_discount.responsive_secondrow").text().trim();
    if (discountString.equals("")) {
      String priceString =
          container.select("div.col.search_price.responsive_secondrow").first().text().trim();
      return baseCrawler.extractPrice(priceString);
    } else {
      String priceString =
          container
              .select("div.col.search_price.discounted.responsive_secondrow strike")
              .first()
              .text()
              .trim();
      return baseCrawler.extractPrice(priceString);
    }
  }

  public Double dealLowPrice(Element container) {
    String discountString =
        container.select("div.col.search_discount.responsive_secondrow").text().trim();
    if (discountString.equals("")) {
      String priceString =
          container.select("div.col.search_price.responsive_secondrow").first().text().trim();
      return baseCrawler.extractPrice(priceString);
    } else {
      String priceString =
          container
              .select("div.col.search_price.discounted.responsive_secondrow")
              .first()
              .text()
              .trim();
      return baseCrawler.extractPrice(priceString);
    }
  }

  public Double dealDiscount(Element container) {
    return null;
  }

  public void run() {
    StoreSteampoweredCom storeSteampoweredCom =
        applicationContext.getBean("storeSteampoweredCom", StoreSteampoweredCom.class);
    for (String url : storeSteampoweredCom.startUrls()) {
      baseCrawler.crawlHtml(storeSteampoweredCom, url, new HashSet<>());
    }
  }
}
