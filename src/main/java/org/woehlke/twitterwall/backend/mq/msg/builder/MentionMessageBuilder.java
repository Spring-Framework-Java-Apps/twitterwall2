package org.woehlke.twitterwall.backend.mq.msg.builder;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.model.Mention;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.backend.mq.msg.MentionMessage;
import org.woehlke.twitterwall.backend.mq.msg.TaskMessage;

public interface MentionMessageBuilder {

    Message<MentionMessage> buildMentionMessageForTask(Message<TaskMessage> incomingTaskMessage, Mention onePersMention);

    Message<MentionMessage> buildMentionMessage(Message<MentionMessage> incomingMessage, TwitterProfile userFromTwitter);

    Message<MentionMessage> buildMentionMessage(Message<MentionMessage> incomingMessage, User user);

    Message<MentionMessage> buildMentionMessage(Message<MentionMessage> incomingMessage, Mention mention);

}
