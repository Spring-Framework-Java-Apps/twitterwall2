package org.woehlke.twitterwall.schedulled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.schedulled.mq.msg.TwitterProfileMessage;
import org.woehlke.twitterwall.schedulled.mq.msg.UserMessage;

public interface UserTransformator {

    Message<UserMessage> transformUser(Message<TwitterProfileMessage> mqMessage);
}
