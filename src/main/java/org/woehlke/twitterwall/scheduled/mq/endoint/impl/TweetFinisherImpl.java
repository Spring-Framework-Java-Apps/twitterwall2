package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.scheduled.mq.endoint.TweetFinisher;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetResultList;

import java.util.ArrayList;
import java.util.List;

@Component("mqTweetFinisher")
public class TweetFinisherImpl implements TweetFinisher {

    public TweetResultList finish(Message<List<TweetFromTwitter>> incomingMessageList){
        List<Tweet> resultList = new ArrayList<>();
        long taskId = 0L;
        for(TweetFromTwitter msg:incomingMessageList.getPayload()){
            resultList.add(msg.getTweet());
            taskId = msg.getTaskId();
        }
        TweetResultList result = new TweetResultList(taskId,resultList);
       return result;
   }
}
