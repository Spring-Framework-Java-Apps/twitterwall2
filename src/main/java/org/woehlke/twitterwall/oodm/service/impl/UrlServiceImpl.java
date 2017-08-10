package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.UrlRepository;
import org.woehlke.twitterwall.oodm.service.UrlService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UrlServiceImpl extends DomainServiceWithTaskImpl<Url> implements UrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository, TaskRepository taskRepository) {
        super(urlRepository,taskRepository);
        this.urlRepository = urlRepository;
    }

    @Override
    public Url findByUrl(String url) {
        String name = "findByUrl "+url+" ";
        if(url == null){
            log.debug(name+"Url.findByUrl: url == null");
            return null;
            //throw new IllegalArgumentException("Url.findByUrl: url == null");
        }
        Url result = urlRepository.findByUrl(url);
        if(result == null){
            log.debug(name+"Url.findByUrl: url == null");
        } else {
            log.debug(name+result.getUniqueId());
            log.trace(name+result.toString());
        }
        return result;
    }

}
