package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services.ListsTransformator;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;

@Component("mqListsTransformator")
public class ListsTransformatorImpl implements ListsTransformator {

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @Override
    public Message<UserListMessage> transformList(Message<UserListMessage> incomingMessage) {
        return null;
    }
}
