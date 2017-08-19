package org.woehlke.twitterwall.backend.mq.endpoint.users.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.UserMessage;
import org.woehlke.twitterwall.backend.mq.msg.results.UserResultList;

import java.util.List;

public interface UserFinisher {

    Message<UserResultList> finish(Message<List<UserMessage>> incomingMessageList);

    void finishAsnyc(Message<List<UserMessage>> incomingMessageList);

    void finishOneUserAsnyc(Message<UserMessage> incomingMessage);
}
