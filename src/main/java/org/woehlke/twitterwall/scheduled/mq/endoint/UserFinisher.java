package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserResultList;

import java.util.List;

public interface UserFinisher {

    UserResultList finish(Message<List<UserMessage>> incomingMessageList);
}
