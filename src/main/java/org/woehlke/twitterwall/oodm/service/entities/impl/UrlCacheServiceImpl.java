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
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repository.entities.UrlCacheRepository;
import org.woehlke.twitterwall.oodm.service.entities.UrlCacheService;


/**
 * Created by tw on 23.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UrlCacheServiceImpl implements UrlCacheService {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolServiceImpl.class);

    private final UrlCacheRepository urlCacheRepository;

    @Autowired
    public UrlCacheServiceImpl(UrlCacheRepository urlCacheRepository) {
        this.urlCacheRepository = urlCacheRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public UrlCache store(UrlCache urlCache, Task task) {
        String name = "UrlCache.store: store";
        try {
            UrlCache urlCachePers = this.findByUrl(urlCache.getUrl());
            urlCache.setId(urlCachePers.getId());
            urlCache.setCreatedBy(urlCachePers.getCreatedBy());
            urlCache.setUpdatedBy(task);
            UrlCache result = this.urlCacheRepository.update(urlCache);
            log.debug(name+" uodated "+result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            urlCache.setCreatedBy(task);
            UrlCache result = this.urlCacheRepository.persist(urlCache);
            log.debug(name+" psersisted "+result);
            return result;
        }
    }

    @Override
    public UrlCache create(UrlCache domainObject, Task task) {
        domainObject.setCreatedBy(task);
        return this.urlCacheRepository.persist(domainObject);
    }

    @Override
    public UrlCache update(UrlCache domainObject, Task task) {
        domainObject.setUpdatedBy(task);
        return domainObject;
    }

    @Override
    public Page<UrlCache> getAll(Pageable pageRequest) {
        return this.urlCacheRepository.getAll(UrlCache.class,pageRequest);
    }

    @Override
    public long count() {
        return this.urlCacheRepository.count(UrlCache.class);
    }

    @Override
    public UrlCache findByUrl(String url) {
        log.debug("UrlCache.findByUrl: try to find: "+url);
        UrlCache urlCache = this.urlCacheRepository.findByUrl(url);
        log.debug("UrlCache.findByUrl: found: "+urlCache.toString());
        return urlCache;
    }
}
