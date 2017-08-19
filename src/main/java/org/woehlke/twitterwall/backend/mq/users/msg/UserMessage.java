package org.woehlke.twitterwall.backend.mq.users.msg;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.oodm.model.User;

import java.io.Serializable;

public class UserMessage implements Serializable {

    private final TaskMessage taskMessage;
    private final String screenName;
    private final TwitterProfile twitterProfile;
    private final Long twitterProfileId;
    private final User user;
    private final boolean ignoreTransformation;

    public UserMessage(TaskMessage taskMessage, long twitterProfileId){
        this.taskMessage = taskMessage;
        this.screenName = null;
        this.twitterProfileId = twitterProfileId;
        this.twitterProfile = null;
        this.user = null;
        this.ignoreTransformation = false;
    }

    public UserMessage(TaskMessage taskMessage, long twitterProfileId, boolean ignoreTransformation){
        this.taskMessage = taskMessage;
        this.screenName = null;
        this.twitterProfileId = twitterProfileId;
        this.twitterProfile = null;
        this.user = null;
        this.ignoreTransformation = ignoreTransformation;
    }

    public UserMessage(TaskMessage taskMessage, TwitterProfile twitterProfile, boolean ignoreTransformation){
        this.taskMessage = taskMessage;
        this.screenName = twitterProfile.getScreenName();
        this.twitterProfileId = twitterProfile.getId();
        this.twitterProfile = twitterProfile;
        this.user = null;
        this.ignoreTransformation = ignoreTransformation;
    }

    public UserMessage(TaskMessage taskMessage, TwitterProfile twitterProfile){
        this.taskMessage = taskMessage;
        this.screenName = twitterProfile.getScreenName();
        this.twitterProfile = twitterProfile;
        this.twitterProfileId = null;
        this.user = null;
        this.ignoreTransformation = false;
    }

    @Deprecated
    public UserMessage(TaskMessage taskMessage, TwitterProfile twitterProfile, String screenName) {
        this.taskMessage = taskMessage;
        this.screenName = screenName;
        this.twitterProfile = twitterProfile;
        this.twitterProfileId = twitterProfile.getId();
        this.user = null;
        this.ignoreTransformation = false;
    }

    public UserMessage(TaskMessage taskMessage, User user){
        this.taskMessage = taskMessage;
        this.screenName = user.getScreenName();
        this.twitterProfile = null;
        this.twitterProfileId = user.getIdTwitter();
        this.user = user;
        this.ignoreTransformation = true;
    }

    public UserMessage(TaskMessage taskMessage, User user, boolean ignoreTransformation){
        this.taskMessage = taskMessage;
        this.screenName = user.getScreenName();
        this.twitterProfile = null;
        this.twitterProfileId = user.getIdTwitter();
        this.user = user;
        this.ignoreTransformation = ignoreTransformation;
    }

    public UserMessage(TaskMessage taskMessage, TwitterProfile twitterProfile, User user) {
        //Assert.that(twitterProfile.getScreenName().compareTo(user.getScreenName())==0,"ScreenName must be the same on TwitterProfile and User");
        //Assert.that(twitterProfile.getId() == user.getIdTwitter(),"twitterProfileId must be the same on TwitterProfile and User");
        this.taskMessage = taskMessage;
        this.screenName = twitterProfile.getScreenName();
        this.twitterProfile = twitterProfile;
        this.twitterProfileId = twitterProfile.getId();
        this.user = user;
        this.ignoreTransformation = false;
    }

    public UserMessage(
            TaskMessage taskMessage,
            TwitterProfile twitterProfile,
            User user,
            boolean ignoreTransformation
    ) {
        //Assert.that(twitterProfile.getScreenName().compareTo(user.getScreenName())==0,"ScreenName must be the same on TwitterProfile and User");
        //Assert.that(twitterProfile.getId() == user.getIdTwitter(),"twitterProfileId must be the same on TwitterProfile and User");
        this.taskMessage = taskMessage;
        this.screenName = twitterProfile.getScreenName();
        this.twitterProfile = twitterProfile;
        this.twitterProfileId = twitterProfile.getId();
        this.user = user;
        this.ignoreTransformation = ignoreTransformation;
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

    public User getUser() {
        return user;
    }

    public boolean isIgnoreTransformation() {
        return ignoreTransformation;
    }

    public Long getTwitterProfileId() {
        return twitterProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMessage)) return false;

        UserMessage that = (UserMessage) o;

        if (ignoreTransformation != that.ignoreTransformation) return false;
        if (taskMessage != null ? !taskMessage.equals(that.taskMessage) : that.taskMessage != null) return false;
        if (screenName != null ? !screenName.equals(that.screenName) : that.screenName != null) return false;
        if (twitterProfile != null ? !twitterProfile.equals(that.twitterProfile) : that.twitterProfile != null)
            return false;
        if (twitterProfileId != null ? !twitterProfileId.equals(that.twitterProfileId) : that.twitterProfileId != null)
            return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = taskMessage != null ? taskMessage.hashCode() : 0;
        result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
        result = 31 * result + (twitterProfile != null ? twitterProfile.hashCode() : 0);
        result = 31 * result + (twitterProfileId != null ? twitterProfileId.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (ignoreTransformation ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "taskMessage=" + taskMessage +
                ", screenName='" + screenName + '\'' +
                ", twitterProfile=" + twitterProfile +
                ", twitterProfileId=" + twitterProfileId +
                ", user=" + user +
                ", ignoreTransformation=" + ignoreTransformation +
                '}';
    }
}
