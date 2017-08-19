package org.woehlke.twitterwall.backend.mq.mentions.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.mentions.msg.MentionMessage;

public interface UpdateMentionWithUser {

    Message<MentionMessage> updateMentionWithUser(Message<MentionMessage> incomingMessage);
}
