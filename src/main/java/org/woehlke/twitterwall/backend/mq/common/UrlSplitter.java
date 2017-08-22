package org.woehlke.twitterwall.backend.mq.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;

import java.util.List;

public interface UrlSplitter {

    List<Message<UrlMessage>> splitUrlMessage(Message<TaskMessage> incomingTaskMessage);
}
