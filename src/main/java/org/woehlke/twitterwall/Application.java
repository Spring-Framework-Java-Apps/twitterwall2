package org.woehlke.twitterwall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.woehlke.twitterwall.conf.TwitterProperties;
import org.woehlke.twitterwall.conf.TwitterwallBackendProperties;
import org.woehlke.twitterwall.conf.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.conf.TwitterwallSchedulerProperties;


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
//@EnableWebMvc
@EnableSpringDataWebSupport
public class Application {

    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }

}
