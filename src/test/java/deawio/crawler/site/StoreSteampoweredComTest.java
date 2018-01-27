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
public class StoreSteampoweredComTest extends TestCase {
  @Autowired private StoreSteampoweredCom storeSteampoweredCom;
  private static String baseUrl = "http://store.steampowered.com/search/?tags=-1&category1=998";

  @Test
  public void paginationUrlsWithValidHtml() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    List<String> paginationUrls = storeSteampoweredCom.paginationUrls(html, baseUrl);
    assertTrue(
        paginationUrls.contains(
            "http://store.steampowered.com/search/?sort_by=&sort_order=0&category1=998&special_categories=&tags=-1&page=2"));
  }

  @Test
  public void paginationUrlsWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    List<String> paginationUrls = storeSteampoweredCom.paginationUrls(html, baseUrl);
    assertTrue(paginationUrls.isEmpty());
  }

  @Test
  public void containersWithValidHtml() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Elements containers = storeSteampoweredCom.containers(html);
    assertFalse(containers.isEmpty());
  }

  @Test
  public void containersWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Elements containers = storeSteampoweredCom.containers(html);
    assertTrue(containers.isEmpty());
  }

  @Test
  public void productNameWithValidHtml() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Element container = storeSteampoweredCom.containers(html).get(0);
    String productName = storeSteampoweredCom.productName(container);
    assertEquals(productName, "Counter-Strike: Global Offensive");
  }

  @Test
  public void productNameWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String productName = storeSteampoweredCom.productName(container);
    assertNull(productName);
  }

  @Test
  public void productImageUrlWithValidHtml() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Element container = storeSteampoweredCom.containers(html).get(0);
    String productImageUrl = storeSteampoweredCom.productImageUrl(container, baseUrl);
    assertEquals(
        productImageUrl,
        "http://cdn.edgecast.steamstatic.com/steam/apps/730/capsule_sm_120.jpg?t=1513742714");
  }

  @Test
  public void productImageUrlWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String productImageUrl = storeSteampoweredCom.productImageUrl(container, baseUrl);
    assertNull(productImageUrl);
  }

  @Test
  public void dealUrlWithValidHtml() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Element container = storeSteampoweredCom.containers(html).get(0);
    String dealUrl = storeSteampoweredCom.dealUrl(container, baseUrl);
    assertEquals(
        dealUrl,
        "http://store.steampowered.com/app/730/CounterStrike_Global_Offensive/?snr=1_7_7_230_150_1");
  }

  @Test
  public void dealUrlWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String dealUrl = storeSteampoweredCom.dealUrl(container, baseUrl);
    assertNull(dealUrl);
  }

  @Test
  public void dealHighPriceWithValidHtml() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Element container = storeSteampoweredCom.containers(html).get(0);
    Double dealHighPrice = storeSteampoweredCom.dealHighPrice(container);
    assertEquals(dealHighPrice, 11.99);

    container = storeSteampoweredCom.containers(html).get(1);
    dealHighPrice = storeSteampoweredCom.dealHighPrice(container);
    assertEquals(dealHighPrice, 26.99);

    container = storeSteampoweredCom.containers(html).get(2);
    dealHighPrice = storeSteampoweredCom.dealHighPrice(container);
    assertEquals(dealHighPrice, 23.99);
  }

  @Test
  public void dealHighPriceWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    Double dealHighPrice = storeSteampoweredCom.dealHighPrice(container);
    assertNull(dealHighPrice);
  }

  @Test
  public void dealLowPriceWithValidHtml() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Element container = storeSteampoweredCom.containers(html).get(0);
    Double dealLowPrice = storeSteampoweredCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 11.99);

    container = storeSteampoweredCom.containers(html).get(1);
    dealLowPrice = storeSteampoweredCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 26.99);

    container = storeSteampoweredCom.containers(html).get(2);
    dealLowPrice = storeSteampoweredCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 7.91);
  }

  @Test
  public void dealLowPriceWithInvalidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream("/samples/github.com.html"));
    Element container = Jsoup.parse("<div></div>").select("div").first();
    Double dealLowPrice = storeSteampoweredCom.dealLowPrice(container);
    assertNull(dealLowPrice);
  }
}
