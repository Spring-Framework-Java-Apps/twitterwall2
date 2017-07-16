package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.repositories.UrlCacheRepository;
import org.woehlke.twitterwall.oodm.repositories.UrlRepository;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterUrlService;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
//import org.woehlke.twitterwall.oodm.dao.UrlCacheDao;
//import org.woehlke.twitterwall.oodm.dao.UrlDao;
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
        int indices[] = {};
        if (url == null) {
            return null;
        } else {
            try {
                log.debug(msg+" try to find ");
                Url urlPers = urlRepository.findByUrl(url);//this.urlDao.findByUrl(url);
                log.debug(msg+" found: "+urlPers);
                if(urlPers.isUrlAndExpandedTheSame()){
                    log.debug(msg+" urlPers.isUrlAndExpandedTheSame "+urlPers.toString());
                }
                urlPers.setUpdatedBy(task);
                urlPers = urlRepository.save(urlPers);//this.urlDao.update(urlPers);
                return urlPers;
            } catch (EmptyResultDataAccessException ex) {
                log.debug(msg+" not found ");
                try {
                    log.debug(msg+" try to find UrlCache");
                    UrlCache urlCache = urlCacheRepository.findByUrl(url);//urlCacheDao.findByUrl(url);
                    log.debug(msg+" found: "+urlCache);
                    String displayUrl = urlCache.getExpanded();
                    try {
                        URL myURL = new URL(urlCache.getExpanded());
                        displayUrl = myURL.getHost();
                    } catch (MalformedURLException exe) {
                        log.warn(exe.getMessage());
                    }
                    Url newUrl = new Url(displayUrl, urlCache.getExpanded(), urlCache.getUrl(), indices);
                    newUrl.setCreatedBy(task);
                    log.debug(msg+" try to persist: "+newUrl.toString());
                    newUrl = urlRepository.save(newUrl);//this.urlDao.persist(newUrl);
                    log.debug(msg+" persisted: "+newUrl.toString());
                    return newUrl;
                } catch (EmptyResultDataAccessException e) {
                    UrlCache urlCache = new UrlCache();
                    log.debug(msg + " try to fetchTransientUrl");
                    Url myUrl = twitterUrlService.fetchTransientUrl(url);
                    if(myUrl == null) {
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
                            urlCache = urlCacheRepository.save(urlCache);//urlCacheDao.persist(urlCache);
                            log.debug(msg + " persisted: " + urlCache.toString());
                        }
                        String displayUrl = myUrl.getExpanded();
                        try {
                            URL newURL = new URL(myUrl.getExpanded());
                            displayUrl = newURL.getHost();
                        } catch (MalformedURLException exe) {
                            log.warn(exe.getMessage());
                        }
                        Url newUrl = new Url(displayUrl, myUrl.getExpanded(), myUrl.getUrl(), indices);
                        newUrl.setCreatedBy(task);
                        log.debug(msg+" try to persist: "+newUrl.toString());
                        newUrl = urlRepository.save(newUrl); //this.urlDao.persist(newUrl);
                        log.debug(msg+" persisted: "+newUrl.toString());
                        return newUrl;
                    }
                }
            }
        }
    }


    private static final Logger log = LoggerFactory.getLogger(CreatePersistentUrlImpl.class);

    //private final UrlDao urlDao;

    private final UrlRepository urlRepository;

    //private final UrlCacheDao urlCacheDao;

    private final UrlCacheRepository urlCacheRepository;

    private final TwitterUrlService twitterUrlService;

    @Autowired
    public CreatePersistentUrlImpl(UrlRepository urlRepository, UrlCacheRepository urlCacheRepository, TwitterUrlService twitterUrlService) {
        //this.urlDao = urlDao;
        this.urlRepository = urlRepository;
        //this.urlCacheDao = urlCacheDao;
        this.urlCacheRepository = urlCacheRepository;
        this.twitterUrlService = twitterUrlService;
    }
}
