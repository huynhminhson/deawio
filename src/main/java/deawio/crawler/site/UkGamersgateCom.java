package deawio.crawler.site;

import deawio.crawler.BaseCrawler;
import deawio.crawler.HtmlCrawler;
import deawio.crawler.SimpleExtracter;
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
public class UkGamersgateCom implements HtmlCrawler, Runnable {
  @Autowired private ApplicationContext context;
  @Autowired private BaseCrawler baseCrawler;
  @Autowired private SimpleExtracter simpleExtracter;

  @Override
  public String storeName() {
    return "GamersGate";
  }

  @Override
  public String storeCurrency() {
    return "GBP";
  }

  @Override
  public List<String> startUrls() {
    return Arrays.asList("https://uk.gamersgate.com/games?state=available");
  }

  @Override
  public List<String> paginationUrls(String html, String baseUrl) {
    return simpleExtracter.urls(html, "div.paginator a", "href", baseUrl);
  }

  @Override
  public Elements containers(String html) {
    return Jsoup.parse(html).select("div.product_display");
  }

  @Override
  public String productName(Element container) {
    List<String> texts = simpleExtracter.texts(container, "a.ttl");
    if (texts.isEmpty()) {
      return null;
    } else {
      return texts.get(0);
    }
  }

  @Override
  public String productImageUrl(Element container, String baseUrl) {
    List<String> urls = simpleExtracter.urls(container, "div.box_cont a img", "src", baseUrl);
    if (urls.isEmpty()) {
      return null;
    } else {
      return urls.get(0);
    }
  }

  @Override
  public String dealUrl(Element container, String baseUrl) {
    List<String> urls = simpleExtracter.urls(container, "a.ttl", "href", baseUrl);
    if (urls.isEmpty()) {
      return null;
    } else {
      return urls.get(0);
    }
  }

  @Override
  public Double dealHighPrice(Element container) {
    List<String> check = simpleExtracter.texts(container, "div.grid-discount-perc");
    if (check.isEmpty()) {
      List<String> priceStrings = simpleExtracter.texts(container, "span.prtag");
      if (priceStrings.isEmpty()) {
        return null;
      } else {
        return baseCrawler.extractPrice(priceStrings.get(0));
      }
    } else {
      List<String> priceStrings = simpleExtracter.texts(container, "div.grid-old-price");
      if (priceStrings.isEmpty()) {
        return null;
      } else {
        return baseCrawler.extractPrice(priceStrings.get(0));
      }
    }
  }

  @Override
  public Double dealLowPrice(Element container) {
    List<String> check = simpleExtracter.texts(container, "div.grid-discount-perc");
    if (check.isEmpty()) {
      List<String> priceStrings = simpleExtracter.texts(container, "span.prtag");
      if (priceStrings.isEmpty()) {
        return null;
      } else {
        return baseCrawler.extractPrice(priceStrings.get(0));
      }
    } else {
      List<String> priceStrings = simpleExtracter.texts(container, "span.prtag.redbg");
      if (priceStrings.isEmpty()) {
        return null;
      } else {
        return baseCrawler.extractPrice(priceStrings.get(0));
      }
    }
  }

  @Override
  public Double dealDiscount(Element container) {
    return null;
  }

  @Override
  public void run() {
    UkGamersgateCom ukGamersgateCom = context.getBean("ukGamersgateCom", UkGamersgateCom.class);
    for (String url : ukGamersgateCom.startUrls()) {
      baseCrawler.crawlHtml(ukGamersgateCom, url, new HashSet<>());
    }
  }
}
