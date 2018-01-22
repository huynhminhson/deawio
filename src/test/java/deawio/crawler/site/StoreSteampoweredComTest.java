package deawio.crawler.site;

import deawio.config.AppConfig;
import java.io.IOException;
import java.util.List;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
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
  public void testPaginationUrls() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    StoreSteampoweredCom storeSteampoweredCom = new StoreSteampoweredCom();
    List<String> paginationUrls = storeSteampoweredCom.paginationUrls(html, baseUrl);
    assertFalse(paginationUrls.isEmpty());
  }

  @Test
  public void testContainers() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    StoreSteampoweredCom storeSteampoweredCom = new StoreSteampoweredCom();
    Elements containers = storeSteampoweredCom.containers(html);
    assertFalse(containers.isEmpty());
  }

  @Test
  public void testProductName() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    StoreSteampoweredCom storeSteampoweredCom = new StoreSteampoweredCom();
    Element container = storeSteampoweredCom.containers(html).get(0);
    String productName = storeSteampoweredCom.productName(container);
    Validate.notBlank(productName);
  }

  @Test
  public void testProductImageUrl() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    StoreSteampoweredCom storeSteampoweredCom = new StoreSteampoweredCom();
    Element container = storeSteampoweredCom.containers(html).get(0);
    String productImageUrl = storeSteampoweredCom.productImageUrl(container, baseUrl);
    Validate.notBlank(productImageUrl);
  }

  @Test
  public void testDealUrl() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    StoreSteampoweredCom storeSteampoweredCom = new StoreSteampoweredCom();
    Element container = storeSteampoweredCom.containers(html).get(0);
    String dealUrl = storeSteampoweredCom.dealUrl(container, baseUrl);
    Validate.notBlank(dealUrl);
  }

  @Test
  public void testDealHighPrice() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Element container = storeSteampoweredCom.containers(html).get(0);
    Double dealHighPrice = storeSteampoweredCom.dealHighPrice(container);
    Validate.notNull(dealHighPrice);
  }

  @Test
  public void testDealLowPrice() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Element container = storeSteampoweredCom.containers(html).get(0);
    Double dealLowPrice = storeSteampoweredCom.dealLowPrice(container);
    Validate.notNull(dealLowPrice);
  }
}
