package org.woehlke.twitterwall;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 17.07.17.
 */
@Validated
@ConfigurationProperties(prefix="twitterwall")
public class TwitterwallProperties {

    @Valid
    public Backend backenend = new Backend();

    @Valid
    public Frontend frontend = new Frontend();

    @Valid
    public Scheduler scheduler = new Scheduler();

    public static class Backend {

    }

    public static class Frontend {
    }

    public static class Scheduler {

    }

    public Backend getBackenend() {
        return backenend;
    }

    public void setBackenend(Backend backenend) {
        this.backenend = backenend;
    }

    public Frontend getFrontend() {
        return frontend;
    }

    public void setFrontend(Frontend frontend) {
        this.frontend = frontend;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
