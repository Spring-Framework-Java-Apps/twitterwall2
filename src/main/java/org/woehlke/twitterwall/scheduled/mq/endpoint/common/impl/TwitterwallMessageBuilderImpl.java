package org.woehlke.twitterwall.scheduled.mq.endpoint.common.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

@Component
public class TwitterwallMessageBuilderImpl implements TwitterwallMessageBuilder {

    @Override
    public Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, org.woehlke.twitterwall.oodm.entities.Tweet tweet, int loopId, int loopAll){
        TweetMessage outputPayload = new TweetMessage(incomingTaskMessage.getPayload(),tweet);
        Message<TweetMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("loop_id",loopId)
                        .setHeader("loop_all",loopAll)
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, Tweet tweet, int loopId, int loopAll){
        TweetMessage outputPayload = new TweetMessage(incomingTaskMessage.getPayload(),tweet);
        Message<TweetMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("loop_id",loopId)
                        .setHeader("loop_all",loopAll)
                        .build();
        return mqMessageOut;
    }

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
                        .setHeader("tw_lfd_nr",loopId)
                        .setHeader("tw_all",loopAll)
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
    public Message<TaskMessage> buildTaskMessage(Task task) {
        TaskMessage outputPayload = new TaskMessage(task.getId(), task.getTaskType(), task.getSendType(), task.getTimeStarted());
        Message<TaskMessage> mqMessage =
                MessageBuilder.withPayload(outputPayload)
                    .setHeader("task_id", task.getId())
                    .setHeader("task_uid", task.getUniqueId())
                    .setHeader("task_type", task.getTaskType())
                    .setHeader("time_started", task.getTimeStarted().getTime())
                    .setHeader("send_type", task.getSendType())
                    .build();
        return mqMessage;
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
