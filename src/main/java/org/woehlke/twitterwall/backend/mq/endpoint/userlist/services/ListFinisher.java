package org.woehlke.twitterwall.backend.mq.endpoint.userlist.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.msg.results.UserListResultList;

import java.util.List;

public interface ListFinisher {

    Message<UserListResultList> finish(Message<List<UserListMessage>> incomingMessageList);

    void finishAsnyc(Message<List<UserListMessage>> incomingMessageList);
}
