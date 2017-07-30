package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.UrlField;
import org.woehlke.twitterwall.oodm.repositories.UrlCacheRepository;
import org.woehlke.twitterwall.oodm.repositories.UrlRepository;
import org.woehlke.twitterwall.oodm.service.UrlCacheService;
import org.woehlke.twitterwall.oodm.service.UrlService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterUrlService;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.scheduled.service.persist.CreatePersistentUrl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tw on 09.07.17.
 */
@Component
public class CreatePersistentUrlImpl implements CreatePersistentUrl {

    @Override
    public Url createPersistentUrlFor(String url, Task task) {
        String msg = "Url.createPersistentUrlFor url="+url+" "+task.getUniqueId()+" : ";
        try {
            if (url == null) {
                return null;
            } else {
                log.debug(msg + " try to find ");
                Url urlPers = urlService.findByUrl(url);
                if (urlPers != null) {
                    log.debug(msg + " found: " + urlPers);
                    if (urlPers.isUrlAndExpandedTheSame()) {
                        log.debug(msg + " urlPers.isUrlAndExpandedTheSame " + urlPers.toString());
                        Url myTransientUrl = twitterUrlService.fetchTransientUrl(url, task);
                        if (myTransientUrl == null) {
                            log.debug(msg + "nothing found by fetchTransientUrl");
                            return null;
                        } else {
                            urlPers.setExpanded(myTransientUrl.getExpanded());
                            String displayUrl = myTransientUrl.getExpanded();
                            try {
                                URL newURL = new URL(myTransientUrl.getExpanded());
                                displayUrl = newURL.getHost();
                            } catch (MalformedURLException exe) {
                                log.warn(exe.getMessage());
                            }
                            urlPers.setDisplay(displayUrl);
                        }
                    }
                    urlPers.setUpdatedBy(task);
                    urlPers = urlService.store(urlPers,task);
                    return urlPers;
                } else {
                    log.debug(msg + " not found ");
                    log.debug(msg + " try to find UrlCache");
                    UrlCache urlCache = urlCacheService.findByUrl(url);
                    if (urlCache != null) {
                        log.debug(msg + " found: " + urlCache);
                        String displayUrl = urlCache.getExpanded();
                        try {
                            URL myURL = new URL(urlCache.getExpanded());
                            displayUrl = myURL.getHost();
                        } catch (MalformedURLException exe) {
                            log.warn(exe.getMessage());
                        }
                        Url newUrl = new Url(task, null, displayUrl, urlCache.getExpanded(), urlCache.getUrl());
                        log.debug(msg + " try to persist: " + newUrl.toString());
                        newUrl = urlService.store(newUrl,task);
                        //TODO: delete ?
                        if ((!newUrl.isRawUrlsFromDescription()) && (!newUrl.isUrlAndExpandedTheSame())) {
                            urlCacheService.delete(urlCache);
                        }
                        log.debug(msg + " persisted: " + newUrl.toString());
                        return newUrl;
                    } else {
                        UrlField urlField = new UrlField(url);
                        urlCache = new UrlCache(task, null, urlField);
                        log.debug(msg + " try to fetchTransientUrl");
                        Url myTransientUrl = twitterUrlService.fetchTransientUrl(url, task);
                        if (myTransientUrl == null) {
                            log.debug(msg + "nothing found by fetchTransientUrl");
                            return null;
                        } else {
                            log.debug(msg + " found by fetchTransientUrl: " + myTransientUrl);
                            urlCache.setExpanded(myTransientUrl.getExpanded());
                            log.debug(msg + " try to persist: " + urlCache.toString());
                            if (urlCache.isUrlAndExpandedTheSame()) {
                                log.debug(msg + " not persisted: " + urlCache.toString());
                            } else {
                                urlCache = urlCacheService.store(urlCache,task);
                                log.debug(msg + " persisted: " + urlCache.toString());
                            }
                            String displayUrl = myTransientUrl.getExpanded();
                            try {
                                URL newURL = new URL(myTransientUrl.getExpanded());
                                displayUrl = newURL.getHost();
                            } catch (MalformedURLException exe) {
                                log.warn(exe.getMessage());
                            }
                            Url newUrl = new Url(task, null, displayUrl, myTransientUrl.getExpanded(), myTransientUrl.getUrl());
                            log.debug(msg + " try to persist: " + newUrl.toString());
                            newUrl = urlService.store(newUrl,task);
                            log.debug(msg + " persisted: " + newUrl.toString());
                            return newUrl;
                        }
                    }
                }
            }
        } catch (Exception e){
            log.error(msg,e.getMessage());
        }
        return null;
    }

    private static final Logger log = LoggerFactory.getLogger(CreatePersistentUrlImpl.class);

    //private final UrlRepository urlRepository;

    //private final UrlCacheRepository urlCacheRepository;

    private final UrlService urlService;

    private final UrlCacheService urlCacheService;

    private final TwitterUrlService twitterUrlService;

    @Autowired
    public CreatePersistentUrlImpl(UrlRepository urlRepository, UrlCacheRepository urlCacheRepository, UrlService urlService, UrlCacheService urlCacheService, TwitterUrlService twitterUrlService) {
        this.urlService = urlService;
        this.urlCacheService = urlCacheService;
        //this.urlRepository = urlRepository;
        //this.urlCacheRepository = urlCacheRepository;
        this.twitterUrlService = twitterUrlService;
    }
}
