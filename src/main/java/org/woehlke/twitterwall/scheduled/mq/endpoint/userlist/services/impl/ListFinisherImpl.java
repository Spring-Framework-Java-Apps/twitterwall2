package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services.ListFinisher;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListResultList;

import java.util.List;

@Component("mqListFinisher")
public class ListFinisherImpl implements ListFinisher {

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @Override
    public Message<UserListResultList> finish(Message<List<UserListMessage>> incomingMessageList) {
        return null;
    }

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @Override
    public void finishAsnyc(Message<List<UserListMessage>> incomingMessageList) {

    }
}
