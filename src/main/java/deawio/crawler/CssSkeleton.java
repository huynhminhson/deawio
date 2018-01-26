package deawio.crawler;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class CssSkeleton {
  public List<String> parseDocument(String document) {
    List<String> allCssLines = new ArrayList<>();
    Elements roots = Jsoup.parse(document).select("body").first().children();
    for (Element root : roots) {
      if (isDOM(root)) {
        List<String> cssLines = new ArrayList<>();
        parseElement(root, cssLines);
        for (String line : cssLines) {
          allCssLines.add(line);
        }
      }
    }
    return allCssLines;
  }

  public void parseElement(Element element, List<String> cssLines) {
    List<String> cssSelector = new ArrayList<>();
    cssSelector(element, cssSelector);
    if (cssSelector.size() > 0) {
      String line = String.join(" > ", cssSelector) + " {}";
      if (!cssLines.contains(line)) {
        cssLines.add(line);
      }
      if (element.children() != null) {
        for (Element child : element.children()) {
          if (isDOM(child)) {
            parseElement(child, cssLines);
          }
        }
      }
    }
  }

  public void cssSelector(Element element, List<String> cssSelector) {
    cssSelector.add(0, nodeName(element));
    if (element.parent() != null && isDOM(element.parent())) {
      cssSelector(element.parent(), cssSelector);
    }
  }

  public String nodeName(Element element) {
    if (element.className() == null || element.className().isEmpty()) {
      return element.tagName();
    } else {
      String className = "";
      if (element.className().contains(" ")) {
        for (String str : element.className().split(" ")) {
          className = className + "." + str.trim();
        }
      } else {
        className = "." + element.className();
      }
      return className;
    }
  }

  public boolean isDOM(Element element) {
    if (element.tagName() == "style"
        || element.tagName() == "script"
        || element.tagName() == "body") {
      return false;
    } else {
      return true;
    }
  }
}
