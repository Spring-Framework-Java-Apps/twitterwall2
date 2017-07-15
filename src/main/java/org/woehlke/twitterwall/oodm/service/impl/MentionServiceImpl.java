package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.dao.MentionDao;
import org.woehlke.twitterwall.oodm.repositories.MentionRepository;
import org.woehlke.twitterwall.oodm.service.MentionService;


/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class MentionServiceImpl implements MentionService {

    private static final Logger log = LoggerFactory.getLogger(MentionServiceImpl.class);

    private final MentionDao mentionDao;

    private final MentionRepository mentionRepository;

    @Autowired
    public MentionServiceImpl(MentionDao mentionDao, MentionRepository mentionRepository) {
        this.mentionDao = mentionDao;
        this.mentionRepository = mentionRepository;
    }

    @Override
    public Mention create(Mention mention, Task task) {
        mention.setCreatedBy(task);
        return this.mentionDao.persist(mention);
    }

    @Override
    public Mention findByIdTwitter(long idTwitter) {
        return this.mentionDao.findByIdTwitter(idTwitter,Mention.class);
    }

    @Override
    public Mention update(Mention mention, Task task) {
        mention.setUpdatedBy(task);
        return this.mentionDao.update(mention);
    }

    @Override
    public Page<Mention> getAll(Pageable pageRequest) {
        return this.mentionDao.getAll(Mention.class,pageRequest);
    }

    @Override
    public long count() {
        return this.mentionDao.count(Mention.class);
    }

    @Override
    public Mention store(Mention mention, Task task) {
        log.debug("try to store Mention: "+mention.toString());
        try {
            String screenName = mention.getScreenName();
            Long idTwitter = mention.getIdTwitter();
            Mention mentionPers = null;
            if(screenName != null && idTwitter != null){
                log.debug("try to find Mention.findByIdTwitterAndScreenName: "+mention.toString());
                mentionPers = this.mentionDao.findByIdTwitterAndScreenName(idTwitter,screenName);
            } else if (screenName != null && idTwitter == null) {
                log.debug("try to find Mention.findByScreenName: "+mention.toString());
                mentionPers = this.mentionDao.findByScreenName(screenName);
            } else if (screenName == null && idTwitter != null) {
                log.debug("try to find Mention.findByIdTwitter: "+mention.toString());
                mentionPers = this.mentionDao.findByIdTwitter(idTwitter,Mention.class);
            }
            //mentionPers.setIndices(mention.getIndices());
            mention.setId(mentionPers.getId());
            mention.setCreatedBy(mentionPers.getCreatedBy());
            mention.setUpdatedBy(task);
            log.debug("try to update Mention: "+mention.toString());
            return this.mentionDao.update(mentionPers);
        } catch (EmptyResultDataAccessException e){
            mention.setCreatedBy(task);
            log.debug("try to persist Mention: "+mention.toString());
            return this.mentionDao.persist(mention);
        }
    }

    @Override
    public Mention findByScreenName(String screenName) {
        return this.mentionDao.findByScreenName(screenName);
    }

    @Override
    public Mention createProxyMention(Mention mention, Task task) {
        long lowestIdTwitter = this.mentionDao.findLowestIdTwitter(mention);
        lowestIdTwitter--;
        mention.setIdTwitter(lowestIdTwitter);
        mention.setCreatedBy(task);
        mention = this.mentionDao.persist(mention);
        return mention;
    }
}
