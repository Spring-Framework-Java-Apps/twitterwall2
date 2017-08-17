package org.woehlke.twitterwall.scheduled.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UserList;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

public interface TwitterwallMessageBuilder {

    Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, org.woehlke.twitterwall.oodm.entities.Tweet tweet, int loopId, int loopAll);

    Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, Tweet tweet, int loopId, int loopAll);

    Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, User userPers, int loopId, int loopAll);

    Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, TwitterProfile userProfiles, int loopId, int loopAll);

    Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, long twitterProfileId , int loopId, int loopAll);

    Message<TaskMessage> buildTaskMessage(Task task);

    Message<UserMessage> buildUserMessage(Message<UserMessage> incomingMessage, boolean isInStorage);

    Message<UserMessage> buildUserMessage(Message<UserMessage> incomingMessage, TwitterProfile twitterProfile, boolean ignoreTransformation);

    Message<UserMessage> buildUserMessage(Message<TaskMessage> mqMessageIn, TwitterProfile twitterProfile);

    Message<UserMessage> buildUserMessage(Message<TaskMessage> mqMessageIn, User imprintUser);

    Message<UserListMessage> buildUserListMessage(Message<TaskMessage> incomingTaskMessage, UserList userList, int loopId, int loopAll);
}
