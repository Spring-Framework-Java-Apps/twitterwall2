package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.ListFinisher;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListResultList;

import java.util.List;

@Component("mqListFinisher")
public class ListFinisherImpl implements ListFinisher {
    @Override
    public Message<UserListResultList> finish(Message<List<UserListMessage>> incomingMessageList) {
        return null;
    }

    @Override
    public void finishAsnyc(Message<List<UserListMessage>> incomingMessageList) {

    }
}
