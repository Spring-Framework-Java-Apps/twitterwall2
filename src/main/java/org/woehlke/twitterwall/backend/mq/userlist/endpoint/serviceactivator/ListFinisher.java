package org.woehlke.twitterwall.backend.mq.userlist.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListResultList;

import java.util.List;

public interface ListFinisher {

    Message<UserListResultList> finish(Message<List<UserListMessage>> incomingMessageList);

    void finishAsnyc(Message<List<UserListMessage>> incomingMessageList);
}
