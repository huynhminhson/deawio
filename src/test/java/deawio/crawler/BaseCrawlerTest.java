package deawio.crawler;

import deawio.config.AppConfig;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class BaseCrawlerTest extends TestCase {
  @Autowired private BaseCrawler baseCrawler;

  @Test
  public void testExtractPrice() {
    // BaseCrawler baseCrawler = context.getBean("baseCrawler", BaseCrawler.class);
    assertEquals(baseCrawler.extractPrice("Free"), (double) 0);
    assertEquals(baseCrawler.extractPrice("free"), (double) 0);
    assertEquals(baseCrawler.extractPrice("$99"), (double) 99);
    assertEquals(baseCrawler.extractPrice("$99.99"), (double) 99.99);
    assertEquals(baseCrawler.extractPrice("$99,99"), (double) 99.99);
    assertEquals(baseCrawler.extractPrice("$99.999,99"), (double) 99999.99);
    assertEquals(baseCrawler.extractPrice("$99,999.99"), (double) 99999.99);
    assertEquals(baseCrawler.extractPrice("ERROR"), null);
  }

  @Test
  public void testExtractDiscount() {
    BaseCrawler baseCrawler = new BaseCrawler();
    assertEquals(baseCrawler.extractDiscount("99"), (double) 0.99);
    assertEquals(baseCrawler.extractDiscount("99%"), (double) 0.99);
    assertEquals(baseCrawler.extractDiscount("99% OFF"), (double) 0.99);
    assertEquals(baseCrawler.extractDiscount("ERROR"), null);
  }

  @Test
  public void testCalcDiscount() {
    BaseCrawler baseCrawler = new BaseCrawler();
    assertEquals(baseCrawler.calcDiscount(5, 10), (double) 0.5);
    assertEquals(baseCrawler.calcDiscount(0, 10), (double) 1);
    assertEquals(baseCrawler.calcDiscount(0, 0), (double) 1);
    assertEquals(baseCrawler.calcDiscount(10, 0), null);
  }

  @Test
  public void testCalcHighPrice() {
    BaseCrawler baseCrawler = new BaseCrawler();
    assertEquals(baseCrawler.calcHighPrice(0.5, 5), (double) 10);
    assertEquals(baseCrawler.calcHighPrice(1, 0), (double) 0);
    assertEquals(baseCrawler.calcHighPrice(1, 10), null);
  }

  @Test
  public void testCalcLowPrice() {
    BaseCrawler baseCrawler = new BaseCrawler();
    assertEquals(baseCrawler.calcLowPrice(0.5, 10), (double) 5);
  }
}
