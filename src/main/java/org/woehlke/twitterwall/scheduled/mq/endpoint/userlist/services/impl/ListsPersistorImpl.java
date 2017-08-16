package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services.ListsPersistor;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;

@Component("mqListsPersistor")
public class ListsPersistorImpl implements ListsPersistor {

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @Override
    public Message<UserListMessage> persistList(Message<UserListMessage> incomingMessage) {
        return null;
    }
}
