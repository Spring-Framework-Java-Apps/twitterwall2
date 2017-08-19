package org.woehlke.twitterwall.scheduled.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.MentionMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;

import java.util.List;

public interface MentionSplitter {

    List<Message<MentionMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage);
}
