package deawio.crawler.site;

import deawio.base.AppConfig;
import java.io.IOException;
import java.util.List;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class DeGamesplanetComTest extends TestCase {
  @Autowired private DeGamesplanetCom deGamesplanetCom;
  private static final String baseUrl = "https://de.gamesplanet.com/games/offers";
  private static final String sampleHtml = "/samples/de.gamesplanet.com.html";

  @Test
  public void paginationUrlsWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    List<String> paginationUrls = deGamesplanetCom.paginationUrls(html, baseUrl);
    assertTrue(paginationUrls.contains("https://de.gamesplanet.com/games/offers?page=2"));
  }

  @Test
  public void paginationUrlsWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    List<String> paginationUrls = deGamesplanetCom.paginationUrls(html, baseUrl);
    assertTrue(paginationUrls.isEmpty());
  }

  @Test
  public void containersWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Elements containers = deGamesplanetCom.containers(html);
    assertFalse(containers.isEmpty());
  }

  @Test
  public void containersWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Elements containers = deGamesplanetCom.containers(html);
    assertTrue(containers.isEmpty());
  }

  @Test
  public void productNameWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = deGamesplanetCom.containers(html).get(0);
    String productName = deGamesplanetCom.productName(container).trim();
    assertEquals(productName, "Handball 16");
  }

  @Test
  public void productNameWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String productName = deGamesplanetCom.productName(container);
    assertNull(productName);
  }

  @Test
  public void productImageUrlWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = deGamesplanetCom.containers(html).get(0);
    String productImageUrl = deGamesplanetCom.productImageUrl(container, baseUrl);
    assertEquals(
        productImageUrl,
        "https://gpstatic.com/assets/transparent-bb59255f5b4eae5662e2c25d4fb26c9ac3b808190ae31fbb23538d11a9c2066a.png");
  }

  @Test
  public void productImageUrlWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String productImageUrl = deGamesplanetCom.productImageUrl(container, baseUrl);
    assertNull(productImageUrl);
  }

  @Test
  public void dealUrlWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = deGamesplanetCom.containers(html).get(0);
    String dealUrl = deGamesplanetCom.dealUrl(container, baseUrl);
    assertEquals(dealUrl, "https://de.gamesplanet.com/game/handball-16-steam-key--2940-1");
  }

  @Test
  public void dealUrlWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String dealUrl = deGamesplanetCom.dealUrl(container, baseUrl);
    assertNull(dealUrl);
  }

  @Test
  public void dealLowPriceWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = deGamesplanetCom.containers(html).get(0);
    Double dealLowPrice = deGamesplanetCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 6.99);

    container = deGamesplanetCom.containers(html).get(1);
    dealLowPrice = deGamesplanetCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 9.99);

    container = deGamesplanetCom.containers(html).get(2);
    dealLowPrice = deGamesplanetCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 12.99);
  }

  @Test
  public void dealLowPriceWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Element container = Jsoup.parse("<div></div>").select("div").first();
    Double dealLowPrice = deGamesplanetCom.dealLowPrice(container);
    assertNull(dealLowPrice);
  }

  @Test
  public void dealDiscountWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = deGamesplanetCom.containers(html).get(0);
    Double dealDiscount = deGamesplanetCom.dealDiscount(container);
    assertEquals(dealDiscount, 0.83);

    container = deGamesplanetCom.containers(html).get(1);
    dealDiscount = deGamesplanetCom.dealDiscount(container);
    assertEquals(dealDiscount, 0.83);

    container = deGamesplanetCom.containers(html).get(2);
    dealDiscount = deGamesplanetCom.dealDiscount(container);
    assertEquals(dealDiscount, 0.78);
  }

  @Test
  public void dealDiscountWithInvalidHtml() throws IOException {
    Element container = Jsoup.parse("<div></div>").select("div").first();
    Double dealDiscount = deGamesplanetCom.dealDiscount(container);
    assertNull(dealDiscount);
  }
}
