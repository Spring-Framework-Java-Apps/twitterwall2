package org.woehlke.twitterwall.oodm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.MyMediaEntity;
import org.woehlke.twitterwall.oodm.repository.MyMediaEntityRepository;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED,readOnly = true)
public class MyMediaEntityServiceImpl implements MyMediaEntityService {

    private final MyMediaEntityRepository myMediaEntityRepository;

    @Autowired
    public MyMediaEntityServiceImpl(MyMediaEntityRepository myMediaEntityRepository) {
        this.myMediaEntityRepository = myMediaEntityRepository;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public MyMediaEntity store(MyMediaEntity media) {
        return this.myMediaEntityRepository.persist(media);
    }

    @Override
    public MyMediaEntity findByIdTwitter(long idTwitter) {
        return this.myMediaEntityRepository.findByIdTwitter(idTwitter);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public MyMediaEntity update(MyMediaEntity media) {
        return this.myMediaEntityRepository.update(media);
    }
}
