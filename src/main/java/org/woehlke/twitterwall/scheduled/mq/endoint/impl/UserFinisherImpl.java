package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.mq.endoint.UserFinisher;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserResultList;

import java.util.ArrayList;
import java.util.List;

@Component("mqUserFinisher")
public class UserFinisherImpl implements UserFinisher {


    @Override
    public UserResultList finish(Message<List<UserMessage>> incomingMessageList) {
        long taskId = 0L;
        List<User> users = new ArrayList<>();
        List<UserMessage> userMessageList = incomingMessageList.getPayload();
        if(userMessageList.size()>0) {
            taskId = userMessageList.get(0).getTaskId();
        }
        for(UserMessage msg :userMessageList){
            users.add(msg.getUser());
        }
        UserResultList userResultList = new UserResultList(taskId,users);
        return userResultList;
    }
}
