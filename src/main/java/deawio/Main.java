package deawio;

import deawio.crawler.site.StoreSteampoweredCom;
import deawio.util.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    while (true) {
      Thread t1 =
          new Thread((StoreSteampoweredCom) applicationContext.getBean("storeSteampoweredCom"));
      t1.start();
      t1.join();
    }
  }
}
