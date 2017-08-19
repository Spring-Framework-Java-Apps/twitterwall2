package org.woehlke.twitterwall.backend.mq.urls.endpoint.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.urls.endpoint.services.UrlFinisher;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessageBuilder;

import java.util.List;

@Component("mqUrlFinisher")
public class UrlFinisherImpl implements UrlFinisher {


    @Override
    public Message<UrlMessage> finish(Message<List<UrlMessage>> incomingMessageList) {
        return null;
    }

    @Override
    public void finishAsnyc(Message<List<UrlMessage>> incomingMessageList) {

    }

    private final UrlMessageBuilder urlMessageBuilder;

    @Autowired
    public UrlFinisherImpl(UrlMessageBuilder urlMessageBuilder) {
        this.urlMessageBuilder = urlMessageBuilder;
    }
}
