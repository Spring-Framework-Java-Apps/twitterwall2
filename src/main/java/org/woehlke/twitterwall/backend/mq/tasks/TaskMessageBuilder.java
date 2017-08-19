package org.woehlke.twitterwall.backend.mq.tasks;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.oodm.model.Task;

public interface TaskMessageBuilder {

    Message<TaskMessage> buildTaskMessage(Task task);

}
