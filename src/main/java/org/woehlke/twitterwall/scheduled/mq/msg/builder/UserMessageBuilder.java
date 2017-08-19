package org.woehlke.twitterwall.scheduled.mq.msg.builder;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

public interface UserMessageBuilder {

    Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, User userPers, int loopId, int loopAll);

    Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, TwitterProfile userProfiles, int loopId, int loopAll);

    Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, long twitterProfileId , int loopId, int loopAll);

    Message<UserMessage> buildUserMessage(Message<UserMessage> incomingMessage, boolean isInStorage);

    Message<UserMessage> buildUserMessage(Message<UserMessage> incomingMessage, TwitterProfile twitterProfile, boolean ignoreTransformation);

    Message<UserMessage> buildUserMessage(Message<TaskMessage> mqMessageIn, TwitterProfile twitterProfile);

    Message<UserMessage> buildUserMessage(Message<TaskMessage> mqMessageIn, User imprintUser);

}
