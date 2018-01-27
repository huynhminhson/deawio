package deawio;

import deawio.base.AppConfig;
import deawio.crawler.site.StoreSteampoweredCom;
import deawio.crawler.site.UkGamersgateCom;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    StoreSteampoweredCom storeSteampoweredCom =
        context.getBean("storeSteampoweredCom", StoreSteampoweredCom.class);
    UkGamersgateCom ukGamersgateCom = context.getBean("ukGamersgateCom", UkGamersgateCom.class);
    Thread t1 = new Thread(storeSteampoweredCom);
    Thread t2 = new Thread(ukGamersgateCom);
    t1.start();
    t2.start();

    //    TaskExecutor taskExecutor = context.getBean("taskExecutor", TaskExecutor.class);
    //    taskExecutor.execute(storeSteampoweredCom);
    //    taskExecutor.execute(ukGamersgateCom);
  }
}
