package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TwitterProfileMessage;

import java.util.List;

public interface UpdateUserProfiles {

    List<TwitterProfileMessage> splitMessage(Message<TaskMessage> message);
}
