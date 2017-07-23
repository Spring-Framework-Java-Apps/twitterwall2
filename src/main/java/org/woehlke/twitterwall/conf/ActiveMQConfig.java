package org.woehlke.twitterwall.conf;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class ActiveMQConfig {

    @Bean
    public Queue startTaskChannelJMSQueue() {
        return new ActiveMQQueue(QUEUE_START_TASK);
    }

    @Bean
    public Queue startUpdateTweetsChannelJMSQueue() {
        return new ActiveMQQueue(QUEUE_UPDATE_TWEETS);
    }

    @Bean
    public Queue startUpdateUserProfilesChannelJMSQueue() {
        return new ActiveMQQueue(QUEUE_UPDATE_USERS);
    }

    @Bean
    public Queue startUpdateUserProfilesFromMentionsChannelJMSQueue() {
        return new ActiveMQQueue(QUEUE_UPDATE_USERS_FROM_NENTIONS);
    }

    @Bean
    public Queue startFetchTweetsFromTwitterSearchChannelJMSQueue() {
        return new ActiveMQQueue(QUEUE_FETCH_TWEETS_FROM_TWITTER_SEARCH);
    }

    @Bean
    public Queue startFetchUsersFromDefinedUserListChannelJMSQueue() {
        return new ActiveMQQueue(HELLO_QUEUE_FETCH_USERS_FROM_LIST);
    }

    @Bean
    public Queue startFetchTestDataForUserChannelJMSQueue() {
        return new ActiveMQQueue(HELLO_QUEUE_FETCH_TESTDATA_USERS);
    }

    @Bean
    public Queue startFetchTestDataForTweetsChannelJMSQueue() {
        return new ActiveMQQueue(HELLO_QUEUE_FETCH_TESDATA_TWEETS);
    }

    @Bean
    public Queue startFetchImprintUserChannelJMSQueue() {
        return new ActiveMQQueue(HELLO_QUEUE_FETCH_IMPRINT_USER);
    }


    public static final String QUEUE_START_TASK = "startTaskChannel.queue";
    public static final String QUEUE_UPDATE_TWEETS = "startUpdateTweetsChannel.queue";
    public static final String QUEUE_UPDATE_USERS =  "startUpdateUserProfilesChannel.queue";
    public static final String QUEUE_UPDATE_USERS_FROM_NENTIONS = "startUpdateUserProfilesFromMentionsChannel.queue";
    public static final String QUEUE_FETCH_TWEETS_FROM_TWITTER_SEARCH =    "startFetchTweetsFromTwitterSearchChannel.queue";
    public static final String HELLO_QUEUE_FETCH_USERS_FROM_LIST =    "startFetchUsersFromDefinedUserListChannel.queue";
    public static final String HELLO_QUEUE_FETCH_TESTDATA_USERS =    "startFetchTestDataForUserChannel.queue";
    public static final String HELLO_QUEUE_FETCH_TESDATA_TWEETS =    "startFetchTestDataForTweetsChannel.queue";
    public static final String HELLO_QUEUE_FETCH_IMPRINT_USER =    "startFetchImprintUserChannel.queue";

}
