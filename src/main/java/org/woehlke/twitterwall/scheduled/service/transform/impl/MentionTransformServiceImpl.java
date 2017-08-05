package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.EntitiesFilter;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.scheduled.service.transform.MentionTransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */

@Component
//@Service
//@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class MentionTransformServiceImpl extends EntitiesFilter implements MentionTransformService {

    private static final Logger log = LoggerFactory.getLogger(MentionTransformServiceImpl.class);

    @Override
    public Mention transform(MentionEntity mention,Task task) {
        long idTwitter = mention.getId();
        String screenNameUnique = mention.getScreenName();
        String name = mention.getName();
        Mention myMentionEntity = new Mention(task,null,idTwitter, screenNameUnique, name);
        return myMentionEntity;
    }

    @Override
    public Set<Mention> findByUser(TwitterProfile userSource,Task task) {
        String description = userSource.getDescription();
        Set<Mention> mentionsTarget = super.findByUserDescription(description,task);
        return mentionsTarget;
    }
}
