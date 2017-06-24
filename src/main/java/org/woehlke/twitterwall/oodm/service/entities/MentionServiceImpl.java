package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.repository.entities.MentionRepository;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class MentionServiceImpl implements MentionService {

    private final MentionRepository mentionRepository;

    @Autowired
    public MentionServiceImpl(MentionRepository mentionRepository) {
        this.mentionRepository = mentionRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Mention store(Mention mention) {
        return this.mentionRepository.persist(mention);
    }

    @Override
    public Mention findByIdTwitter(long idTwitter) {
        return this.mentionRepository.findByIdTwitter(idTwitter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Mention update(Mention mention) {
        return this.mentionRepository.update(mention);
    }

    @Override
    public Mention findByScreenNameAndName(Mention mention) {
        return this.mentionRepository.findByScreenNameAndName(mention.getScreenName(), mention.getName());
    }
}
