package org.woehlke.twitterwall.scheduled.mq.msg.builder.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.builder.UserMessageBuilder;

@Component
public class UserMessageBuilderImpl implements UserMessageBuilder {

    @Override
    public Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, User userPers, int loopId, int loopAll){
        UserMessage outputPayload = new UserMessage(incomingTaskMessage.getPayload(),userPers);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("loop_id",loopId)
                        .setHeader("loop_all",loopAll)
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, TwitterProfile userProfiles, int loopId, int loopAll){
        UserMessage outputPayload = new UserMessage(incomingTaskMessage.getPayload(),userProfiles);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("loop_id",loopId)
                        .setHeader("loop_all",loopAll)
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserMessage> buildUserMessage(Message<TaskMessage> incomingTaskMessage, long twitterProfileId, int loopId, int loopAll) {
        UserMessage outputPayload = new UserMessage(incomingTaskMessage.getPayload(),twitterProfileId);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("loop_id",loopId)
                        .setHeader("loop_all",loopAll)
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
                        .setHeader("twitter_profile_id", imprintUser.getIdTwitter())
                        .build();
        return mqMessageOut;
    }


}
