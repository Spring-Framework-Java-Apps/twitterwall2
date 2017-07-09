package org.woehlke.twitterwall.oodm.service.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.repository.entities.UrlRepository;


import java.util.*;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UrlServiceImpl implements UrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Url store(Url domainObject) {
        if(domainObject == null){
            throw new IllegalArgumentException("Url == null");
        }
        String url = domainObject.getUrl();
        if(url == null){
            throw new IllegalArgumentException("Url.getUrl() == null");
        }
        try {
            Url urlPersistent = this.urlRepository.findByUrl(url);
            domainObject.setId(urlPersistent.getId());
            return this.urlRepository.update(domainObject);
        } catch (EmptyResultDataAccessException e) {
            return this.urlRepository.persist(domainObject);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Url create(Url url) {
        return this.urlRepository.persist(url);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Url update(Url url) {
        return this.urlRepository.update(url);
    }

    @Override
    public List<Url> getAll() {
        return this.urlRepository.getAll(Url.class);
    }

    @Override
    public long count() {
        return this.urlRepository.count(Url.class);
    }

    @Override
    public Url findByUrl(String url) {
        if(url == null){
            throw new IllegalArgumentException("Url.findByUrl: url == null");
        }
        return this.urlRepository.findByUrl(url);
    }

}
