package org.woehlke.twitterwall.backend.mq.urls.endpoint.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.urls.endpoint.services.UrFetcher;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessageBuilder;

@Component("mqUrFetcher")
public class UrFetcherImpl implements UrFetcher {


    @Override
    public Message<UrlMessage> fetchUrl(Message<UrlMessage> incomingUserMessage) {
        return null;
    }

    private final UrlMessageBuilder urlMessageBuilder;

    @Autowired
    public UrFetcherImpl(UrlMessageBuilder urlMessageBuilder) {
        this.urlMessageBuilder = urlMessageBuilder;
    }
}
