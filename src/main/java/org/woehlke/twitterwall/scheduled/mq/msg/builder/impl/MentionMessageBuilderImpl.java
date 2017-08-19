package org.woehlke.twitterwall.scheduled.mq.msg.builder.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.mq.msg.MentionMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.builder.MentionMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;

@Component
public class MentionMessageBuilderImpl implements MentionMessageBuilder {

    @Override
    public Message<MentionMessage> buildMentionMessageForTask(Message<TaskMessage> incomingTaskMessage, Mention onePersMention) {
        MentionMessage outputPayload = new MentionMessage(
                incomingTaskMessage.getPayload(),
                onePersMention.getId(),
                onePersMention.getScreenName()
        );
        Message<MentionMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("mention_id", onePersMention.getId())
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<MentionMessage> buildMentionMessage(Message<MentionMessage> incomingMessage, TwitterProfile userFromTwitter) {
        MentionMessage outputPayload = new MentionMessage(
                incomingMessage.getPayload().getTaskMessage(),
                incomingMessage.getPayload().getMentionId(),
                incomingMessage.getPayload().getScreenName(),
                userFromTwitter
        );
        Message<MentionMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingMessage.getHeaders())
                        .setHeader("twitter_profile_id", userFromTwitter.getId())
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<MentionMessage> buildMentionMessage(Message<MentionMessage> incomingMessage, User user) {
        MentionMessage outMsg = new MentionMessage(
                incomingMessage.getPayload().getTaskMessage(),
                incomingMessage.getPayload().getMentionId(),
                incomingMessage.getPayload().getScreenName(),
                incomingMessage.getPayload().getTwitterProfile(),
                user
        );
        Message<MentionMessage> mqMessageOut =
                MessageBuilder.withPayload(outMsg)
                        .copyHeaders(incomingMessage.getHeaders())
                        .setHeader("transformed",Boolean.TRUE)
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<MentionMessage> buildMentionMessage(Message<MentionMessage> incomingMessage, Mention mention) {
        TwitterProfile twitterProfile = null;
        User user = null;
        MentionMessage outMsg = new MentionMessage(
                incomingMessage.getPayload().getTaskMessage(),
                incomingMessage.getPayload().getMentionId(),
                incomingMessage.getPayload().getScreenName(),
                twitterProfile,
                user,
                mention.getIdOfUser(),
                mention.getIdTwitterOfUser()
        );
        Message<MentionMessage> mqMessageOut =
                MessageBuilder.withPayload(outMsg)
                        .copyHeaders(incomingMessage.getHeaders())
                        .setHeader("transformed",Boolean.TRUE)
                        .build();
        return mqMessageOut;
    }

}
