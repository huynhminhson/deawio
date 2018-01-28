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
public class UkGamesplanetComTest extends TestCase {
  @Autowired private UkGamesplanetCom ukGamesplanetCom;
  private static final String baseUrl = "https://uk.gamesplanet.com/games/offers";
  private static final String sampleHtml = "/samples/uk.gamesplanet.com.html";

  @Test
  public void paginationUrlsWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    List<String> paginationUrls = ukGamesplanetCom.paginationUrls(html, baseUrl);
    assertTrue(paginationUrls.contains("https://uk.gamesplanet.com/games/offers?page=2"));
  }

  @Test
  public void paginationUrlsWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    List<String> paginationUrls = ukGamesplanetCom.paginationUrls(html, baseUrl);
    assertTrue(paginationUrls.isEmpty());
  }

  @Test
  public void containersWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Elements containers = ukGamesplanetCom.containers(html);
    assertFalse(containers.isEmpty());
  }

  @Test
  public void containersWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Elements containers = ukGamesplanetCom.containers(html);
    assertTrue(containers.isEmpty());
  }

  @Test
  public void productNameWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = ukGamesplanetCom.containers(html).get(0);
    String productName = ukGamesplanetCom.productName(container).trim();
    assertEquals(productName, "BioShock Triple Pack");
  }

  @Test
  public void productNameWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String productName = ukGamesplanetCom.productName(container);
    assertNull(productName);
  }

  @Test
  public void productImageUrlWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = ukGamesplanetCom.containers(html).get(0);
    String productImageUrl = ukGamesplanetCom.productImageUrl(container, baseUrl);
    assertEquals(
        productImageUrl,
        "https://gpstatic.com/assets/transparent-bb59255f5b4eae5662e2c25d4fb26c9ac3b808190ae31fbb23538d11a9c2066a.png");
  }

  @Test
  public void productImageUrlWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String productImageUrl = ukGamesplanetCom.productImageUrl(container, baseUrl);
    assertNull(productImageUrl);
  }

  @Test
  public void dealUrlWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = ukGamesplanetCom.containers(html).get(0);
    String dealUrl = ukGamesplanetCom.dealUrl(container, baseUrl);
    assertEquals(dealUrl, "https://uk.gamesplanet.com/game/bioshock-triple-pack-steam-key--1155-7");
  }

  @Test
  public void dealUrlWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String dealUrl = ukGamesplanetCom.dealUrl(container, baseUrl);
    assertNull(dealUrl);
  }

  @Test
  public void dealLowPriceWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = ukGamesplanetCom.containers(html).get(0);
    Double dealLowPrice = ukGamesplanetCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 8.49);

    container = ukGamesplanetCom.containers(html).get(1);
    dealLowPrice = ukGamesplanetCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 6.00);

    container = ukGamesplanetCom.containers(html).get(2);
    dealLowPrice = ukGamesplanetCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 8.49);
  }

  @Test
  public void dealLowPriceWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    Double dealLowPrice = ukGamesplanetCom.dealLowPrice(container);
    assertNull(dealLowPrice);
  }

  @Test
  public void dealDiscountWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = ukGamesplanetCom.containers(html).get(0);
    Double dealDiscount = ukGamesplanetCom.dealDiscount(container);
    assertEquals(dealDiscount, 0.81);

    container = ukGamesplanetCom.containers(html).get(1);
    dealDiscount = ukGamesplanetCom.dealDiscount(container);
    assertEquals(dealDiscount, 0.80);

    container = ukGamesplanetCom.containers(html).get(2);
    dealDiscount = ukGamesplanetCom.dealDiscount(container);
    assertEquals(dealDiscount, 0.74);
  }

  @Test
  public void dealDiscountWithInvalidHtml() throws IOException {
    Element container = Jsoup.parse("<div></div>").select("div").first();
    Double dealDiscount = ukGamesplanetCom.dealDiscount(container);
    assertNull(dealDiscount);
  }
}
