package deawio;

import deawio.config.AppConfig;
import deawio.crawler.site.StoreSteampoweredCom;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    Thread t1 = new Thread(context.getBean("storeSteampoweredCom", StoreSteampoweredCom.class));
    t1.start();
    t1.join();
  }
}
