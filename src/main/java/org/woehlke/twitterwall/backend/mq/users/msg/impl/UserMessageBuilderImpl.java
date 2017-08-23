package org.woehlke.twitterwall.backend.mq.users.msg.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessageBuilder;

@Component
public class UserMessageBuilderImpl implements UserMessageBuilder {

    @Override
    public Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, User userPers, int loopId, int loopAll){
        UserMessage outputPayload = new UserMessage(incomingTaskMessage.getPayload(),userPers);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("taskId",incomingTaskMessage.getPayload().getTaskId())
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, TwitterProfile userProfiles, int loopId, int loopAll){
        UserMessage outputPayload = new UserMessage(incomingTaskMessage.getPayload(),userProfiles);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("taskId",incomingTaskMessage.getPayload().getTaskId())
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, long twitterProfileId, int loopId, int loopAll) {
        UserMessage outputPayload = new UserMessage(incomingTaskMessage.getPayload(),twitterProfileId);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("taskId",incomingTaskMessage.getPayload().getTaskId())
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserMessage> buildUserMessage(Message<UserMessage> incomingMessage, boolean isInStorage) {
        UserMessage outputPayload = new UserMessage(incomingMessage.getPayload().getTaskMessage(),incomingMessage.getPayload().getTwitterProfileId(),isInStorage);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingMessage.getHeaders())
                        .setHeader("checked_storage",Boolean.TRUE)
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserMessage> buildUserMessage(Message<UserMessage> incomingMessage, TwitterProfile twitterProfile, boolean ignoreTransformation) {
        UserMessage outputPayload = new UserMessage(incomingMessage.getPayload().getTaskMessage(),twitterProfile,ignoreTransformation);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingMessage.getHeaders())
                        .setHeader("checked_storage",Boolean.TRUE)
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingMessage, TwitterProfile twitterProfile) {
        UserMessage outputPayload = new UserMessage(incomingMessage.getPayload(),twitterProfile);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingMessage.getHeaders())
                        .setHeader("taskId",incomingMessage.getPayload().getTaskId())
                        .setHeader("twitter_profile_id", twitterProfile.getId())
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingMessage, User imprintUser) {
        UserMessage outputPayload = new UserMessage(incomingMessage.getPayload(), imprintUser);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingMessage.getHeaders())
                        .setHeader("taskId",incomingMessage.getPayload().getTaskId())
                        .setHeader("twitter_profile_id", imprintUser.getIdTwitter())
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserMessage> buildUserMessageForUser(Message<TaskMessage> incomingTaskMessage, long userIdTwitter, int loopId, int loopAll) {
        UserMessage outputPayload = new UserMessage(incomingTaskMessage.getPayload(),userIdTwitter);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("taskId",incomingTaskMessage.getPayload().getTaskId())
                        .setHeader("userIdTwitter",userIdTwitter)
                        .build();
        return mqMessageOut;
    }


}
