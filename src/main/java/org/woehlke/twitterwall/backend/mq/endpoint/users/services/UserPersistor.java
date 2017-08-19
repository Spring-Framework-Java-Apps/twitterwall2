package org.woehlke.twitterwall.backend.mq.endpoint.users.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.UserMessage;

public interface UserPersistor {

    Message<UserMessage> persistUser(Message<UserMessage> incomingUserMessage);
}
