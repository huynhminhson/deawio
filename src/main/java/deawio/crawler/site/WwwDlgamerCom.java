package deawio.crawler.site;

import deawio.crawler.BaseCrawler;
import deawio.crawler.CssSkeleton;
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
public class WwwDlgamerCom implements HtmlCrawler, Runnable {
  @Autowired private ApplicationContext context;
  @Autowired private BaseCrawler baseCrawler;
  @Autowired private SimpleExtracter simpleExtracter;
  @Autowired private CssSkeleton cssSkeleton;

  @Override
  public String storeName() {
    return "DLGAMER";
  }

  @Override
  public String storeCurrency() {
    return "GBP";
  }

  @Override
  public List<String> startUrls() {
    return Arrays.asList("https://www.dlgamer.com/en/search?keywords=");
  }

  @Override
  public List<String> paginationUrls(String html, String baseUrl) {
    return simpleExtracter.urls(html, "div.pagination-area a", "href", baseUrl);
  }

  @Override
  public Elements containers(String html) {
    return Jsoup.parse(html).select("div.product-card");
  }

  @Override
  public String productName(Element container) {
    List<String> texts = simpleExtracter.texts(container, "a.product-card-title");
    if (texts.isEmpty()) {
      return null;
    } else {
      return texts.get(0);
    }
  }

  @Override
  public String productImageUrl(Element container, String baseUrl) {
    List<String> urls = simpleExtracter.urls(container, "img.product-card-cover", "src", baseUrl);
    if (urls.isEmpty()) {
      return null;
    } else {
      return urls.get(0);
    }
  }

  @Override
  public String dealUrl(Element container, String baseUrl) {
    List<String> urls = simpleExtracter.urls(container, "a.product-card-title", "href", baseUrl);
    if (urls.isEmpty()) {
      return null;
    } else {
      return urls.get(0);
    }
  }

  @Override
  public Double dealHighPrice(Element container) {
    return null;
  }

  @Override
  public Double dealLowPrice(Element container) {
    List<String> priceStrings = simpleExtracter.texts(container, "span.product-card-price");
    if (priceStrings.isEmpty()) {
      return null;
    } else {
      return baseCrawler.extractPrice(priceStrings.get(0));
    }
  }

  @Override
  public Double dealDiscount(Element container) {
    List<String> discountStrings = simpleExtracter.texts(container, "span.product-card-percent");
    if (discountStrings.isEmpty()) {
      return null;
    } else {
      return baseCrawler.extractDiscount(discountStrings.get(0));
    }
  }

  @Override
  public void run() {
    for (String url : startUrls()) {
      baseCrawler.crawlHtml(
          context.getBean("wwwDlgamerCom", WwwDlgamerCom.class), url, new HashSet<>());
    }
  }
}
