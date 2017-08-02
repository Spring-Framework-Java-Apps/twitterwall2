package org.woehlke.twitterwall.conf;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;

@Configuration
@EnableConfigurationProperties({
    TestdataProperties.class
})
public class TestConfig {
}
