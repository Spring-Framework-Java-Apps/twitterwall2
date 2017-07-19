package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.repositories.MentionRepository;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.service.MentionService;


/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class MentionServiceImpl implements MentionService {

    private static final Logger log = LoggerFactory.getLogger(MentionServiceImpl.class);

    private final MentionRepository mentionRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public MentionServiceImpl(MentionRepository mentionRepository, TaskRepository taskRepository) {
        this.mentionRepository = mentionRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Mention create(Mention mention, Task task) {
        mention.setCreatedBy(task);
        return mentionRepository.save(mention);
    }

    @Override
    public Mention findByIdTwitter(long idTwitter) {
        return mentionRepository.findByIdTwitter(idTwitter);
    }

    @Override
    public Mention update(Mention mention, Task task) {
        mention.setUpdatedBy(task);
        return mentionRepository.save(mention);
    }

    @Override
    public Page<Mention> getAll(Pageable pageRequest) {
        return mentionRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return mentionRepository.count();
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
                mentionPers = mentionRepository.findByIdTwitterAndScreenName(idTwitter,screenName);
            } else if (screenName != null && idTwitter == null) {
                log.debug("try to find Mention.findByScreenName: "+mention.toString());
                mentionPers = mentionRepository.findByIdTwitterAndScreenName(idTwitter,screenName);
            } else if (screenName == null && idTwitter != null) {
                log.debug("try to find Mention.findByIdTwitter: "+mention.toString());
                mentionPers = mentionRepository.findByIdTwitter(idTwitter);
            }
            mention.setId(mentionPers.getId());
            mention.setCreatedBy(mentionPers.getCreatedBy());
            task = this.taskRepository.save(task);
            mention.setUpdatedBy(task);
            log.debug("try to update Mention: "+mention.toString());
            return mentionRepository.save(mentionPers);
        } catch (EmptyResultDataAccessException e){
            task = this.taskRepository.save(task);
            mention.setCreatedBy(task);
            log.debug("try to persist Mention: "+mention.toString());
            return mentionRepository.save(mention);
        }
    }

    @Override
    public Mention findByScreenName(String screenName) {
        return mentionRepository.findByScreenName(screenName);
    }

    @Override
    public Mention createProxyMention(Mention mention, Task task) {
        long lowestIdTwitter = 0;
        int page = 0;
        int pageSize = 1;
        Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.ASC,"idTwitter");
        Page<Mention> mentions = mentionRepository.findAll(pageRequest);
        if(mentions.hasContent()){
            lowestIdTwitter = mentions.getContent().iterator().next().getIdTwitter();
        }
        lowestIdTwitter--;
        mention.setIdTwitter(lowestIdTwitter);
        task = this.taskRepository.save(task);
        mention.setCreatedBy(task);
        mention = mentionRepository.save(mention);
        return mention;
    }
}
