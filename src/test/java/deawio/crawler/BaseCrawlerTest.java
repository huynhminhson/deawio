package deawio.crawler;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class BaseCrawlerTest extends TestCase {
  @Autowired public BaseCrawler baseCrawler;

  @Test
  public void testExtractPrice() {
    assertEquals(baseCrawler.extractPrice("Free"), 0);
    assertEquals(baseCrawler.extractPrice("free"), 0);
    assertEquals(baseCrawler.extractPrice("$99"), 99);
    assertEquals(baseCrawler.extractPrice("$99.99"), 99.99);
    assertEquals(baseCrawler.extractPrice("$99,99"), 99.99);
    assertEquals(baseCrawler.extractPrice("$99.999,99"), 99999.99);
    assertEquals(baseCrawler.extractPrice("$99,999.99"), 99999.99);
    assertEquals(baseCrawler.extractPrice("ERROR"), null);
  }

  @Test
  public void testExtractDiscount() {
    assertEquals(baseCrawler.extractDiscount("99"), 0.99);
    assertEquals(baseCrawler.extractDiscount("99%"), 0.99);
    assertEquals(baseCrawler.extractDiscount("99% OFF"), 0.99);
    assertEquals(baseCrawler.extractDiscount("ERROR"), null);
  }

  @Test
  public void testCalcDiscount() {}
}
