package org.woehlke.twitterwall.backend.mq.userlist.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;

public interface ListsTransformator {

    Message<UserListMessage> transformList(Message<UserListMessage> incomingMessage);
}
