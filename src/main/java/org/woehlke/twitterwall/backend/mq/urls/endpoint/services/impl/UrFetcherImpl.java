package org.woehlke.twitterwall.backend.mq.urls.endpoint.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.urls.endpoint.services.UrFetcher;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessageBuilder;
import org.woehlke.twitterwall.backend.service.remote.TwitterUrlService;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.oodm.service.TaskService;

@Component("mqUrFetcher")
public class UrFetcherImpl implements UrFetcher {


    @Override
    public Message<UrlMessage> fetchUrl(Message<UrlMessage> incomingUserMessage) {
        UrlMessage urlMessage =incomingUserMessage.getPayload();
        Task task = taskService.findById(urlMessage.getTaskMessage().getTaskId());
        String url = urlMessage.getUrlString();
        Url foundUrl = twitterUrlService.fetchTransientUrl(url,task);
        if((!foundUrl.isRawUrlsFromDescription()) && (foundUrl.isUrlAndExpandedTheSame())){
            return urlMessageBuilder.createUrlMessage(incomingUserMessage, foundUrl);
        } else {
            boolean ignoreNextSteps = true;
            return urlMessageBuilder.createUrlMessage(incomingUserMessage, ignoreNextSteps);
        }
    }


    private final TaskService taskService;

    private final UrlMessageBuilder urlMessageBuilder;

    private final TwitterUrlService twitterUrlService;

    @Autowired
    public UrFetcherImpl(TaskService taskService, UrlMessageBuilder urlMessageBuilder, TwitterUrlService twitterUrlService) {
        this.taskService = taskService;
        this.urlMessageBuilder = urlMessageBuilder;
        this.twitterUrlService = twitterUrlService;
    }
}
