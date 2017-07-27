package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MentionServiceImpl extends DomainServiceWithTaskImpl<Mention> implements MentionService {

    private static final Logger log = LoggerFactory.getLogger(MentionServiceImpl.class);

    private final MentionRepository mentionRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public MentionServiceImpl(MentionRepository mentionRepository, TaskRepository taskRepository) {
        super(mentionRepository,taskRepository);
        this.mentionRepository = mentionRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Mention findByIdTwitter(long idTwitter) {
        return mentionRepository.findByIdTwitter(idTwitter);
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
        mention.setCreatedBy(task);
        task = this.taskRepository.save(task);
        mention.setCreatedBy(task);
        mention = mentionRepository.save(mention);
        return mention;
    }

    @Override
    public Page<Mention> getAllWithoutPersistentUser(Pageable pageRequest) {
        return mentionRepository.findAllByUserNull(pageRequest);
    }

}
