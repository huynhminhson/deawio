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

    TaskExecutor taskExecutor = context.getBean("taskExecutor", TaskExecutor.class);
    taskExecutor.execute(context.getBean("storeSteampoweredCom", StoreSteampoweredCom.class));
    taskExecutor.execute(context.getBean("ukGamersgateCom", UkGamersgateCom.class));
    taskExecutor.execute(context.getBean("ukGamesplanetCom", UkGamesplanetCom.class));
    taskExecutor.execute(context.getBean("deGamesplanetCom", DeGamesplanetCom.class));
  }
}
