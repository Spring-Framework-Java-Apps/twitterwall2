package org.woehlke.twitterwall.oodm.service.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.common.AbstractFormattedText;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMentionByIdTwitterAndScreenNameException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMentionByIdTwitterException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMentionByScreenNameAndNameException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMentionByScreenNameException;
import org.woehlke.twitterwall.oodm.repository.entities.MentionRepository;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class MentionServiceImpl implements MentionService {

    private static final Logger log = LoggerFactory.getLogger(MentionServiceImpl.class);

    private final MentionRepository mentionRepository;

    @Autowired
    public MentionServiceImpl(MentionRepository mentionRepository) {
        this.mentionRepository = mentionRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Mention create(Mention mention) {
        return this.mentionRepository.persist(mention);
    }

    @Override
    public Mention findByIdTwitter(long idTwitter) {
        return this.mentionRepository.findByIdTwitter(idTwitter,Mention.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Mention update(Mention mention) {
        return this.mentionRepository.update(mention);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Mention store(Mention mention) {
        log.info("try to store Mention: "+mention.toString());
        try {
            String screenName = mention.getScreenName();
            Long idTwitter = mention.getIdTwitter();
            Mention mentionPers = null;
            if(screenName != null && idTwitter != null){
                log.info("try to find Mention.findByIdTwitterAndScreenName: "+mention.toString());
                mentionPers = this.mentionRepository.findByIdTwitterAndScreenName(idTwitter,screenName);
            } else if (screenName != null && idTwitter == null) {
                log.info("try to find Mention.findByScreenName: "+mention.toString());
                mentionPers = this.mentionRepository.findByScreenName(screenName);
            } else if (screenName == null && idTwitter != null) {
                log.info("try to find Mention.findByIdTwitter: "+mention.toString());
                mentionPers = this.mentionRepository.findByIdTwitter(idTwitter,Mention.class);
            }
            mentionPers.setIndices(mention.getIndices());
            mentionPers.setIdTwitter(mention.getIdTwitter());
            mentionPers.setName(mention.getName());
            mentionPers.setScreenName(mention.getScreenName());
            log.info("try to update Mention: "+mention.toString());
            return this.mentionRepository.update(mentionPers);
        } catch (NoResultException e){
            log.info(e.getMessage());
            log.info("try to persist Mention: "+mention.toString());
            return this.mentionRepository.persist(mention);
        }
    }

    @Override
    public Set<Mention> transform(List<MentionEntity> mentions) {
        Set<Mention> myMentionEntities = new LinkedHashSet<Mention>();
        for (MentionEntity mention : mentions) {
            long idTwitter = mention.getId();
            String screenName = mention.getScreenName();
            String name = mention.getName();
            int[] indices = mention.getIndices();
            Mention myMentionEntity = new Mention(idTwitter, screenName, name, indices);
            myMentionEntities.add(myMentionEntity);
        }
        return myMentionEntities;
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
