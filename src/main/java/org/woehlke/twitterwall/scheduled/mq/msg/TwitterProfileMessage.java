package org.woehlke.twitterwall.scheduled.mq.msg;

import org.springframework.social.twitter.api.TwitterProfile;

import java.io.Serializable;

public class TwitterProfileMessage implements Serializable {

    private final TaskMessage taskMessage;
    private final String screenName;
    private final TwitterProfile twitterProfile;

    public TwitterProfileMessage(TaskMessage taskMessage, TwitterProfile twitterProfile){
        this.taskMessage = taskMessage;
        this.screenName = twitterProfile.getScreenName();
        this.twitterProfile = twitterProfile;
    }

    public TwitterProfileMessage(TaskMessage taskMessage, String screenName, TwitterProfile twitterProfile) {
        this.taskMessage = taskMessage;
        this.screenName = screenName;
        this.twitterProfile = twitterProfile;
    }

    public String getScreenName() {
        return screenName;
    }

    public TwitterProfile getTwitterProfile() {
        return twitterProfile;
    }

    public TaskMessage getTaskMessage() {
        return taskMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwitterProfileMessage)) return false;

        TwitterProfileMessage that = (TwitterProfileMessage) o;

        if (getTaskMessage() != null ? !getTaskMessage().equals(that.getTaskMessage()) : that.getTaskMessage() != null)
            return false;
        if (getScreenName() != null ? !getScreenName().equals(that.getScreenName()) : that.getScreenName() != null)
            return false;
        return getTwitterProfile() != null ? getTwitterProfile().equals(that.getTwitterProfile()) : that.getTwitterProfile() == null;
    }

    @Override
    public int hashCode() {
        int result = getTaskMessage() != null ? getTaskMessage().hashCode() : 0;
        result = 31 * result + (getScreenName() != null ? getScreenName().hashCode() : 0);
        result = 31 * result + (getTwitterProfile() != null ? getTwitterProfile().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TwitterProfileMessage{" +
            "taskMessage=" + taskMessage +
            ", screenName='" + screenName + '\'' +
            ", twitterProfile=" + twitterProfile +
            '}';
    }
}
