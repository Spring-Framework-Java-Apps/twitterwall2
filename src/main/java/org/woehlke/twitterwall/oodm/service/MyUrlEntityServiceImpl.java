package org.woehlke.twitterwall.oodm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.MyUrlEntity;
import org.woehlke.twitterwall.oodm.repository.MyUrlEntityRepository;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED,readOnly = true)
public class MyUrlEntityServiceImpl implements MyUrlEntityService {

    private final MyUrlEntityRepository myUrlEntityRepository;

    @Autowired
    public MyUrlEntityServiceImpl(MyUrlEntityRepository myUrlEntityRepository) {
        this.myUrlEntityRepository = myUrlEntityRepository;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public MyUrlEntity store(MyUrlEntity url) {
        return this.myUrlEntityRepository.persist(url);
    }
}
