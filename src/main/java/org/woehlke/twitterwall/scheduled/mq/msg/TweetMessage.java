package org.woehlke.twitterwall.scheduled.mq.msg;


import org.springframework.social.twitter.api.Tweet;

import java.io.Serializable;

public class TweetMessage implements Serializable {

    private final TaskMessage taskMessage;
    private final long tweetIdTwitter;
    private final org.springframework.social.twitter.api.Tweet tweetFromTwitter;
    private final org.woehlke.twitterwall.oodm.entities.Tweet tweet;
    private final boolean ignoreTransformation;

    public TweetMessage(
            TaskMessage taskMessage,
            Tweet tweetFromTwitter
    ){
        this.taskMessage = taskMessage;
        this.tweetFromTwitter = tweetFromTwitter;
        this.tweetIdTwitter = tweetFromTwitter.getId();
        this.tweet = null;
        this.ignoreTransformation = false;
    }

    public TweetMessage(
            TaskMessage taskMessage,
            org.woehlke.twitterwall.oodm.entities.Tweet tweet
    ){
        this.taskMessage = taskMessage;
        this.tweetFromTwitter = null;
        this.tweetIdTwitter = tweet.getIdTwitter();
        this.tweet = tweet;
        this.ignoreTransformation = true;
    }

    public TweetMessage(
            TaskMessage taskMessage,
            long tweetIdTwitter,
            Tweet tweetFromTwitter,
            org.woehlke.twitterwall.oodm.entities.Tweet tweet
    ) {
        this.taskMessage = taskMessage;
        this.tweetIdTwitter = tweetIdTwitter;
        this.tweetFromTwitter = tweetFromTwitter;
        this.tweet = tweet;
        this.ignoreTransformation = false;
    }

    public TweetMessage(
            TaskMessage taskMessage,
            org.woehlke.twitterwall.oodm.entities.Tweet myTweet,
            org.springframework.social.twitter.api.Tweet tweetFromTwitter
    ) {
        this.taskMessage = taskMessage;
        this.tweetFromTwitter = tweetFromTwitter;
        this.tweetIdTwitter = tweetFromTwitter.getId();
        this.tweet = myTweet;
        this.ignoreTransformation = false;
    }

    public TaskMessage getTaskMessage() {
        return taskMessage;
    }

    public long getTweetIdTwitter() {
        return tweetIdTwitter;
    }

    public Tweet getTweetFromTwitter() {
        return tweetFromTwitter;
    }

    public org.woehlke.twitterwall.oodm.entities.Tweet getTweet() {
        return tweet;
    }

    public boolean isIgnoreTransformation() {
        return ignoreTransformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TweetMessage)) return false;

        TweetMessage that = (TweetMessage) o;

        if (tweetIdTwitter != that.tweetIdTwitter) return false;
        if (ignoreTransformation != that.ignoreTransformation) return false;
        if (taskMessage != null ? !taskMessage.equals(that.taskMessage) : that.taskMessage != null) return false;
        if (tweetFromTwitter != null ? !tweetFromTwitter.equals(that.tweetFromTwitter) : that.tweetFromTwitter != null)
            return false;
        return tweet != null ? tweet.equals(that.tweet) : that.tweet == null;
    }

    @Override
    public int hashCode() {
        int result = taskMessage != null ? taskMessage.hashCode() : 0;
        result = 31 * result + (int) (tweetIdTwitter ^ (tweetIdTwitter >>> 32));
        result = 31 * result + (tweetFromTwitter != null ? tweetFromTwitter.hashCode() : 0);
        result = 31 * result + (tweet != null ? tweet.hashCode() : 0);
        result = 31 * result + (ignoreTransformation ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TweetMessage{" +
                "taskMessage=" + taskMessage +
                ", tweetIdTwitter=" + tweetIdTwitter +
                ", tweetFromTwitter=" + tweetFromTwitter +
                ", tweet=" + tweet +
                ", ignoreTransformation=" + ignoreTransformation +
                '}';
    }
}
