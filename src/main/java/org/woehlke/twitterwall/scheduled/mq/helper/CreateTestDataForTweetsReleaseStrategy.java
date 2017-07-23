package org.woehlke.twitterwall.scheduled.mq.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.TwitterwallSchedulerProperties;

@Component("mqCreateTestDataForTweetsReleaseStrategy")
public class CreateTestDataForTweetsReleaseStrategy implements ReleaseStrategy {

    private final TwitterwallSchedulerProperties twitterwallSchedulerProperties;

    @Autowired
    public CreateTestDataForTweetsReleaseStrategy(TwitterwallSchedulerProperties twitterwallSchedulerProperties) {
        this.twitterwallSchedulerProperties = twitterwallSchedulerProperties;
    }

    @Override
    public boolean canRelease(MessageGroup group) {
        return ! (group.size() < twitterwallSchedulerProperties.getFacade().getIdTwitterToFetchForTweetTest().size());
    }
}
