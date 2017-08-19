package org.woehlke.twitterwall.scheduled.mq.msg;

import org.woehlke.twitterwall.oodm.entities.Mention;

import java.io.Serializable;
import java.util.List;

public class MentionResultList implements Serializable {

    private final long taskId;

    private final List<Long> mentionIds;

    public MentionResultList(long taskId, List<Long> mentionIds) {
        this.taskId = taskId;
        this.mentionIds = mentionIds;
    }

    public long getTaskId() {
        return taskId;
    }

    public List<Long> getMentionIds() {
        return mentionIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MentionResultList)) return false;

        MentionResultList that = (MentionResultList) o;

        if (taskId != that.taskId) return false;
        return mentionIds != null ? mentionIds.equals(that.mentionIds) : that.mentionIds == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (taskId ^ (taskId >>> 32));
        result = 31 * result + (mentionIds != null ? mentionIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MentionResultList{" +
                "taskId=" + taskId +
                ", mentionIds=" + mentionIds +
                '}';
    }
}
