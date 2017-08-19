package org.woehlke.twitterwall.scheduled.mq.msg.builder.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.builder.TaskMessageBuilder;

@Component
public class TaskMessageBuilderImpl implements TaskMessageBuilder {

    @Override
    public Message<TaskMessage> buildTaskMessage(Task task) {
        TaskMessage outputPayload = new TaskMessage(task.getId(), task.getTaskType(), task.getTaskSendType(), task.getTimeStarted());
        Message<TaskMessage> mqMessage =
                MessageBuilder.withPayload(outputPayload)
                        .setHeader("task_id", task.getId())
                        .setHeader("task_uid", task.getUniqueId())
                        .setHeader("task_type", task.getTaskType())
                        .setHeader("time_started", task.getTimeStarted().getTime())
                        .setHeader("send_type", task.getTaskSendType())
                        .build();
        return mqMessage;
    }

}
