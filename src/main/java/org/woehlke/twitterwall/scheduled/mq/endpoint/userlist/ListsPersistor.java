package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;

public interface ListsPersistor {

    Message<UserListMessage> persistList(Message<UserListMessage> incomingMessage);
}
