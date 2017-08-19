package org.woehlke.twitterwall.backend.mq.endpoint.users.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.TaskMessage;
import org.woehlke.twitterwall.backend.mq.msg.UserMessage;

public interface CreateImprintUser {

    Message<UserMessage> createImprintUser(Message<TaskMessage> mqMessage);
}
