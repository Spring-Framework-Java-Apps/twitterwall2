package org.woehlke.twitterwall.backend.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.MentionMessage;
import org.woehlke.twitterwall.backend.mq.msg.TaskMessage;

import java.util.List;

public interface MentionSplitter {

    List<Message<MentionMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage);
}
