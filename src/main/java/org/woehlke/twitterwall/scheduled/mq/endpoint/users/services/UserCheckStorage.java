package org.woehlke.twitterwall.scheduled.mq.endpoint.users.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

public interface UserCheckStorage {

    Message<UserMessage> checkIfUserIsInStorage(Message<UserMessage> mqMessage);
}
