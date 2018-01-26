package deawio.crawler;

import deawio.base.AppConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class CssSkeletonTest extends TestCase {
  @Autowired private CssSkeleton cssSkeleton;

  @Test
  public void isDomShouldBeFalse() {
    cssSkeleton.isDOM(Jsoup.parse("<style></style>").select("style").first());
    cssSkeleton.isDOM(Jsoup.parse("<script></script>").select("script").first());
    cssSkeleton.isDOM(Jsoup.parse("<body></body>").select("body").first());
  }

  @Test
  public void nodeNameShouldBeTagWhenThereIsNoClasses() {
    String html = "<div></div>";
    Element element = Jsoup.parse(html).select("div").first();
    assertEquals(cssSkeleton.nodeName(element), "div");
  }

  @Test
  public void nodeNameShouldBeCssSelectorWhenElementHasOneClass() {
    String html = "<div class=\"a\"></div>";
    Element element = Jsoup.parse(html).select("div").first();
    assertEquals(cssSkeleton.nodeName(element), ".a");
  }

  @Test
  public void nodeNameShouldBeCssSelectorWhenElementHasManyClasses() {
    String html = "<div class=\"a b c d e\"></div>";
    Element element = Jsoup.parse(html).select("div").first();
    assertEquals(cssSkeleton.nodeName(element), ".a.b.c.d.e");
  }

  @Test
  public void cssSelectorWhenDocumentHasOnlyOneNode() {
    String html = "<div class=\"a b c d e\"></div>";
    Element element = Jsoup.parse(html).select("div").first();
    List<String> cssSelector = new ArrayList<>();
    cssSkeleton.cssSelector(element, cssSelector);
    assertEquals(cssSelector, Arrays.asList(".a.b.c.d.e"));
  }

  @Test
  public void cssSelectorWhenDocumentHasManyNodes() {
    String html =
        "<div class=\"a\"><div class=\"b\"><div class=\"c\"><div class=\"d\"></div></div></div></div>";
    Element element = Jsoup.parse(html).select(".d").first();
    List<String> cssSelector = new ArrayList<>();
    cssSkeleton.cssSelector(element, cssSelector);
    assertEquals(cssSelector, Arrays.asList(".a", ".b", ".c", ".d"));
  }

  @Test
  public void parseElementShouldReturnList() {
    String html = "<div class=\"a\"><div class=\"b\"></div><div class=\"c\"></div></div>";
    Element element = Jsoup.parse(html).select(".a").first();
    List<String> cssLines = new ArrayList<>();
    cssSkeleton.parseElement(element, cssLines);
    assertEquals(cssLines, Arrays.asList(".a {}", ".a > .b {}", ".a > .c {}"));
  }

  @Test
  public void parseDocumentShouldReturnListOfCssSelectors() {
    String html = "<div class=\"a\"><div class=\"b\"></div><div class=\"c\"></div></div>";
    assertEquals(
        cssSkeleton.parseDocument(html), Arrays.asList(".a {}", ".a > .b {}", ".a > .c {}"));
  }
}
