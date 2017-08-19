package org.woehlke.twitterwall.scheduled.mq.msg.results;

import org.woehlke.twitterwall.oodm.entities.Tweet;

import java.io.Serializable;
import java.util.List;

public class TweetResultList implements Serializable {

    private final long taskId;
    private final List<Tweet> tweetList;

    public TweetResultList(long taskId, List<Tweet> tweetList) {
        this.taskId = taskId;
        this.tweetList = tweetList;
    }

    public long getTaskId() {
        return taskId;
    }

    public List<Tweet> getTweetList() {
        return tweetList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TweetResultList that = (TweetResultList) o;

        if (taskId != that.taskId) return false;
        return tweetList != null ? tweetList.equals(that.tweetList) : that.tweetList == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (taskId ^ (taskId >>> 32));
        result = 31 * result + (tweetList != null ? tweetList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TweetResultList{" +
            "taskId=" + taskId +
            ", tweetList=" + tweetList +
            '}';
    }
}
