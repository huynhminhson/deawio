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
public class WwwDlgamerComTest extends TestCase {
  @Autowired private WwwDlgamerCom wwwDlgamerCom;
  private static String baseUrl = "https://www.dlgamer.com/en/search?keywords=";
  private static String sampleHtml = "/samples/www.dlgamer.com.html";

  @Test
  public void paginationUrlsWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    List<String> paginationUrls = wwwDlgamerCom.paginationUrls(html, baseUrl);
    assertTrue(
        paginationUrls.contains(
            "https://www.dlgamer.com/en/search?keywords=&sortby=release%2B&discount_min=0&page=2"));
  }

  @Test
  public void paginationUrlsWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    List<String> paginationUrls = wwwDlgamerCom.paginationUrls(html, baseUrl);
    assertTrue(paginationUrls.isEmpty());
  }

  @Test
  public void containersWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Elements containers = wwwDlgamerCom.containers(html);
    assertFalse(containers.isEmpty());
  }

  @Test
  public void containersWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Elements containers = wwwDlgamerCom.containers(html);
    assertTrue(containers.isEmpty());
  }

  @Test
  public void productNameWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = wwwDlgamerCom.containers(html).get(0);
    String productName = wwwDlgamerCom.productName(container);
    assertEquals(productName, "Pillars of Eternity II: Deadfire - Obsidian Edition");
  }

  @Test
  public void productNameWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String productName = wwwDlgamerCom.productName(container);
    assertNull(productName);
  }

  @Test
  public void productImageUrlWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = wwwDlgamerCom.containers(html).get(0);
    String productImageUrl = wwwDlgamerCom.productImageUrl(container, baseUrl);
    assertEquals(
        productImageUrl,
        "https://static.dlgamer.com/assets/458/90/packshots/pillars_of_eternity_ii_deadfire_obsidian_edition.jpg");
  }

  @Test
  public void productImageUrlWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String productImageUrl = wwwDlgamerCom.productImageUrl(container, baseUrl);
    assertNull(productImageUrl);
  }

  @Test
  public void dealUrlWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = wwwDlgamerCom.containers(html).get(0);
    String dealUrl = wwwDlgamerCom.dealUrl(container, baseUrl);
    assertEquals(
        dealUrl,
        "https://www.dlgamer.com/en/games/buy-pillars-of-eternity-ii-deadfire-obsidian-45890");
  }

  @Test
  public void dealUrlWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Element container = Jsoup.parse("<div></div>").select("div").first();
    String dealUrl = wwwDlgamerCom.dealUrl(container, baseUrl);
    assertNull(dealUrl);
  }

  @Test
  public void dealLowPriceWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = wwwDlgamerCom.containers(html).get(0);
    Double dealLowPrice = wwwDlgamerCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 55.09);

    container = wwwDlgamerCom.containers(html).get(1);
    dealLowPrice = wwwDlgamerCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 44.64);

    container = wwwDlgamerCom.containers(html).get(2);
    dealLowPrice = wwwDlgamerCom.dealLowPrice(container);
    assertEquals(dealLowPrice, 40.49);
  }

  @Test
  public void dealLowPriceWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Element container = Jsoup.parse("<div></div>").select("div").first();
    Double dealLowPrice = wwwDlgamerCom.dealLowPrice(container);
    assertNull(dealLowPrice);
  }

  @Test
  public void dealDiscountWithValidHtml() throws IOException {
    String html = IOUtils.toString(this.getClass().getResourceAsStream(sampleHtml));
    Element container = wwwDlgamerCom.containers(html).get(0);
    Double dealDiscount = wwwDlgamerCom.dealDiscount(container);
    assertEquals(dealDiscount, 0.05);

    container = wwwDlgamerCom.containers(html).get(1);
    dealDiscount = wwwDlgamerCom.dealDiscount(container);
    assertEquals(dealDiscount, 0.05);

    container = wwwDlgamerCom.containers(html).get(2);
    dealDiscount = wwwDlgamerCom.dealDiscount(container);
    assertEquals(dealDiscount, 0.1);
  }

  @Test
  public void dealDiscountWithInvalidHtml() throws IOException {
    String html = "<div></div>";
    Element container = Jsoup.parse("<div></div>").select("div").first();
    Double dealDiscount = wwwDlgamerCom.dealDiscount(container);
    assertNull(dealDiscount);
  }
}
