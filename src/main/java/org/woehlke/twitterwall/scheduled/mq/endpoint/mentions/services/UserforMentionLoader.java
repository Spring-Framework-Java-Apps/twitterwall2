package org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.MentionMessage;

public interface UserforMentionLoader {

    Message<MentionMessage> fetchUserforMentionfromTwitter(Message<MentionMessage> incomingMessage);
}
