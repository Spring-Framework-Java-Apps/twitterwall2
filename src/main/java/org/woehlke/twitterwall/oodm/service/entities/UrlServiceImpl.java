package org.woehlke.twitterwall.oodm.service.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.backend.TwitterUrlService;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.exceptions.remote.FetchUrlException;
import org.woehlke.twitterwall.oodm.repository.entities.UrlCacheRepository;
import org.woehlke.twitterwall.oodm.repository.entities.UrlRepository;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UrlServiceImpl implements UrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    private final UrlRepository urlRepository;

    private final UrlCacheRepository urlCacheRepository;

    private final TwitterUrlService twitterUrlService;
    
    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository, UrlCacheRepository urlCacheRepository, TwitterUrlService twitterUrlService) {
        this.urlRepository = urlRepository;
        this.urlCacheRepository = urlCacheRepository;
        this.twitterUrlService = twitterUrlService;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Url store(Url url) {
        String msg = "Url.store: ";
        String urlStr = url.getUrl();
        log.info(msg+"try to store by getPersistentUrlFor url="+urlStr);
        return getPersistentUrlFor(urlStr);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Url getPersistentUrlFor(String url) {
        String msg = "Url.getPersistentUrlFor url="+url+" ";
        int indices[] = {};
        if (url == null) {
            return null;
        } else {
            try {
                log.info(msg+" try to find ");
                Url urlPers = this.urlRepository.findByUrl(url);
                log.info(msg+" found: "+urlPers);
                if(urlPers.isUrlAndExpandedTheSame()){
                    log.info(msg+" urlPers.isUrlAndExpandedTheSame "+urlPers.toString());
                }
                return urlPers;
            } catch (EmptyResultDataAccessException ex) {
                log.info(msg+" not found ");
                try {
                    log.info(msg+" try to find UrlCache");
                    UrlCache urlCache = urlCacheRepository.findByUrl(url);
                    log.info(msg+" found: "+urlCache);
                    String displayUrl = urlCache.getExpanded();
                    try {
                        URL myURL = new URL(urlCache.getExpanded());
                        displayUrl = myURL.getHost();
                    } catch (MalformedURLException exe) {
                        log.warn(exe.getMessage());
                    }
                    Url newUrl = new Url(displayUrl, urlCache.getExpanded(), urlCache.getUrl(), indices);
                    log.info(msg+" try to persist: "+newUrl.toString());
                    newUrl = this.urlRepository.persist(newUrl);
                    log.info(msg+" persisted: "+newUrl.toString());
                    return newUrl;
                } catch (EmptyResultDataAccessException e) {
                    UrlCache urlCache = new UrlCache();
                    try {
                        log.info(msg + " try to fetchTransientUrl");
                        Url myUrl = twitterUrlService.fetchTransientUrl(url);
                        log.info(msg + " found by fetchTransientUrl: " + myUrl);
                        urlCache.setUrl(myUrl.getUrl());
                        urlCache.setExpanded(myUrl.getExpanded());
                        log.info(msg + " try to persist: " + urlCache.toString());
                        if (urlCache.isUrlAndExpandedTheSame()) {
                            urlCache = urlCacheRepository.persist(urlCache);
                            log.info(msg + " persisted: " + urlCache.toString());
                        } else {
                            log.info(msg + " not persisted: " + urlCache.toString());
                        }
                    } catch (FetchUrlException fue)  {
                        log.info(msg+fue.getMessage());
                        urlCache.setUrl(url);
                        urlCache.setExpanded(url);
                    }
                    String displayUrl = urlCache.getExpanded();
                    try {
                        URL myURL = new URL(urlCache.getExpanded());
                        displayUrl = myURL.getHost();
                    } catch (MalformedURLException exe) {
                        log.warn(exe.getMessage());
                    }
                    Url newUrl = new Url(displayUrl, urlCache.getExpanded(), urlCache.getUrl(), indices);
                    log.info(msg+" try to persist: "+newUrl.toString());
                    newUrl =this.urlRepository.persist(newUrl);
                    log.info(msg+" persisted: "+newUrl.toString());
                    return newUrl;
                }
            }
        }
    }
}
