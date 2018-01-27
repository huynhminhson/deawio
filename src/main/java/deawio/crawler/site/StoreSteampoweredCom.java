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
public class StoreSteampoweredCom implements HtmlCrawler, Runnable {
  @Autowired private ApplicationContext context;
  @Autowired private BaseCrawler baseCrawler;
  @Autowired private SimpleExtracter simpleExtracter;
  @Autowired private CssSkeleton cssSkeleton;

  @Override
  public String storeName() {
    return "Steam";
  }

  @Override
  public String storeCurrency() {
    return "GBP";
  }

  @Override
  public List<String> startUrls() {
    return Arrays.asList("http://store.steampowered.com/search/?tags=-1&category1=998");
  }

  @Override
  public List<String> paginationUrls(String html, String baseUrl) {
    return simpleExtracter.urls(html, "div.search_pagination_right a", "href", baseUrl);
  }

  @Override
  public Elements containers(String html) {
    return Jsoup.parse(html).select("a.search_result_row");
  }

  @Override
  public String productName(Element container) {
    List<String> texts = simpleExtracter.texts(container, "span.title");
    if (texts.isEmpty()) {
      return null;
    } else {
      return texts.get(0);
    }
  }

  @Override
  public String productImageUrl(Element container, String baseUrl) {
    List<String> urls = simpleExtracter.urls(container, "img", "src", baseUrl);
    if (urls.isEmpty()) {
      return null;
    } else {
      return urls.get(0);
    }
  }

  @Override
  public String dealUrl(Element container, String baseUrl) {
    List<String> urls = simpleExtracter.urls(container, "a", "href", baseUrl);
    if (urls.isEmpty()) {
      return null;
    } else {
      return urls.get(0);
    }
  }

  @Override
  public Double dealHighPrice(Element container) {
    List<String> discountStrings =
        simpleExtracter.texts(container, "div.col.search_discount.responsive_secondrow");
    if (discountStrings.isEmpty()) {
      return null;
    } else {
      if (discountStrings.get(0).isEmpty()) {
        List<String> priceStrings =
            simpleExtracter.texts(container, "div.col.search_price.responsive_secondrow");
        if (priceStrings.isEmpty()) {
          return null;
        } else {
          return baseCrawler.extractPrice(priceStrings.get(0));
        }
      } else {
        List<String> priceStrings =
            simpleExtracter.texts(
                container, "div.col.search_price.discounted.responsive_secondrow strike");
        if (priceStrings.isEmpty()) {
          return null;
        } else {
          return baseCrawler.extractPrice(priceStrings.get(0));
        }
      }
    }
  }

  @Override
  public Double dealLowPrice(Element container) {
    List<String> discountStrings =
        simpleExtracter.texts(container, "div.col.search_discount.responsive_secondrow");
    if (discountStrings.isEmpty()) {
      return null;
    } else {
      if (discountStrings.get(0).isEmpty()) {
        List<String> priceStrings =
            simpleExtracter.texts(container, "div.col.search_price.responsive_secondrow");
        if (priceStrings.isEmpty()) {
          return null;
        } else {
          return baseCrawler.extractPrice(priceStrings.get(0));
        }
      } else {
        List<String> priceStrings =
            simpleExtracter.texts(
                container, "div.col.search_price.discounted.responsive_secondrow");
        if (priceStrings.isEmpty()) {
          return null;
        } else {
          String[] chunks = priceStrings.get(0).split("£");
          return baseCrawler.extractPrice(chunks[chunks.length - 1]);
        }
      }
    }
  }

  @Override
  public Double dealDiscount(Element container) {
    return null;
  }

  @Override
  public void run() {
    StoreSteampoweredCom storeSteampoweredCom =
        context.getBean("storeSteampoweredCom", StoreSteampoweredCom.class);
    for (String url : storeSteampoweredCom.startUrls()) {
      baseCrawler.crawlHtml(storeSteampoweredCom, url, new HashSet<>());
    }
  }
}
