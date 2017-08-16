package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;

public interface ListsTransformator {

    Message<UserListMessage> transformList(Message<UserListMessage> incomingMessage);
}
