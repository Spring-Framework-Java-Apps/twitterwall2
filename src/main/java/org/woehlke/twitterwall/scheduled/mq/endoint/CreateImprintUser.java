package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TwitterProfileMessage;

public interface CreateImprintUser {

    Message<TwitterProfileMessage> createImprintUser(Message<TaskMessage> mqMessage);
}
