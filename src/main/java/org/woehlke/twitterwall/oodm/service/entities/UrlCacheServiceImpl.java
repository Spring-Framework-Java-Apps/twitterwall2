package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlCacheByUrlException;
import org.woehlke.twitterwall.oodm.repository.entities.UrlCacheRepository;

/**
 * Created by tw on 23.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UrlCacheServiceImpl implements UrlCacheService {

    private final UrlCacheRepository urlCacheRepository;

    @Autowired
    public UrlCacheServiceImpl(UrlCacheRepository urlCacheRepository) {
        this.urlCacheRepository = urlCacheRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public UrlCache store(UrlCache urlCache) {
        return this.urlCacheRepository.persist(urlCache);
    }

    @Override
    public UrlCache findByUrl(String url) {
        if(url == null) {
            throw new FindUrlCacheByUrlException(url);
        }
        return this.urlCacheRepository.findByUrl(url);
    }
}
