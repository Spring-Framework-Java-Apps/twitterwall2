package org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.splitter.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.splitter.FavoritesSplitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;

import java.util.List;

@Component("mqFavoritesSplitter")
public class FavoritesSplitterImpl implements FavoritesSplitter {
    @Override
    public List<Message<TweetMessage>> splitTweetMessage(Message<TaskMessage> message) {
        return null;
    }
}
