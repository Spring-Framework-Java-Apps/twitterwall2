package org.woehlke.twitterwall.oodm.service.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.repository.entities.HashTagRepository;

import java.util.List;

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
    public HashTag create(HashTag tag) {
        return this.hashTagRepository.persist(tag);
    }

    @Override
    public HashTag update(HashTag tag) {
        return this.hashTagRepository.update(tag);
    }

    @Override
    public HashTag findByText(String text) {
        return this.hashTagRepository.findByText(text);
    }

    @Override
    public List<HashTag> getAll() {
        return this.hashTagRepository.getAll(HashTag.class);
    }

    @Override
    public long count() {
        return this.hashTagRepository.count(HashTag.class);
    }

    @Override
    public HashTag store(HashTag hashTag, Task task) {
        try {
            HashTag tagPers = this.hashTagRepository.findByText(hashTag.getText());
            tagPers.setUpdatedBy(task);
            tagPers = this.hashTagRepository.update(tagPers);
            log.debug("found: "+tagPers.toString());
            return tagPers;
        } catch (EmptyResultDataAccessException e) {
            hashTag.setCreatedBy(task);
            log.debug("try to persist: "+hashTag.toString());
            HashTag tagPers = this.hashTagRepository.persist(hashTag);
            log.debug("persisted: "+tagPers.toString());
            return tagPers;
        }
    }

}
