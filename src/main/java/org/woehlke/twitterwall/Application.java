package org.woehlke.twitterwall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created by tw on 10.06.17.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@IntegrationComponentScan
@EnableScheduling
public class Application {

    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }

}
