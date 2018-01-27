package deawio.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserUtil {
  public static final ThreadLocal<WebDriver> webDriver =
      new ThreadLocal<WebDriver>() {
        @Override
        protected WebDriver initialValue() {
          ChromeOptions chromeOptions = new ChromeOptions();
          chromeOptions.addArguments("--headless");
          return new ChromeDriver(chromeOptions);
        }
      };
}
