package org.woehlke.twitterwall.backend.mq.endpoint.mentions.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.mentions.msg.MentionMessage;

public interface UpdateMentionWithUser {

    Message<MentionMessage> updateMentionWithUser(Message<MentionMessage> incomingMessage);
}
