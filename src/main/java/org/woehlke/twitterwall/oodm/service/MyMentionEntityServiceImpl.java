package org.woehlke.twitterwall.oodm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.MyMentionEntity;
import org.woehlke.twitterwall.oodm.repository.MyMentionEntityRepository;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED,readOnly = true)
public class MyMentionEntityServiceImpl implements MyMentionEntityService {

    private final MyMentionEntityRepository myMentionEntityRepository;

    @Autowired
    public MyMentionEntityServiceImpl(MyMentionEntityRepository myMentionEntityRepository) {
        this.myMentionEntityRepository = myMentionEntityRepository;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public MyMentionEntity store(MyMentionEntity mention) {
        return this.myMentionEntityRepository.persist(mention);
    }

    @Override
    public MyMentionEntity findByIdTwitter(long idTwitter) {
        return this.myMentionEntityRepository.findByIdTwitter(idTwitter);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public MyMentionEntity update(MyMentionEntity mention) {
        return this.myMentionEntityRepository.update(mention);
    }
}
