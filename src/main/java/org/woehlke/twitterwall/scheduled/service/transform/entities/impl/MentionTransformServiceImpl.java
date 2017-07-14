package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.scheduled.service.transform.entities.MentionTransformService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class MentionTransformServiceImpl implements MentionTransformService {

    private static final Logger log = LoggerFactory.getLogger(MentionTransformServiceImpl.class);

    @Override
    public Mention transform(MentionEntity mention) {
        long idTwitter = mention.getId();
        String screenName = mention.getScreenName();
        String name = mention.getName();
        int[] indices = mention.getIndices();
        Mention myMentionEntity = new Mention(idTwitter, screenName, name, indices);
        return myMentionEntity;
    }

    private Set<Mention> findByUserDescription(String description) {
        Set<Mention> mentions = new LinkedHashSet<>();
        if (description != null) {
            Pattern mentionPattern1 = Pattern.compile("@("+User.SCREEN_NAME_PATTERN+")(" + Entities.stopChar + ")");
            Matcher m3 = mentionPattern1.matcher(description);
            while (m3.find()) {
                Mention newMention = Mention.getMention(m3.group(1));
                if(!mentions.contains(newMention)){
                    mentions.add(newMention);
                }
            }
            Pattern mentionPattern2 = Pattern.compile("@("+User.SCREEN_NAME_PATTERN+")$");
            Matcher m4 = mentionPattern2.matcher(description);
            while (m4.find()) {
                Mention newMention = Mention.getMention(m4.group(1));
                if(!mentions.contains(newMention)){
                    mentions.add(newMention);
                }
            }
        }
        return mentions;
    }

    @Override
    public Set<Mention> findByUser(TwitterProfile userSource) {
        String description = userSource.getDescription();
        Set<Mention> mentionsTarget = findByUserDescription(description);
        return mentionsTarget;
    }
}
