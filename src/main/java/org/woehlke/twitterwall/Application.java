package org.woehlke.twitterwall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created by tw on 10.06.17.
 */
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({
        ConfigTwitterwall.class,
        TwitterProperties.class
})
public class Application {

    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }

}
