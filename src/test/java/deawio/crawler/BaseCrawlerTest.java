package deawio.crawler;

import junit.framework.TestCase;
import org.junit.Test;

public class BaseCrawlerTest extends TestCase {
  @Test
  public void testExtractPrice() {
    assertEquals(BaseCrawler.extractPrice("Free"), 0);
    assertEquals(BaseCrawler.extractPrice("free"), 0);
    assertEquals(BaseCrawler.extractPrice("$99"), 99);
    assertEquals(BaseCrawler.extractPrice("$99.99"), 99.99);
    assertEquals(BaseCrawler.extractPrice("$99,99"), 99.99);
    assertEquals(BaseCrawler.extractPrice("$99.999,99"), 99999.99);
    assertEquals(BaseCrawler.extractPrice("$99,999.99"), 99999.99);
    assertEquals(BaseCrawler.extractPrice("ERROR"), null);
  }

  @Test
  public void testExtractDiscount() {
    assertEquals(BaseCrawler.extractDiscount("99"), 0.99);
    assertEquals(BaseCrawler.extractDiscount("99%"), 0.99);
    assertEquals(BaseCrawler.extractDiscount("99% OFF"), 0.99);
    assertEquals(BaseCrawler.extractDiscount("ERROR"), null);
  }

  @Test
  public void testCalcDiscount() {}
}
