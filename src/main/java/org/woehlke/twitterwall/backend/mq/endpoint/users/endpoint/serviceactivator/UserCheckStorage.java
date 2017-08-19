package org.woehlke.twitterwall.backend.mq.endpoint.users.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.users.msg.UserMessage;

public interface UserCheckStorage {

    Message<UserMessage> checkIfUserIsInStorage(Message<UserMessage> mqMessage);
}
