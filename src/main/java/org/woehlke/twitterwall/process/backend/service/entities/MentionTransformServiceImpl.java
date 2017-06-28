package org.woehlke.twitterwall.process.backend.service.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.common.AbstractFormattedText;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;

import java.util.Date;
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

    @Override
    public Set<Mention> findByUser(User user) {
        String description = user.getDescription();
        Set<Mention> mentions = new LinkedHashSet<>();
        if (description != null) {
            Pattern mentionPattern1 = Pattern.compile("@("+User.SCREEN_NAME_PATTERN+")(" + AbstractFormattedText.stopChar + ")");
            Matcher m3 = mentionPattern1.matcher(description);
            while (m3.find()) {
                mentions.add(getMention(m3.group(1)));
            }
            Pattern mentionPattern2 = Pattern.compile("@("+User.SCREEN_NAME_PATTERN+")$");
            Matcher m4 = mentionPattern2.matcher(description);
            while (m4.find()) {
                mentions.add(getMention(m4.group(1)));
            }
        }
        return mentions;
    }

    private Mention getMention(String mentionString) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
        long idTwitter = new Date().getTime();
        String screenName = mentionString;
        String name = mentionString;
        int[] myindices = {};
        return new Mention(idTwitter, screenName, name, myindices);
    }
}
