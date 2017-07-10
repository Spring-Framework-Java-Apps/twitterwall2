package org.woehlke.twitterwall.oodm.service.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.repository.entities.MentionRepository;


import java.util.List;

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
    public Mention create(Mention mention) {
        return this.mentionRepository.persist(mention);
    }

    @Override
    public Mention findByIdTwitter(long idTwitter) {
        return this.mentionRepository.findByIdTwitter(idTwitter,Mention.class);
    }

    @Override
    public Mention update(Mention mention) {
        return this.mentionRepository.update(mention);
    }

    @Override
    public List<Mention> getAll() {
        return this.mentionRepository.getAll(Mention.class);
    }

    @Override
    public long count() {
        return this.mentionRepository.count(Mention.class);
    }

    @Override
    public Mention store(Mention mention) {
        log.debug("try to store Mention: "+mention.toString());
        try {
            String screenName = mention.getScreenName();
            Long idTwitter = mention.getIdTwitter();
            Mention mentionPers = null;
            if(screenName != null && idTwitter != null){
                log.debug("try to find Mention.findByIdTwitterAndScreenName: "+mention.toString());
                mentionPers = this.mentionRepository.findByIdTwitterAndScreenName(idTwitter,screenName);
            } else if (screenName != null && idTwitter == null) {
                log.debug("try to find Mention.findByScreenName: "+mention.toString());
                mentionPers = this.mentionRepository.findByScreenName(screenName);
            } else if (screenName == null && idTwitter != null) {
                log.debug("try to find Mention.findByIdTwitter: "+mention.toString());
                mentionPers = this.mentionRepository.findByIdTwitter(idTwitter,Mention.class);
            }
            mentionPers.setIndices(mention.getIndices());
            mentionPers.setIdTwitter(mention.getIdTwitter());
            mentionPers.setName(mention.getName());
            mentionPers.setScreenName(mention.getScreenName());
            log.debug("try to update Mention: "+mention.toString());
            return this.mentionRepository.update(mentionPers);
        } catch (EmptyResultDataAccessException e){
            log.debug(e.getMessage());
            log.debug("try to persist Mention: "+mention.toString());
            return this.mentionRepository.persist(mention);
        }
    }
}
