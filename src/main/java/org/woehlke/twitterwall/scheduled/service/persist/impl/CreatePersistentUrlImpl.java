package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.repositories.UrlCacheRepository;
import org.woehlke.twitterwall.oodm.repositories.UrlRepository;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterUrlService;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.scheduled.service.persist.CreatePersistentUrl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class CreatePersistentUrlImpl implements CreatePersistentUrl {

    @Override
    public Url getPersistentUrlFor(String url, Task task) {
        String msg = "Url.getPersistentUrlFor url="+url+" ";
        if (url == null) {
            return null;
        } else {
            log.debug(msg + " try to find ");
            Url urlPers = urlRepository.findByUrl(url);
            if (urlPers != null) {
                log.debug(msg + " found: " + urlPers);
                if (urlPers.isUrlAndExpandedTheSame()) {
                    log.debug(msg + " urlPers.isUrlAndExpandedTheSame " + urlPers.toString());
                }
                urlPers.setUpdatedBy(task);
                urlPers = urlRepository.save(urlPers);
                return urlPers;
            } else {
                log.debug(msg + " not found ");
                log.debug(msg + " try to find UrlCache");
                UrlCache urlCache = urlCacheRepository.findByUrl(url);
                if (urlCache != null) {
                    log.debug(msg + " found: " + urlCache);
                    String displayUrl = urlCache.getExpanded();
                    try {
                        URL myURL = new URL(urlCache.getExpanded());
                        displayUrl = myURL.getHost();
                    } catch (MalformedURLException exe) {
                        log.warn(exe.getMessage());
                    }
                    Url newUrl = new Url(displayUrl, urlCache.getExpanded(), urlCache.getUrl());
                    newUrl.setCreatedBy(task);
                    log.debug(msg + " try to persist: " + newUrl.toString());
                    newUrl = urlRepository.save(newUrl);
                    log.debug(msg + " persisted: " + newUrl.toString());
                    return newUrl;
                } else {
                    urlCache = new UrlCache();
                    log.debug(msg + " try to fetchTransientUrl");
                    Url myUrl = twitterUrlService.fetchTransientUrl(url);
                    if (myUrl == null) {
                        log.debug(msg + "nothing found by fetchTransientUrl");
                        return null;
                    } else {
                        log.debug(msg + " found by fetchTransientUrl: " + myUrl);
                        urlCache.setUrl(myUrl.getUrl());
                        urlCache.setExpanded(myUrl.getExpanded());
                        log.debug(msg + " try to persist: " + urlCache.toString());
                        if (urlCache.isUrlAndExpandedTheSame()) {
                            log.debug(msg + " not persisted: " + urlCache.toString());
                        } else {
                            urlCache = urlCacheRepository.save(urlCache);
                            log.debug(msg + " persisted: " + urlCache.toString());
                        }
                        String displayUrl = myUrl.getExpanded();
                        try {
                            URL newURL = new URL(myUrl.getExpanded());
                            displayUrl = newURL.getHost();
                        } catch (MalformedURLException exe) {
                            log.warn(exe.getMessage());
                        }
                        Url newUrl = new Url(displayUrl, myUrl.getExpanded(), myUrl.getUrl());
                        newUrl.setCreatedBy(task);
                        log.debug(msg + " try to persist: " + newUrl.toString());
                        newUrl = urlRepository.save(newUrl);
                        log.debug(msg + " persisted: " + newUrl.toString());
                        return newUrl;
                    }
                }
            }
        }
    }

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
}
