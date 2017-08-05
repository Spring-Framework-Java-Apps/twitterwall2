package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.service.UrlService;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterUrlService;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.scheduled.service.persist.CreatePersistentUrl;

import java.net.MalformedURLException;
import java.net.URL;

import static org.woehlke.twitterwall.ScheduledTasks.ZWOELF_STUNDEN;

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
                if (urlPers == null) {
                    return twitterUrlService.fetchTransientUrl(url, task);
                } else {
                    log.debug(msg + " found: " + urlPers);
                    if (urlPers.isUrlAndExpandedTheSame() || urlPers.isRawUrlsFromDescription()) {
                        log.debug(msg + " urlPers.isUrlAndExpandedTheSame " + urlPers.getUniqueId());
                        if (urlPers.getTwitterApiCaching().isCached(task.getTaskType(), ZWOELF_STUNDEN)) {
                            return urlPers;
                        } else {
                            Url myTransientUrl = twitterUrlService.fetchTransientUrl(url, task);
                            if (myTransientUrl == null) {
                                log.debug(msg + "nothing found by fetchTransientUrl");
                                return urlPers;
                            } else {
                                urlPers.setExpanded(myTransientUrl.getExpanded());
                                try {
                                    URL newURL = new URL(myTransientUrl.getExpanded());
                                    urlPers.setDisplay(newURL.getHost());
                                } catch (MalformedURLException exe) {
                                    urlPers.setDisplay(myTransientUrl.getExpanded());
                                    log.warn(exe.getMessage());
                                }
                                urlPers.setUpdatedBy(task);
                                return urlService.store(urlPers,task);
                            }
                        }
                    } else {
                        return urlPers;
                    }
                }
            }
        } catch (Exception e){
            log.error(msg,e.getMessage());
            return null;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(CreatePersistentUrlImpl.class);

    private final UrlService urlService;

    private final TwitterUrlService twitterUrlService;

    @Autowired
    public CreatePersistentUrlImpl(UrlService urlService, TwitterUrlService twitterUrlService) {
        this.urlService = urlService;
        this.twitterUrlService = twitterUrlService;
    }
}
