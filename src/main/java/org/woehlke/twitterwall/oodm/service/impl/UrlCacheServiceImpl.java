package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.UrlCacheRepository;
import org.woehlke.twitterwall.oodm.service.UrlCacheService;


/**
 * Created by tw on 23.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UrlCacheServiceImpl extends DomainServiceWithTaskImpl<UrlCache> implements UrlCacheService {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolServiceImpl.class);

    private final UrlCacheRepository urlCacheRepository;

    @Autowired
    public UrlCacheServiceImpl(UrlCacheRepository urlCacheRepository, TaskRepository taskRepository) {
        super(urlCacheRepository,taskRepository);
        this.urlCacheRepository = urlCacheRepository;
    }

    @Override
    public UrlCache findByUrl(String url) {
        log.debug("UrlCache.findByUrl: try to find: "+url);
        UrlCache urlCache = this.urlCacheRepository.findByUrl(url);
        log.debug("UrlCache.findByUrl: found: "+urlCache.toString());
        return urlCache;
    }
}
