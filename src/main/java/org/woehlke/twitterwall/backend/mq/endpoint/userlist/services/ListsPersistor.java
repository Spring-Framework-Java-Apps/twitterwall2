package org.woehlke.twitterwall.backend.mq.endpoint.userlist.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.UserListMessage;

public interface ListsPersistor {

    Message<UserListMessage> persistList(Message<UserListMessage> incomingMessage);
}
