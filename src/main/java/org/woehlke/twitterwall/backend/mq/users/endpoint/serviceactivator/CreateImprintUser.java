package org.woehlke.twitterwall.backend.mq.users.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;

public interface CreateImprintUser {

    Message<UserMessage> createImprintUser(Message<TaskMessage> mqMessage);
}
