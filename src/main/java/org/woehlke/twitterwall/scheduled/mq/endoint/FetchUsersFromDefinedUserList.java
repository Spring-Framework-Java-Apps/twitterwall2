package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TwitterProfileMessage;

import java.util.List;

public interface FetchUsersFromDefinedUserList {

    List<TwitterProfileMessage> splitMessage(Message<TaskMessage> message);
}
