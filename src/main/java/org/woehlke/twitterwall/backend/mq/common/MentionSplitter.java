package org.woehlke.twitterwall.backend.mq.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.mentions.msg.MentionMessage;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;

import java.util.List;

public interface MentionSplitter {

    List<Message<MentionMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage);
}
