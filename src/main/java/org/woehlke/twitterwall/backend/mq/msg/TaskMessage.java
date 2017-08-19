package org.woehlke.twitterwall.backend.mq.msg;

import org.woehlke.twitterwall.oodm.model.tasks.TaskType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;

import java.io.Serializable;
import java.util.Date;

public class TaskMessage implements Serializable {

    protected final long taskId;
    protected final TaskType taskType;
    protected final Date timeStarted;
    protected final TaskSendType taskSendType;

    public TaskMessage(long taskId, TaskType taskType, TaskSendType taskSendType, Date timeStarted) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.taskSendType = taskSendType;
        this.timeStarted = timeStarted;
    }

    public long getTaskId() {
        return taskId;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public TaskSendType getTaskSendType() {
        return taskSendType;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskMessage)) return false;

        TaskMessage that = (TaskMessage) o;

        if (taskId != that.taskId) return false;
        if (taskType != that.taskType) return false;
        if (timeStarted != null ? !timeStarted.equals(that.timeStarted) : that.timeStarted != null) return false;
        return taskSendType == that.taskSendType;
    }

    @Override
    public int hashCode() {
        int result = (int) (taskId ^ (taskId >>> 32));
        result = 31 * result + (taskType != null ? taskType.hashCode() : 0);
        result = 31 * result + (timeStarted != null ? timeStarted.hashCode() : 0);
        result = 31 * result + (taskSendType != null ? taskSendType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskMessage{" +
                "taskId=" + taskId +
                ", taskType=" + taskType +
                ", timeStarted=" + timeStarted +
                ", taskSendType=" + taskSendType +
                '}';
    }
}
