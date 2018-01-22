package deawio.extracter;

import deawio.config.AppConfig;
import java.io.IOException;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SimpleExtracterTest extends TestCase {
  @Autowired SimpleExtracter simpleExtracter;
  private static String baseUrl = "http://store.steampowered.com/search/?tags=-1&category1=998";

  @Test
  public void testTexts() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Element body = Jsoup.parse(html).select("body").first();
    assertFalse(simpleExtracter.texts(body, "a.search_result_row").isEmpty());
  }

  @Test
  public void testAttrs() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Element body = Jsoup.parse(html).select("body").first();
    assertFalse(simpleExtracter.attrs(body, "a.search_result_row", "href").isEmpty());
  }

  @Test
  public void testUrls() throws IOException {
    String html =
        IOUtils.toString(
            this.getClass().getResourceAsStream("/samples/store.steampowered.com.html"));
    Element body = Jsoup.parse(html).select("body").first();
    assertFalse(simpleExtracter.urls(body, "a.search_result_row", "href", baseUrl).isEmpty());
  }
}
