package org.woehlke.twitterwall.backend.mq.endpoint.userlist.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg.UserListMessage;

public interface ListsPersistor {

    Message<UserListMessage> persistList(Message<UserListMessage> incomingMessage);
}
