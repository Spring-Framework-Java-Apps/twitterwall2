package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListResultList;

import java.util.List;

public interface ListFinisher {

    Message<UserListResultList> finish(Message<List<UserListMessage>> incomingMessageList);

    void finishAsnyc(Message<List<UserListMessage>> incomingMessageList);
}
