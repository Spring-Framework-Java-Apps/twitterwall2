package org.woehlke.twitterwall.backend.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.UrlMessage;

import java.util.List;

public interface UrlSplitter {

    List<Message<UrlMessage>> splitUrlMessage(Message<UrlMessage> incomingTaskMessage);
}
