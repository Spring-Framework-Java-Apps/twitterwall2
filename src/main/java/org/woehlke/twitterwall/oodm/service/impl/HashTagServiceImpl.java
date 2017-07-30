package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.repositories.HashTagRepository;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.service.HashTagService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class HashTagServiceImpl extends DomainServiceWithTaskImpl<HashTag> implements HashTagService {

    private static final Logger log = LoggerFactory.getLogger(HashTagServiceImpl.class);

    private final HashTagRepository hashTagRepository;

    @Autowired
    public HashTagServiceImpl(HashTagRepository hashTagRepository, TaskRepository taskRepository) {
        super(hashTagRepository,taskRepository);
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    public HashTag findByText(String text) {
        return hashTagRepository.findByText(text);
    }

    @Override
    public HashTag findByUniqueId(HashTag example) {
        return hashTagRepository.findByUniqueId(example);
    }
}
