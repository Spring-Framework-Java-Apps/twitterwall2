package org.woehlke.twitterwall.scheduled.service.msg;

import org.woehlke.twitterwall.oodm.entities.parts.TaskType;

import java.io.Serializable;

public class TaskMessage implements Serializable {

    private final long taskId;
    private final TaskType taskType;

    public TaskMessage(long taskId, TaskType taskType) {
        this.taskId = taskId;
        this.taskType = taskType;
    }

    public long getTaskId() {
        return taskId;
    }

    public TaskType getTaskType() {
        return taskType;
    }
}
