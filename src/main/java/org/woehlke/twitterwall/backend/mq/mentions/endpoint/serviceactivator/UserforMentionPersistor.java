package org.woehlke.twitterwall.backend.mq.mentions.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.mentions.msg.MentionMessage;

public interface UserforMentionPersistor {

    Message<MentionMessage> persistUserforMention(Message<MentionMessage> incomingMessage);
}
