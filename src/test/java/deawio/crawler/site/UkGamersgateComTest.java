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
public class UkGamersgateComTest extends TestCase {
  @Autowired private UkGamersgateCom ukGamersgateCom;
  private static final String baseUrl = "https://uk.gamersgate.com/games?state=available";
  private static final String sampleHtml = "/samples/uk.gamersgate.com.html";

  @Test
  public void paginationUrlsWithValidHtml() throws IOException {
    String html =
        IOUtils.toString(this.getClass().getResourceAsStream("/samples/uk.gamersgate.com.html"));
    List<String> paginationUrls = ukGamersgateCom.paginationUrls(html, baseUrl);
    assertTrue(paginationUrls.contains("https://uk.gamersgate.com/games?state=available&pg=2"));
  }

  @Test
  public void paginationUrlsWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    List<String> paginationUrls = ukGamersgateCom.paginationUrls(html, baseUrl);
    assertTrue(paginationUrls.isEmpty());
  }

  @Test
  public void containersWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Elements containers = ukGamersgateCom.containers(html);
    assertFalse(containers.isEmpty());
  }

  @Test
  public void containersWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Elements containers = ukGamersgateCom.containers(html);
    assertTrue(containers.isEmpty());
  }

  @Test
  public void productNameWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = ukGamersgateCom.containers(html).get(0);
    String productName = ukGamersgateCom.productName(container).trim();
    assertEquals(productName, "Dragon Ball FighterZ – FighterZ Edition ");
  }

  @Test
  public void productNameWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String productName = ukGamersgateCom.productName(container);
    assertNull(productName);
  }

  @Test
  public void productImageUrlWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = ukGamersgateCom.containers(html).get(0);
    String productImageUrl = ukGamersgateCom.productImageUrl(container, baseUrl);
    assertEquals(
        productImageUrl,
        "https://static.gamersgate.com/media/products/profile/129344/180_259.jpg/w122/");
  }

  @Test
  public void productImageUrlWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String productImageUrl = ukGamersgateCom.productImageUrl(container, baseUrl);
    assertNull(productImageUrl);
  }

  @Test
  public void dealUrlWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = ukGamersgateCom.containers(html).get(0);
    String dealUrl = ukGamersgateCom.dealUrl(container, baseUrl);
    assertEquals(
        dealUrl,
        "https://uk.gamersgate.com/DD-DRAGON-BALL-FIGHTER-Z-FIGHTERZ-EDITION/dragon-bal-fighterz-fighterz-edition");
  }

  @Test
  public void dealUrlWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String dealUrl = ukGamersgateCom.dealUrl(container, baseUrl);
    assertNull(dealUrl);
  }

  @Test
  public void dealHighPriceWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = ukGamersgateCom.containers(html).get(0);
    Double dealHighPrice = ukGamersgateCom.dealHighPrice(container);
    assertEquals(dealHighPrice, 74.98);

    container = ukGamersgateCom.containers(html).get(1);
    dealHighPrice = ukGamersgateCom.dealHighPrice(container);
    assertEquals(dealHighPrice, 87.97);

    container = ukGamersgateCom.containers(html).get(2);
    dealHighPrice = ukGamersgateCom.dealHighPrice(container);
    assertEquals(dealHighPrice, 39.99);

    container = ukGamersgateCom.containers(html).get(3);
    dealHighPrice = ukGamersgateCom.dealHighPrice(container);
    assertEquals(dealHighPrice, 3.99);
  }

  @Test
  public void dealHighPriceWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    Double dealHighPrice = ukGamersgateCom.dealHighPrice(container);
    assertNull(dealHighPrice);
  }

  @Test
  public void dealLowPriceWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = ukGamersgateCom.containers(html).get(0);
    Double dealLowPrice = ukGamersgateCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 74.98);

    container = ukGamersgateCom.containers(html).get(1);
    dealLowPrice = ukGamersgateCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 87.97);

    container = ukGamersgateCom.containers(html).get(2);
    dealLowPrice = ukGamersgateCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 39.99);

    container = ukGamersgateCom.containers(html).get(3);
    dealLowPrice = ukGamersgateCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 3.19);
  }

  @Test
  public void dealLowPriceWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    Double dealLowPrice = ukGamersgateCom.dealLowPrice(container);
    assertNull(dealLowPrice);
  }
}
