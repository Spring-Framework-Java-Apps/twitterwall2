package org.woehlke.twitterwall.backend.mq.endpoint.mentions.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.MentionMessage;

public interface UserforMentionLoader {

    Message<MentionMessage> fetchUserforMentionfromTwitter(Message<MentionMessage> incomingMessage);
}
