package org.woehlke.twitterwall.backend.mq.urls.endpoint.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;

public interface UrlTransformator {

    Message<UrlMessage> transformUrl(Message<UrlMessage> incomingUserMessage);
}
