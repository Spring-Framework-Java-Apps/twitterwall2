package org.woehlke.twitterwall.backend.mq.userlist.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;

import java.util.List;


public interface UserListCollector {

    Message<UserMessage> collectList(Message<List<UserListMessage>> incomingListOfUserMessage);
}
