package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.EntitiesFilter;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.scheduled.service.transform.HashTagTransformService;

import java.util.Set;


/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class HashTagTransformServiceImpl extends EntitiesFilter implements HashTagTransformService {

    @Override
    public HashTag transform(HashTagEntity hashTag,Task task) {
        String text = hashTag.getText();
        HashTag myHashTagEntity = new HashTag(text, task);
        return myHashTagEntity;
    }

    @Override
    public Set<HashTag> getHashTagsFor(TwitterProfile userSource,Task tasks) {
        String description = userSource.getDescription();
        Set<HashTag> hashTagsTarget = getHashTagsForDescription(description,tasks);
        return hashTagsTarget;
    }
}
