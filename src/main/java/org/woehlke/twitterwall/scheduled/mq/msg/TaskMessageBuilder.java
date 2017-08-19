package org.woehlke.twitterwall.scheduled.mq.msg;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.oodm.entities.Task;

public interface TaskMessageBuilder {

    Message<TaskMessage> buildTaskMessage(Task task);

}
