package org.woehlke.twitterwall.scheduled.mq.msg;


import org.springframework.social.twitter.api.Tweet;

import java.io.Serializable;

public class TweetFromTwitter implements Serializable {

    private final long taskId;
    private final long tweetIdFromTwitter;
    private final org.springframework.social.twitter.api.Tweet tweetFromTwitter;
    private final org.woehlke.twitterwall.oodm.entities.Tweet tweet;
    private final boolean transformed;
    private final boolean persisted;

    public TweetFromTwitter(long taskId,Tweet tweetFromTwitter){
        this.taskId = taskId;
        this.tweetFromTwitter = tweetFromTwitter;
        this.tweetIdFromTwitter = tweetFromTwitter.getId();
        this.tweet = null;
        this.transformed = false;
        this.persisted = false;
    }

    public TweetFromTwitter(long taskId, long tweetIdFromTwitter, Tweet tweetFromTwitter, org.woehlke.twitterwall.oodm.entities.Tweet tweet, boolean transformed, boolean persisted) {
        this.taskId = taskId;
        this.tweetIdFromTwitter = tweetIdFromTwitter;
        this.tweetFromTwitter = tweetFromTwitter;
        this.tweet = tweet;
        this.transformed = transformed;
        this.persisted = persisted;
    }

    public TweetFromTwitter(long taskId, org.woehlke.twitterwall.oodm.entities.Tweet myTweet, org.springframework.social.twitter.api.Tweet tweetFromTwitter, boolean tansformed) {
        this.taskId = taskId;
        this.tweetFromTwitter = tweetFromTwitter;
        this.tweetIdFromTwitter = tweetFromTwitter.getId();
        this.tweet = myTweet;
        this.transformed = tansformed;
        this.persisted = false;
    }

    public TweetFromTwitter(long taskId, org.woehlke.twitterwall.oodm.entities.Tweet tweet, Tweet tweetFromTwitter, boolean transformed, boolean persisted) {
        this.taskId = taskId;
        this.tweetFromTwitter = tweetFromTwitter;
        this.tweetIdFromTwitter = tweetFromTwitter.getId();
        this.tweet = tweet;
        this.transformed = transformed;
        this.persisted = persisted;
    }

    public long getTaskId() {
        return taskId;
    }

    public long getTweetIdFromTwitter() {
        return tweetIdFromTwitter;
    }

    public Tweet getTweetFromTwitter() {
        return tweetFromTwitter;
    }

    public org.woehlke.twitterwall.oodm.entities.Tweet getTweet() {
        return tweet;
    }

    public boolean isTransformed() {
        return transformed;
    }

    public boolean isPersisted() {
        return persisted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TweetFromTwitter that = (TweetFromTwitter) o;

        if (taskId != that.taskId) return false;
        if (tweetIdFromTwitter != that.tweetIdFromTwitter) return false;
        if (transformed != that.transformed) return false;
        if (persisted != that.persisted) return false;
        if (tweetFromTwitter != null ? !tweetFromTwitter.equals(that.tweetFromTwitter) : that.tweetFromTwitter != null)
            return false;
        return tweet != null ? tweet.equals(that.tweet) : that.tweet == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (taskId ^ (taskId >>> 32));
        result = 31 * result + (int) (tweetIdFromTwitter ^ (tweetIdFromTwitter >>> 32));
        result = 31 * result + (tweetFromTwitter != null ? tweetFromTwitter.hashCode() : 0);
        result = 31 * result + (tweet != null ? tweet.hashCode() : 0);
        result = 31 * result + (transformed ? 1 : 0);
        result = 31 * result + (persisted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TweetFromTwitter{" +
            "taskId=" + taskId +
            ", tweetIdFromTwitter=" + tweetIdFromTwitter +
            ", tweetFromTwitter=" + tweetFromTwitter +
            ", tweet=" + tweet +
            ", transformed=" + transformed +
            ", persisted=" + persisted +
            '}';
    }
}
