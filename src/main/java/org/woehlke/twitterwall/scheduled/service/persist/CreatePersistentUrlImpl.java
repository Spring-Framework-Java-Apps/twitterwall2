package org.woehlke.twitterwall.scheduled.service.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.backend.TwitterUrlService;
import org.woehlke.twitterwall.exceptions.remote.FetchUrlException;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repository.entities.UrlCacheRepository;
import org.woehlke.twitterwall.oodm.repository.entities.UrlRepository;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CreatePersistentUrlImpl implements CreatePersistentUrl {

    private static final Logger log = LoggerFactory.getLogger(CreatePersistentUrlImpl.class);

    private final UrlRepository urlRepository;

    private final UrlCacheRepository urlCacheRepository;

    private final TwitterUrlService twitterUrlService;

    @Autowired
    public CreatePersistentUrlImpl(UrlRepository urlRepository, UrlCacheRepository urlCacheRepository, TwitterUrlService twitterUrlService) {
        this.urlRepository = urlRepository;
        this.urlCacheRepository = urlCacheRepository;
        this.twitterUrlService = twitterUrlService;
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
                log.debug(msg+" try to find ");
                Url urlPers = this.urlRepository.findByUrl(url);
                log.debug(msg+" found: "+urlPers);
                if(urlPers.isUrlAndExpandedTheSame()){
                    log.debug(msg+" urlPers.isUrlAndExpandedTheSame "+urlPers.toString());
                }
                return urlPers;
            } catch (EmptyResultDataAccessException ex) {
                log.debug(msg+" not found ");
                try {
                    log.debug(msg+" try to find UrlCache");
                    UrlCache urlCache = urlCacheRepository.findByUrl(url);
                    log.debug(msg+" found: "+urlCache);
                    String displayUrl = urlCache.getExpanded();
                    try {
                        URL myURL = new URL(urlCache.getExpanded());
                        displayUrl = myURL.getHost();
                    } catch (MalformedURLException exe) {
                        log.warn(exe.getMessage());
                    }
                    Url newUrl = new Url(displayUrl, urlCache.getExpanded(), urlCache.getUrl(), indices);
                    log.debug(msg+" try to persist: "+newUrl.toString());
                    newUrl = this.urlRepository.persist(newUrl);
                    log.debug(msg+" persisted: "+newUrl.toString());
                    return newUrl;
                } catch (EmptyResultDataAccessException e) {
                    UrlCache urlCache = new UrlCache();
                    try {
                        log.debug(msg + " try to fetchTransientUrl");
                        Url myUrl = twitterUrlService.fetchTransientUrl(url);
                        log.debug(msg + " found by fetchTransientUrl: " + myUrl);
                        urlCache.setUrl(myUrl.getUrl());
                        urlCache.setExpanded(myUrl.getExpanded());
                        log.debug(msg + " try to persist: " + urlCache.toString());
                        if (urlCache.isUrlAndExpandedTheSame()) {
                            urlCache = urlCacheRepository.persist(urlCache);
                            log.debug(msg + " persisted: " + urlCache.toString());
                        } else {
                            log.debug(msg + " not persisted: " + urlCache.toString());
                        }
                    } catch (FetchUrlException fue)  {
                        log.debug(msg+fue.getMessage());
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
                    log.debug(msg+" try to persist: "+newUrl.toString());
                    newUrl =this.urlRepository.persist(newUrl);
                    log.debug(msg+" persisted: "+newUrl.toString());
                    return newUrl;
                }
            }
        }
    }
}
