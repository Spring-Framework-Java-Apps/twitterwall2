package org.woehlke.twitterwall.backend.mq.endpoint.userlist.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.UserListMessage;

public interface ListsTransformator {

    Message<UserListMessage> transformList(Message<UserListMessage> incomingMessage);
}
