package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.repository.entities.UrlRepository;
import org.woehlke.twitterwall.oodm.service.entities.UrlService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED,readOnly = true)
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public Url store(Url url) {
        return this.urlRepository.persist(url);
    }
}
