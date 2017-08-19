package org.woehlke.twitterwall.backend.mq.endpoint.tweets.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.tweets.msg.TweetMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.tweets.msg.TweetResultList;

import java.util.List;

public interface TweetFinisher {

    Message<TweetResultList> finish(Message<List<TweetMessage>> incomingMessageList);

    void finishAsnyc(Message<List<TweetMessage>> incomingMessageList);
}
