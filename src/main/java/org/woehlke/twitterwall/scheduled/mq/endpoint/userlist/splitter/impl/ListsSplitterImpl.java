package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.splitter.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.splitter.ListsSplitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;

import java.util.List;

@Component("mqListsSplitter")
public class ListsSplitterImpl implements ListsSplitter {

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @Override
    public List<Message<UserListMessage>> splitUserListMessage(Message<TaskMessage> incomingTaskMessage) {
        return null;
    }
}
