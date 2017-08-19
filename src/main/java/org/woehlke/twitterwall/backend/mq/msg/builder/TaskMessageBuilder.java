package org.woehlke.twitterwall.backend.mq.msg.builder;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.backend.mq.msg.TaskMessage;

public interface TaskMessageBuilder {

    Message<TaskMessage> buildTaskMessage(Task task);

}
