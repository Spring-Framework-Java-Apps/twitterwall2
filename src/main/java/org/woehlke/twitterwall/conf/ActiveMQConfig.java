package org.woehlke.twitterwall.conf;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class ActiveMQConfig {

    public static final String HELLO_QUEUE = "hello.queue";

    @Bean
    public Queue helloJMSQueue() {

        return new ActiveMQQueue(HELLO_QUEUE);

    }


    public static final String QUEUE_START_TASK = "startTaskChannel";

    public static final String QUEUE_UPDATE_TWEETS = "startUpdateTweetsChannel";
    public static final String QUEUE_UPDATE_USERS =  "startUpdateUserProfilesChannel";
    public static final String QUEUE_UPDATE_USERS_FROM_NENTIONS = "startUpdateUserProfilesFromMentionsChannel";

    public static final String QUEUE_FETCH_TWEETS_FROM_TWITTER_SEARCH =    "startFetchTweetsFromTwitterSearchChannel";
    public static final String HELLO_QUEUE_FETCH_USERS_FROM_LIST =    "startFetchUsersFromDefinedUserListChannel";
    public static final String HELLO_QUEUE_FETCH_TESTDATA_USERS =    "startFetchTestDataForUserChannel";
    public static final String HELLO_QUEUE_FETCH_TESDATA_TWEETS =    "startFetchTestDataForTweetsChannel" ;
    public static final String HELLO_QUEUE_FETCH_IMPRINT_USER =    "startFetchImprintUserChannel"    ;


}
