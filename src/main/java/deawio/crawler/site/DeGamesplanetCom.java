package deawio.crawler.site;

import deawio.crawler.BaseCrawler;
import deawio.crawler.CssSkeleton;
import deawio.crawler.HtmlCrawler;
import deawio.crawler.SimpleExtracter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DeGamesplanetCom extends UkGamesplanetCom implements HtmlCrawler, Runnable {
  @Autowired private ApplicationContext context;
  @Autowired private BaseCrawler baseCrawler;
  @Autowired private SimpleExtracter simpleExtracter;
  @Autowired private CssSkeleton cssSkeleton;

  @Override
  public String storeName() {
    return "Gamesplanet DE";
  }

  @Override
  public String storeCurrency() {
    return "EUR";
  }

  @Override
  public List<String> startUrls() {
    return Arrays.asList("https://de.gamesplanet.com/games/offers");
  }

  @Override
  public void run() {
    for (String url : startUrls()) {
      baseCrawler.crawlHtml(
          context.getBean("deGamesplanetCom", DeGamesplanetCom.class), url, new HashSet<>());
    }
  }
}
