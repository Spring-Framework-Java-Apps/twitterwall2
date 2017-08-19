package org.woehlke.twitterwall.backend.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.mentions.msg.MentionMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskMessage;

import java.util.List;

public interface MentionSplitter {

    List<Message<MentionMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage);
}
