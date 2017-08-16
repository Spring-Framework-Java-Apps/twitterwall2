package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.ListsPersistor;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;

@Component("mqListsPersistor")
public class ListsPersistorImpl implements ListsPersistor {
    @Override
    public Message<UserListMessage> persistList(Message<UserListMessage> incomingMessage) {
        return null;
    }
}
