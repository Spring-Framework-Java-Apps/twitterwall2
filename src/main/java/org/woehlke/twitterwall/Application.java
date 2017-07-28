package org.woehlke.twitterwall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.conf.properties.TwitterwallBackendProperties;
import org.woehlke.twitterwall.conf.properties.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.conf.properties.TwitterwallSchedulerProperties;


/**
 * Created by tw on 10.06.17.
 */
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({
    TwitterwallBackendProperties.class,
    TwitterwallFrontendProperties.class,
    TwitterwallSchedulerProperties.class,
    TwitterProperties.class
})
@EnableSpringDataWebSupport
@ImportResource("classpath:integration.xml")
public class Application {

    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }

}
