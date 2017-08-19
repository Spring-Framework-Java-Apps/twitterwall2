package org.woehlke.twitterwall.backend.mq.endpoint.users.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.users.msg.UserMessage;

public interface CreateImprintUser {

    Message<UserMessage> createImprintUser(Message<TaskMessage> mqMessage);
}
