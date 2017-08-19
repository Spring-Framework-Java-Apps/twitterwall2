package org.woehlke.twitterwall.backend.mq.endpoint.mentions.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.MentionMessage;
import org.woehlke.twitterwall.backend.mq.msg.results.MentionResultList;

import java.util.List;

public interface MentionFinisher {

    Message<MentionResultList> finish(Message<List<MentionMessage>> incomingMessageList);

    void finishAsnyc(Message<List<MentionMessage>> incomingMessageList);
}
