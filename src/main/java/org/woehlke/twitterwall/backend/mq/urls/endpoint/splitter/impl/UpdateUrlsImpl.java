package org.woehlke.twitterwall.backend.mq.urls.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.urls.endpoint.splitter.UpdateUrls;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessageBuilder;

import java.util.List;

@Component("mqUpdateUrls")
public class UpdateUrlsImpl implements UpdateUrls {

    @Override
    public List<Message<UrlMessage>> splitUrlMessage(Message<UrlMessage> incomingTaskMessage) {
        return null;
    }


    private final UrlMessageBuilder urlMessageBuilder;

    @Autowired
    public UpdateUrlsImpl(UrlMessageBuilder urlMessageBuilder) {
        this.urlMessageBuilder = urlMessageBuilder;
    }
}
