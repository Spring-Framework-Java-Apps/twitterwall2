package org.woehlke.twitterwall.oodm.service.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repository.entities.UrlCacheRepository;

import java.util.List;

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
    public UrlCache store(UrlCache urlCache) {
        String name = "UrlCache.store: store";
        UrlCache result = this.urlCacheRepository.persist(urlCache);
        log.debug(name+result);
        return result;
    }

    @Override
    public UrlCache create(UrlCache domainObject) {
        return this.urlCacheRepository.persist(domainObject);
    }

    @Override
    public UrlCache update(UrlCache domainObject) {
        return domainObject;
    }

    @Override
    public List<UrlCache> getAll() {
        return this.urlCacheRepository.getAll(UrlCache.class);
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
