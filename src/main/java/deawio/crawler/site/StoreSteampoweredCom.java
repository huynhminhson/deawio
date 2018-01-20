package deawio.crawler.site;

import deawio.crawler.BaseCrawler;
import deawio.crawler.HtmlCrawler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StoreSteampoweredCom implements HtmlCrawler, Runnable {
  @Autowired public ApplicationContext applicationContext;
  @Autowired public BaseCrawler baseCrawler;

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
    List<String> result = new ArrayList<>();
    Elements elements = Jsoup.parse(html).select("div.pagination a");
    for (Element e : elements) {
      if (e.attr("href") != null) {
        result.add(e.attr("href"));
      }
    }
    return result;
  }

  public Elements containers(String html) {
    return Jsoup.parse(html).select("");
  }

  public String productName(Element container) {
    return container.select("").first().text();
  }

  public String productImageUrl(Element container, String baseUrl) {
    return container.select("").first().attr("src");
  }

  public String dealUrl(Element container) {
    return container.select("").first().attr("src");
  }

  public Double dealHighPrice(Element container) {
    Elements check = container.select("div.col.search_discount.responsive_secondrow");
    if (check == null) {
      String priceString =
          container.select("div.col.search_price.responsive_secondrow").first().text();
      return baseCrawler.extractPrice(priceString);
    } else {
      String priceString =
          container
              .select("div.col.search_price.discounted.responsive_secondrow strike")
              .first()
              .text();
      return baseCrawler.extractPrice(priceString);
    }
  }

  public Double dealLowPrice(Element container) {
    Elements check = container.select("div.col.search_discount.responsive_secondrow");
    if (check == null) {
      String priceString =
          container.select("div.col.search_price.responsive_secondrow").first().text();
      return baseCrawler.extractPrice(priceString);
    } else {
      String priceString =
          container.select("div.col.search_price.discounted.responsive_secondrow").first().text();
      return baseCrawler.extractPrice(priceString);
    }
  }

  public Double dealDiscount(Element container) {
    return null;
  }

  public void run() {
    StoreSteampoweredCom storeSteampoweredCom =
        (StoreSteampoweredCom) applicationContext.getBean("storeSteampoweredCom");
    for (String url : storeSteampoweredCom.startUrls()) {
      baseCrawler.crawlHtml(storeSteampoweredCom, url, new HashSet<>());
    }
  }
}
