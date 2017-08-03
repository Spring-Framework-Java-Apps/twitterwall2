package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

import java.util.List;

public interface CreateTestDataForUsers {

    List<Message<UserMessage>> splitMessage(Message<TaskMessage> message);
}
