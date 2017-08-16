package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.ListsTransformator;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;

@Component("mqListsTransformator")
public class ListsTransformatorImpl implements ListsTransformator {
    @Override
    public Message<UserListMessage> transformList(Message<UserListMessage> incomingMessage) {
        return null;
    }
}
