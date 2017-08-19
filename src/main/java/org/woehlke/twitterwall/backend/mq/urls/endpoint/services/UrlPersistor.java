package org.woehlke.twitterwall.backend.mq.urls.endpoint.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;

public interface UrlPersistor {

    Message<UrlMessage> persistUrl(Message<UrlMessage> incomingUserMessage);
}
