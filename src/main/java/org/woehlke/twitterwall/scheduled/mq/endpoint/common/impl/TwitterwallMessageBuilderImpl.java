package org.woehlke.twitterwall.scheduled.mq.endpoint.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UserList;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.*;

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
        if(twitterProfile == null){
            log.error("buildUserMessage: TwitterProfile twitterProfile == null -  bust must not be null");
        }
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
        if(twitterProfile == null){
            log.error("buildUserMessage: TwitterProfile twitterProfile == null -  bust must not be null");
        }
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
        if(imprintUser == null){
            log.error("buildUserMessage: User imprintUser == null -  bust must not be null");
        }
        UserMessage outputPayload = new UserMessage(incomingMessage.getPayload(), imprintUser);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                    .copyHeaders(incomingMessage.getHeaders())
                    .setHeader("twitter_profile_id", imprintUser.getIdTwitter())
                    .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserListMessage> buildUserListMessage(Message<TaskMessage> incomingTaskMessage, UserList userList, int loopId, int loopAll) {
        UserListMessage outputPayload = new UserListMessage(incomingTaskMessage.getPayload(),userList);
        Message<UserListMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("loop_id",loopId)
                        .setHeader("loop_all",loopAll)
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<MentionMessage> buildMentionMessage(Message<TaskMessage> incomingTaskMessage, Mention onePersMention) {
        MentionMessage outputPayload = new MentionMessage(incomingTaskMessage.getPayload(),onePersMention.getId(),onePersMention.getScreenName());
        Message<MentionMessage> mqMessageOut =
            MessageBuilder.withPayload(outputPayload)
                .copyHeaders(incomingTaskMessage.getHeaders())
                .setHeader("mention_id", onePersMention.getId())
                .build();
        return mqMessageOut;
    }

    @Autowired
    public TwitterwallMessageBuilderImpl(TwitterProperties twitterProperties) {
        this.twitterProperties = twitterProperties;
    }

    private final TwitterProperties twitterProperties;

    private static final Logger log = LoggerFactory.getLogger(TwitterwallMessageBuilderImpl.class);
}
