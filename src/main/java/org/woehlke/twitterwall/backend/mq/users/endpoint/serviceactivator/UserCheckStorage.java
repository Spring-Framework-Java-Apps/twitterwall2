package org.woehlke.twitterwall.backend.mq.users.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;

public interface UserCheckStorage {

    Message<UserMessage> checkIfUserIsInStorage(Message<UserMessage> mqMessage);
}
