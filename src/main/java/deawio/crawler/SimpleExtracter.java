package deawio.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class SimpleExtracter {
  public List<String> texts(Elements elements, String css) {
    List<String> texts = new ArrayList<>();
    for (Element element : elements) {
      String text = element.text().trim();
      try {
        Validate.notNull(text);
        texts.add(text);
      } catch (Exception e) {
      }
    }
    return texts;
  }

  public List<String> texts(Element htmlDocument, String css) {
    Elements elements = htmlDocument.select(css);
    if (elements == null) {
      return new ArrayList<>();
    } else {
      return texts(elements, css);
    }
  }

  public List<String> texts(String html, String css) {
    Elements elements = Jsoup.parse(html).select(css);
    if (elements == null) {
      return new ArrayList<>();
    } else {
      return texts(elements, css);
    }
  }

  public List<String> attrs(Elements elements, String css, String attr) {
    List<String> attrs = new ArrayList<>();
    if (elements != null) {
      for (Element element : elements) {
        if (element.attr(attr) != null) {
          String text = element.attr(attr).trim();
          try {
            Validate.notNull(text);
            attrs.add(text);
          } catch (Exception e) {
          }
        }
      }
    }
    return attrs;
  }

  public List<String> attrs(Element htmlDocument, String css, String attr) {
    Elements elements = htmlDocument.select(css);
    if (elements == null) {
      return new ArrayList<>();
    } else {
      return attrs(elements, css, attr);
    }
  }

  public List<String> attrs(String html, String css, String attr) {
    Elements elements = Jsoup.parse(html).select(css);
    if (elements == null) {
      return new ArrayList<>();
    } else {
      return attrs(elements, css, attr);
    }
  }

  public List<String> urls(Elements elements, String css, String attr, String baseUrl) {
    List<String> urls = new ArrayList<>();
    List<String> hrefs = attrs(elements, css, attr);
    for (String href : hrefs) {
      try {
        String joined = new URL(new URL(baseUrl), href).toString();
        try {
          Validate.notNull(joined);
          urls.add(joined);
        } catch (Exception e) {
        }
      } catch (MalformedURLException e) {
      }
    }
    return urls;
  }

  public List<String> urls(Element htmlDocument, String css, String attr, String baseUrl) {
    Elements elements = htmlDocument.select(css);
    if (elements == null) {
      return new ArrayList<>();
    } else {
      return urls(elements, css, attr, baseUrl);
    }
  }

  public List<String> urls(String html, String css, String attr, String baseUrl) {
    Elements elements = Jsoup.parse(html).select(css);
    if (elements == null) {
      return new ArrayList<>();
    } else {
      return urls(elements, css, attr, baseUrl);
    }
  }
}
