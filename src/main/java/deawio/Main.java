package deawio;

import deawio.crawler.site.StoreSteampoweredCom;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    ApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("applicationContext.xml");
    while (true) {
      Thread t1 =
          new Thread((StoreSteampoweredCom) applicationContext.getBean("storeSteampoweredCom"));
      t1.start();
      t1.join();
    }
  }
}
