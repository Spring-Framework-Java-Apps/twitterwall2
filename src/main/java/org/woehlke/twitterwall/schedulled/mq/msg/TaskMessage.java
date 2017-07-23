package org.woehlke.twitterwall.schedulled.mq.msg;

import org.woehlke.twitterwall.oodm.entities.parts.TaskType;

import java.io.Serializable;
import java.util.Date;

public class TaskMessage implements Serializable {

    private final long taskId;
    private final TaskType taskType;
    private final Date timeStarted;

    public TaskMessage(long taskId, TaskType taskType,Date timeStarted) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.timeStarted=timeStarted;
    }

    public long getTaskId() {
        return taskId;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskMessage)) return false;

        TaskMessage that = (TaskMessage) o;

        if (getTaskId() != that.getTaskId()) return false;
        if (getTaskType() != that.getTaskType()) return false;
        return getTimeStarted() != null ? getTimeStarted().equals(that.getTimeStarted()) : that.getTimeStarted() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getTaskId() ^ (getTaskId() >>> 32));
        result = 31 * result + (getTaskType() != null ? getTaskType().hashCode() : 0);
        result = 31 * result + (getTimeStarted() != null ? getTimeStarted().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskMessage{" +
            "taskId=" + taskId +
            ", taskType=" + taskType +
            ", timeStarted=" + timeStarted +
            '}';
    }
}
