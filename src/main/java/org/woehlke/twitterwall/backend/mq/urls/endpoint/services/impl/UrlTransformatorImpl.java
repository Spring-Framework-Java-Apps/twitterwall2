package org.woehlke.twitterwall.backend.mq.urls.endpoint.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.urls.endpoint.services.UrlTransformator;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessageBuilder;

@Component("mqUrlTransformator")
public class UrlTransformatorImpl implements UrlTransformator {


    @Override
    public Message<UrlMessage> transformUrl(Message<UrlMessage> incomingUserMessage) {
        return null;
    }

    private final UrlMessageBuilder urlMessageBuilder;

    @Autowired
    public UrlTransformatorImpl(UrlMessageBuilder urlMessageBuilder) {
        this.urlMessageBuilder = urlMessageBuilder;
    }
}
