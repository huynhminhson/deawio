package deawio.crawler;

import deawio.model.*;
import java.util.List;
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

  public String dealUrl(Element container, String baseUrl);

  public Double dealHighPrice(Element container);

  public Double dealLowPrice(Element container);

  public Double dealDiscount(Element container);
}
