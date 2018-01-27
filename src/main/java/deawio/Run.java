package deawio;

import deawio.base.BrowserUtil;
import org.openqa.selenium.WebDriver;

public class Run {
  public static void main(String[] args) {
    WebDriver webDriver = BrowserUtil.webDriver;
    webDriver.get("https://uk.gamersgate.com/games?state=available");
    System.out.println(webDriver.getPageSource());
  }
}
