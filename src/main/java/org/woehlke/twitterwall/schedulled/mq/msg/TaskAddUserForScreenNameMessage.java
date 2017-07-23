package org.woehlke.twitterwall.schedulled.mq.msg;

import org.woehlke.twitterwall.oodm.entities.parts.TaskType;

import java.io.Serializable;
import java.util.Date;

public class TaskAddUserForScreenNameMessage implements Serializable {

    private final long taskId;
    private final TaskType taskType;
    private final Date timeStarted;
    private final String screenName;

    public TaskAddUserForScreenNameMessage(long taskId, TaskType taskType, Date timeStarted, String screenName) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.timeStarted = timeStarted;
        this.screenName = screenName;
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

    public String getScreenName() {
        return screenName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskAddUserForScreenNameMessage)) return false;

        TaskAddUserForScreenNameMessage that = (TaskAddUserForScreenNameMessage) o;

        if (getTaskId() != that.getTaskId()) return false;
        if (getTaskType() != that.getTaskType()) return false;
        if (getTimeStarted() != null ? !getTimeStarted().equals(that.getTimeStarted()) : that.getTimeStarted() != null)
            return false;
        return getScreenName() != null ? getScreenName().equals(that.getScreenName()) : that.getScreenName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getTaskId() ^ (getTaskId() >>> 32));
        result = 31 * result + (getTaskType() != null ? getTaskType().hashCode() : 0);
        result = 31 * result + (getTimeStarted() != null ? getTimeStarted().hashCode() : 0);
        result = 31 * result + (getScreenName() != null ? getScreenName().hashCode() : 0);
        return result;
    }
}
