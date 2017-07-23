package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

public interface UserPersistor {

    User persistUser(Message<UserMessage> incomingUserMessage);
}
