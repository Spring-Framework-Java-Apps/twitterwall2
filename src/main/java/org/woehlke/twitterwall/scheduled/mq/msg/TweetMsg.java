package org.woehlke.twitterwall.scheduled.mq.msg;

import org.woehlke.twitterwall.oodm.entities.Tweet;

import java.io.Serializable;

public class TweetMsg implements Serializable {

    private final long taskId;
    private final Tweet tweet;


    public TweetMsg(long taskId, Tweet tweet) {
        this.taskId = taskId;
        this.tweet = tweet;
    }

    public long getTaskId() {
        return taskId;
    }

    public Tweet getTweet() {
        return tweet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TweetMsg tweetMsg = (TweetMsg) o;

        if (taskId != tweetMsg.taskId) return false;
        return tweet != null ? tweet.equals(tweetMsg.tweet) : tweetMsg.tweet == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (taskId ^ (taskId >>> 32));
        result = 31 * result + (tweet != null ? tweet.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TweetMsg{" +
            "taskId=" + taskId +
            ", tweet=" + tweet +
            '}';
    }
}
