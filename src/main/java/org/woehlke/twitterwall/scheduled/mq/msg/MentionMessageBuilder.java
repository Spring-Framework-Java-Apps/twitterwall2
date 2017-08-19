package org.woehlke.twitterwall.scheduled.mq.msg;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.User;

public interface MentionMessageBuilder {

    Message<MentionMessage> buildMentionMessageForTask(Message<TaskMessage> incomingTaskMessage, Mention onePersMention);

    Message<MentionMessage> buildMentionMessage(Message<MentionMessage> incomingMessage, TwitterProfile userFromTwitter);

    Message<MentionMessage> buildMentionMessage(Message<MentionMessage> incomingMessage, User user);

    Message<MentionMessage> buildMentionMessage(Message<MentionMessage> incomingMessage, Mention mention);

}
