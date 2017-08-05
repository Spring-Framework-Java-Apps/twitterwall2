package org.woehlke.twitterwall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.woehlke.twitterwall.conf.properties.*;


/**
 * Created by tw on 10.06.17.
 */
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({
    BackendProperties.class,
    FrontendProperties.class,
    SchedulerProperties.class,
    TwitterProperties.class,
    TestdataProperties.class
})
@EnableSpringDataWebSupport
@ImportResource("classpath:integration.xml")
public class Application {

    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }

}
