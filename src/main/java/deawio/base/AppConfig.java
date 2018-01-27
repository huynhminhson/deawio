package deawio.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@ComponentScan("deawio")
public class AppConfig {
  @Bean
  public TaskExecutor taskExecutor() {
    return new SimpleAsyncTaskExecutor();
    //    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    //    executor.setCorePoolSize(4);
    //    executor.setMaxPoolSize(4);
    //    executor.initialize();
    //    return executor;
  }
}
