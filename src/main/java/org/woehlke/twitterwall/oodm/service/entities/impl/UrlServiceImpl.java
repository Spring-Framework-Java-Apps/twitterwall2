package org.woehlke.twitterwall.oodm.service.entities.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.repository.entities.UrlRepository;
import org.woehlke.twitterwall.oodm.service.entities.UrlService;


import java.util.*;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UrlServiceImpl implements UrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Url store(Url domainObject, Task task) {
        String name = "store "+domainObject.getUrl();
        log.debug(name);
        if(domainObject == null){
            String msg = "Url == null";
            log.debug(name+msg+domainObject.toString());
            throw new IllegalArgumentException(msg);
        }
        String url = domainObject.getUrl();
        if(url == null){
            String msg = "Url.getUrl() == null";
            log.debug(name+msg+domainObject.toString());
            throw new IllegalArgumentException();
        }
        Url result;
        try {
            Url urlPersistent = this.urlRepository.findByUrl(url);
            domainObject.setId(urlPersistent.getId());
            domainObject.setCreatedBy(urlPersistent.getCreatedBy());
            domainObject.setUpdatedBy(task);
            result = this.urlRepository.update(domainObject);
            log.debug(name+" uodated "+result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            domainObject.setCreatedBy(task);
            result = this.urlRepository.persist(domainObject);
            log.debug(name+" psersisted "+result.toString());
            return result;
        }
    }

    @Override
    public Url create(Url url) {
        return this.urlRepository.persist(url);
    }

    @Override
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
        String name = "findByUrl "+url+" ";
        if(url == null){
            throw new IllegalArgumentException("Url.findByUrl: url == null");
        }
        Url result = this.urlRepository.findByUrl(url);
        log.debug(name+result.toString());
        return result;
    }

}
