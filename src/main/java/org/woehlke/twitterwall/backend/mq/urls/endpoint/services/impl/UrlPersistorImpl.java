package org.woehlke.twitterwall.backend.mq.urls.endpoint.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.urls.endpoint.services.UrlPersistor;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessageBuilder;

@Component("mqUrlPersistor")
public class UrlPersistorImpl implements UrlPersistor {

    @Override
    public Message<UrlMessage> persistUrl(Message<UrlMessage> incomingUserMessage) {
        return null;
    }

    private final UrlMessageBuilder urlMessageBuilder;

    @Autowired
    public UrlPersistorImpl(UrlMessageBuilder urlMessageBuilder) {
        this.urlMessageBuilder = urlMessageBuilder;
    }

}
