package deawio;

import deawio.base.AppConfig;
import deawio.crawler.site.DeGamesplanetCom;
import deawio.crawler.site.StoreSteampoweredCom;
import deawio.crawler.site.UkGamersgateCom;
import deawio.crawler.site.UkGamesplanetCom;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.task.TaskExecutor;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    StoreSteampoweredCom storeSteampoweredCom =
        context.getBean("storeSteampoweredCom", StoreSteampoweredCom.class);
    UkGamersgateCom ukGamersgateCom = context.getBean("ukGamersgateCom", UkGamersgateCom.class);
    UkGamesplanetCom ukGamesplanetCom = context.getBean("ukGamesplanetCom", UkGamesplanetCom.class);
    DeGamesplanetCom deGamesplanetCom = context.getBean("deGamesplanetCom", DeGamesplanetCom.class);

    TaskExecutor taskExecutor = context.getBean("taskExecutor", TaskExecutor.class);
    taskExecutor.execute(storeSteampoweredCom);
    taskExecutor.execute(ukGamersgateCom);
    taskExecutor.execute(ukGamesplanetCom);
    taskExecutor.execute(deGamesplanetCom);
  }
}
