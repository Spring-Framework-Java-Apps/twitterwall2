package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetResultList;

import java.util.List;

public interface TweetFinisher {

    TweetResultList finish(Message<List<TweetFromTwitter>> incomingMessageList);

    void finishAsnyc(Message<List<TweetFromTwitter>> incomingMessageList);
}
