package org.woehlke.twitterwall.backend.mq.endpoint.users.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.users.msg.UserMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.users.msg.UserResultList;

import java.util.List;

public interface UserFinisher {

    Message<UserResultList> finish(Message<List<UserMessage>> incomingMessageList);

    void finishAsnyc(Message<List<UserMessage>> incomingMessageList);

    void finishOneUserAsnyc(Message<UserMessage> incomingMessage);
}
