package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.model.HashTagCounted;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.repository.entities.HashTagRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class HashTagServiceImpl implements HashTagService {

    private final HashTagRepository hashTagRepository;

    @Autowired
    public HashTagServiceImpl(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public HashTag store(HashTag tag) {
        return this.hashTagRepository.persist(tag);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public HashTag update(HashTag tag) {
        return this.hashTagRepository.update(tag);
    }

    @Override
    public HashTag findByText(String text) {
        return this.hashTagRepository.findByText(text);
    }

    @Override
    public List<HashTag> getAll() {
        return this.hashTagRepository.getAll();
    }
}
