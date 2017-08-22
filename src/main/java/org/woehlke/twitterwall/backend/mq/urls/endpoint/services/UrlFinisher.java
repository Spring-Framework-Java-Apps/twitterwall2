package org.woehlke.twitterwall.backend.mq.urls.endpoint.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlResultList;

import java.util.List;

public interface UrlFinisher {

    Message<UrlResultList> finish(Message<List<UrlMessage>> incomingMessageList);

    void finishAsnyc(Message<List<UrlMessage>> incomingMessageList);
}
