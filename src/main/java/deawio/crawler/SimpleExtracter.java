package deawio.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class SimpleExtracter {
  public List<String> texts(Element document, String css) {
    List<String> texts = new ArrayList<>();
    Elements elements = document.select(css);
    if (elements != null) {
      for (Element element : elements) {
        String text = element.text().trim();
        try {
          Validate.notBlank(text);
          texts.add(element.text().trim());
        } catch (Exception e) {
        }
      }
    }
    return texts;
  }

  public List<String> attrs(Element document, String css, String attr) {
    List<String> attrs = new ArrayList<>();
    Elements elements = document.select(css);
    if (elements != null) {
      for (Element element : elements) {
        if (element.attr(attr) != null) {
          try {
            Validate.notBlank(element.attr(attr).trim());
            attrs.add(element.attr(attr).trim());
          } catch (Exception e) {
          }
        }
      }
    }
    return attrs;
  }

  public List<String> urls(Element document, String css, String attr, String baseUrl) {
    List<String> urls = new ArrayList<>();
    List<String> hrefs = attrs(document, css, attr);
    for (String href : hrefs) {
      try {
        String joined = new URL(new URL(baseUrl), href).toString();
        try {
          Validate.notBlank(joined);
          urls.add(joined);
        } catch (Exception e) {
        }
      } catch (MalformedURLException e) {
      }
    }
    return urls;
  }
}
