package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TwitterProfileMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

public interface UserTransformator {

    Message<UserMessage> transformUser(Message<TwitterProfileMessage> mqMessage);
}
