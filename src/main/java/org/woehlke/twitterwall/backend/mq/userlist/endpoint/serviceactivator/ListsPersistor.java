package org.woehlke.twitterwall.backend.mq.userlist.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;

public interface ListsPersistor {

    Message<UserListMessage> persistList(Message<UserListMessage> incomingMessage);
}
