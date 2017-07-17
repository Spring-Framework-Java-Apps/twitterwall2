package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repositories.UrlCacheRepository;
import org.woehlke.twitterwall.oodm.service.UrlCacheService;


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
        UrlCache urlCachePers = this.findByUrl(urlCache.getUrl());
        if(urlCachePers!=null){
            urlCache.setId(urlCachePers.getId());
            urlCache.setCreatedBy(urlCachePers.getCreatedBy());
            urlCache.setUpdatedBy(task);
            UrlCache result = this.urlCacheRepository.save(urlCache);
            log.debug(name+" updated "+result.toString());
            return result;
        } else {
            urlCache.setCreatedBy(task);
            UrlCache result = this.urlCacheRepository.save(urlCache);
            log.debug(name+" persisted "+result);
            return result;
        }
    }

    @Override
    public UrlCache create(UrlCache domainObject, Task task) {
        domainObject.setCreatedBy(task);
        return urlCacheRepository.save(domainObject);
    }

    @Override
    public UrlCache update(UrlCache domainObject, Task task) {
        domainObject.setUpdatedBy(task);
        return urlCacheRepository.save(domainObject);
    }

    @Override
    public Page<UrlCache> getAll(Pageable pageRequest) {
        return urlCacheRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return urlCacheRepository.count();
    }

    @Override
    public UrlCache findByUrl(String url) {
        log.debug("UrlCache.findByUrl: try to find: "+url);
        UrlCache urlCache = this.urlCacheRepository.findByUrl(url);
        log.debug("UrlCache.findByUrl: found: "+urlCache.toString());
        return urlCache;
    }
}
