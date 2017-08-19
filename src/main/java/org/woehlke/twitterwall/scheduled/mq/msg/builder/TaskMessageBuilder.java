package org.woehlke.twitterwall.scheduled.mq.msg.builder;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;

public interface TaskMessageBuilder {

    Message<TaskMessage> buildTaskMessage(Task task);

}
