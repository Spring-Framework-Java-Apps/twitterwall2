package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

import java.util.List;

public interface UpdateUserProfilesFromMentions {

    List<UserMessage> splitMessage(Message<TaskMessage> message);
}
