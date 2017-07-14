package org.woehlke.twitterwall.oodm.service.entities.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.repository.entities.HashTagRepository;
import org.woehlke.twitterwall.oodm.service.entities.HashTagService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class HashTagServiceImpl implements HashTagService {

    private static final Logger log = LoggerFactory.getLogger(HashTagServiceImpl.class);

    private final HashTagRepository hashTagRepository;

    @Autowired
    public HashTagServiceImpl(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    public HashTag create(HashTag hashTag, Task task) {
        hashTag.setCreatedBy(task);
        return this.hashTagRepository.persist(hashTag);
    }

    @Override
    public HashTag update(HashTag tag, Task task) {
        tag.setUpdatedBy(task);
        return this.hashTagRepository.update(tag);
    }

    @Override
    public HashTag findByText(String text) {
        return this.hashTagRepository.findByText(text);
    }

    @Override
    public Page<HashTag> getAll(Pageable pageRequest) {
        return this.hashTagRepository.getAll(HashTag.class,pageRequest);
    }

    @Override
    public long count() {
        return this.hashTagRepository.count(HashTag.class);
    }

    @Override
    public HashTag store(HashTag hashTag, Task task) {
        try {
            HashTag tagPers = this.hashTagRepository.findByText(hashTag.getText());
            hashTag.setId(tagPers.getId());
            hashTag.setCreatedBy(tagPers.getCreatedBy());
            hashTag.setUpdatedBy(task);
            hashTag = this.hashTagRepository.update(hashTag);
            log.debug("found: "+hashTag.toString());
            return hashTag;
        } catch (EmptyResultDataAccessException e) {
            hashTag.setCreatedBy(task);
            log.debug("try to persist: "+hashTag.toString());
            HashTag tagPers = this.hashTagRepository.persist(hashTag);
            log.debug("persisted: "+tagPers.toString());
            return tagPers;
        }
    }

}
