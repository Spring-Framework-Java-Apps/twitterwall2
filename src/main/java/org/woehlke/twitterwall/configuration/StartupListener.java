package org.woehlke.twitterwall.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.configuration.properties.*;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(StartupListener.class);

    private final BackendProperties backendProperties;

    private final FrontendProperties frontendProperties;

    private final SchedulerProperties schedulerProperties;

    private final TestdataProperties testdataProperties;

    private final TwitterProperties twitterProperties;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public StartupListener(BackendProperties backendProperties, FrontendProperties frontendProperties, SchedulerProperties schedulerProperties, TestdataProperties testdataProperties, TwitterProperties twitterProperties, CountedEntitiesService countedEntitiesService) {
        this.backendProperties = backendProperties;
        this.frontendProperties = frontendProperties;
        this.schedulerProperties = schedulerProperties;
        this.testdataProperties = testdataProperties;
        this.twitterProperties = twitterProperties;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<String> outputLines = new ArrayList<>();
        outputLines.add("--------------------------------------------------------------------------------------------------------------");
        outputLines.add("    ***  Twitterwall - (c) 2017 Thomas Woehlke  ***");
        outputLines.add("--------------------------------------------------------------------------------------------------------------");

        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        for(String outputLine:outputLines){
            sb.append(outputLine);
            sb.append("\n");
        }
        log.info(sb.toString());
    }
}
