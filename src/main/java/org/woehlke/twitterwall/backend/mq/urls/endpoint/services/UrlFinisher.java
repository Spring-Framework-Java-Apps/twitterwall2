package org.woehlke.twitterwall.backend.mq.urls.endpoint.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;

import java.util.List;

public interface UrlFinisher {

    Message<UrlMessage> finish(Message<List<UrlMessage>> incomingMessageList);

    void finishAsnyc(Message<List<UrlMessage>> incomingMessageList);
}
