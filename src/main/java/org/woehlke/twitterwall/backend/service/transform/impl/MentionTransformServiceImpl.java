package org.woehlke.twitterwall.backend.service.transform.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.entities.EntitiesFilter;
import org.woehlke.twitterwall.oodm.model.Mention;
import org.woehlke.twitterwall.backend.service.transform.MentionTransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
@Component
public class MentionTransformServiceImpl extends EntitiesFilter implements MentionTransformService {

    private static final Logger log = LoggerFactory.getLogger(MentionTransformServiceImpl.class);

    @Override
    public Mention transform(MentionEntity mention,Task task) {
        long idTwitter = mention.getId();
        String screenName = mention.getScreenName();
        String name = mention.getName();
        Mention myMentionEntity = Mention.createByTransformService(task, idTwitter, screenName, name);
        return myMentionEntity;
    }

    @Override
    public Set<Mention> findByUser(TwitterProfile userSource,Task task) {
        String description = userSource.getDescription();
        Set<Mention> mentionsTarget = super.findByUserDescription(description,task);
        return mentionsTarget;
    }

    @Override
    public Set<Mention> findByTweet(Tweet tweetSource, Task task) {
        String description = tweetSource.getText();
        Set<Mention> mentionsTarget = super.findByUserDescription(description,task);
        return mentionsTarget;
    }
}
