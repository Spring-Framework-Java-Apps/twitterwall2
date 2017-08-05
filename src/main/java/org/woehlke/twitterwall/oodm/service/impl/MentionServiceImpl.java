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

    @Autowired
    public MentionServiceImpl(MentionRepository mentionRepository, TaskRepository taskRepository) {
        super(mentionRepository,taskRepository);
        this.mentionRepository = mentionRepository;
    }

    @Override
    public Mention findByIdTwitter(long idTwitter) {
        return mentionRepository.findByIdTwitter(idTwitter);
    }

    @Override
    public Mention findByScreenName(String screenName) {
        String screenNameUnique = screenName.toLowerCase();
        return mentionRepository.findByScreenNameUnique(screenNameUnique);
    }

    @Override
    public Mention createProxyMention(Mention mention, Task task) {
        Mention foundPers = mentionRepository.findByScreenNameUnique(mention.getScreenNameUnique());
        if(foundPers!=null){
           return foundPers;
        } else {
            mention.setIdTwitter(Mention.ID_TWITTER_UNDEFINED);
            mention.setCreatedBy(task);
            mention = mentionRepository.save(mention);
            return mention;
        }
    }

    @Override
    public Page<Mention> getAllWithoutUser(Pageable pageRequest) {
        return mentionRepository.findAllWithoutUser(pageRequest);
    }

    @Override
    public Mention findByScreenNameAndIdTwitter(String screenName, Long idTwitter) {
        String screenNameUnique = screenName.toLowerCase();
        return mentionRepository.findByScreenNameUniqueAndIdTwitter(screenNameUnique, idTwitter);
    }

}
