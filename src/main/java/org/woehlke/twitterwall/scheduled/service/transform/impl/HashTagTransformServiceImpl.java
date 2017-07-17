package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    public HashTag transform(HashTagEntity hashTag) {
        String text = hashTag.getText();
        int[] indices = hashTag.getIndices();
        HashTag myHashTagEntity = new HashTag(text, indices);
        return myHashTagEntity;
    }

    @Override
    public Set<HashTag> getHashTagsFor(TwitterProfile userSource) {
        String description = userSource.getDescription();
        Set<HashTag> hashTagsTarget = getHashTagsForDescription(description);
        return hashTagsTarget;
    }
}
