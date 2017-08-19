package org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.MentionMessage;

public interface UpdateMentionWithUser {

    Message<MentionMessage> updateMentionWithUser(Message<MentionMessage> incomingMessage);
}
