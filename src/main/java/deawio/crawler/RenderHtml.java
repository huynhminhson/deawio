package deawio.crawler;

import static spark.Spark.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
public class RenderHtml implements Runnable {
  @Override
  public void run() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--headless");
    WebDriver webDriver = new ChromeDriver(chromeOptions);

    port(9000);
    get(
        "/",
        (request, response) -> {
          String url = request.queryParams("url");
          JavascriptExecutor js = (JavascriptExecutor) webDriver;
          webDriver.get(url);
          String html = (String) js.executeScript("return document.body.innerHTML");

          return html;
        });
  }
}
