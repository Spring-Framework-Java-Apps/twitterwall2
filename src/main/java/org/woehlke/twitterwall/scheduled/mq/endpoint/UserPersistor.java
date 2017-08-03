package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

public interface UserPersistor {

    Message<UserMessage> persistUser(Message<UserMessage> incomingUserMessage);
}
