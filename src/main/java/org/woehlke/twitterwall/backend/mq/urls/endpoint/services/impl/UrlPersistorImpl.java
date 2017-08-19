package org.woehlke.twitterwall.backend.mq.urls.endpoint.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.urls.endpoint.services.UrlPersistor;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessageBuilder;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UrlService;

@Component("mqUrlPersistor")
public class UrlPersistorImpl implements UrlPersistor {

    @Override
    public Message<UrlMessage> persistUrl(Message<UrlMessage> incomingUserMessage) {

        UrlMessage in =incomingUserMessage.getPayload();
        Task task = taskService.findById(in.getTaskMessage().getTaskId());
        if(in.isIgnoreNextSteps()){
            return urlMessageBuilder.createUrlMessage(incomingUserMessage);
        } else {
            Url urlPers = urlService.findById(in.getUrlId());
            urlPers.setExpanded(in.getExpanded());
            urlPers.setDisplay(in.getDisplay());
            urlPers.setUpdatedBy(task);
            urlPers = urlService.update(urlPers,task);
            return urlMessageBuilder.createUrlMessage(incomingUserMessage);
        }
    }

    private final UrlService urlService;

    private final TaskService taskService;

    private final UrlMessageBuilder urlMessageBuilder;

    @Autowired
    public UrlPersistorImpl(UrlService urlService, TaskService taskService, UrlMessageBuilder urlMessageBuilder) {
        this.urlService = urlService;
        this.taskService = taskService;
        this.urlMessageBuilder = urlMessageBuilder;
    }

}
