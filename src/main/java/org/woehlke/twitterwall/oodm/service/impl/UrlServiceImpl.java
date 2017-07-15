package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.dao.UrlDao;
import org.woehlke.twitterwall.oodm.service.UrlService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UrlServiceImpl implements UrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    private final UrlDao urlDao;

    @Autowired
    public UrlServiceImpl(UrlDao urlDao) {
        this.urlDao = urlDao;
    }

    @Override
    public Url store(Url domainObject, Task task) {
        String name = "store "+domainObject.getUrl();
        log.debug(name);
        if(domainObject == null){
            String msg = "Url == null";
            log.debug(name+msg+domainObject.toString());
            throw new IllegalArgumentException(msg);
        }
        String url = domainObject.getUrl();
        if(url == null){
            String msg = "Url.getUrl() == null";
            log.debug(name+msg+domainObject.toString());
            throw new IllegalArgumentException();
        }
        Url result;
        try {
            Url urlPersistent = this.urlDao.findByUrl(url);
            domainObject.setId(urlPersistent.getId());
            domainObject.setCreatedBy(urlPersistent.getCreatedBy());
            domainObject.setUpdatedBy(task);
            result = this.urlDao.update(domainObject);
            log.debug(name+" uodated "+result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            domainObject.setCreatedBy(task);
            result = this.urlDao.persist(domainObject);
            log.debug(name+" persisted "+result.toString());
            return result;
        }
    }

    @Override
    public Url create(Url url, Task task) {
        url.setCreatedBy(task);
        return this.urlDao.persist(url);
    }

    @Override
    public Url update(Url url, Task task) {
        url.setUpdatedBy(task);
        return this.urlDao.update(url);
    }

    @Override
    public Page<Url> getAll(Pageable pageRequest) {
        return this.urlDao.getAll(Url.class,pageRequest);
    }

    @Override
    public long count() {
        return this.urlDao.count(Url.class);
    }

    @Override
    public Url findByUrl(String url) {
        String name = "findByUrl "+url+" ";
        if(url == null){
            throw new IllegalArgumentException("Url.findByUrl: url == null");
        }
        Url result = this.urlDao.findByUrl(url);
        log.debug(name+result.toString());
        return result;
    }

}
