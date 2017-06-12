package org.woehlke.twitterwall.oodm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.MyHashTagEntity;
import org.woehlke.twitterwall.oodm.repository.MyHashTagEntityRepository;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED,readOnly = true)
public class MyHashTagEntityServiceImpl implements MyHashTagEntityService {

    private final MyHashTagEntityRepository myHashTagEntityRepository;

    @Autowired
    public MyHashTagEntityServiceImpl(MyHashTagEntityRepository myHashTagEntityRepository) {
        this.myHashTagEntityRepository = myHashTagEntityRepository;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public MyHashTagEntity store(MyHashTagEntity tag) {
        return this.myHashTagEntityRepository.persist(tag);
    }
}
