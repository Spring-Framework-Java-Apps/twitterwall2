package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.scheduled.service.transform.entities.HashTagTransformService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.oodm.entities.entities.HashTag.HASHTAG_TEXT_PATTERN;


/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class HashTagTransformServiceImpl implements HashTagTransformService {

    @Override
    public HashTag transform(HashTagEntity hashTag) {
        String text = hashTag.getText();
        int[] indices = hashTag.getIndices();
        HashTag myHashTagEntity = new HashTag(text, indices);
        return myHashTagEntity;
    }

    private Set<HashTag> getHashTagsForDescription(String description) {
        int[] indices = {};
        Set<HashTag> hashTags = new LinkedHashSet<>();
        if (description != null) {
            Pattern hashTagPattern = Pattern.compile("#("+HASHTAG_TEXT_PATTERN+")(" + Entities.stopChar + ")");
            Matcher m3 = hashTagPattern.matcher(description);
            while (m3.find()) {
                hashTags.add(new HashTag(m3.group(1), indices));
            }
            Pattern hashTagPattern2 = Pattern.compile("#("+HASHTAG_TEXT_PATTERN+")$");
            Matcher m4 = hashTagPattern2.matcher(description);
            while (m4.find()) {
                hashTags.add(new HashTag(m4.group(1), indices));
            }
        }
        return hashTags;
    }

    @Override
    public Set<HashTag> getHashTagsFor(TwitterProfile userSource) {
        //Map<String, Object> extraData = userSource.getExtraData();
        //Set<HashTag> hashTagsTarget = new LinkedHashSet<HashTag>();
        String description = userSource.getDescription();
        Set<HashTag> hashTagsTarget = getHashTagsForDescription(description);
        return hashTagsTarget;
    }
}
