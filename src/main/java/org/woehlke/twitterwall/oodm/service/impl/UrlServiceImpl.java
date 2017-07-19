package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.UrlRepository;
import org.woehlke.twitterwall.oodm.service.UrlService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UrlServiceImpl implements UrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    private final UrlRepository urlRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository, TaskRepository taskRepository) {
        this.urlRepository = urlRepository;
        this.taskRepository = taskRepository;
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
        Url urlPersistent = urlRepository.findByUrl(url);
        if(urlPersistent!=null){
            domainObject.setId(urlPersistent.getId());
            domainObject.setCreatedBy(urlPersistent.getCreatedBy());
            task = this.taskRepository.save(task);
            domainObject.setUpdatedBy(task);
            result = urlRepository.save(domainObject);
            log.debug(name+" uodated "+result.toString());
            return result;
        } else {
            task = this.taskRepository.save(task);
            domainObject.setCreatedBy(task);
            result = urlRepository.save(domainObject);
            log.debug(name+" persisted "+result.toString());
            return result;
        }
    }

    @Override
    public Url create(Url url, Task task) {
        url.setCreatedBy(task);
        return urlRepository.save(url);
    }

    @Override
    public Url update(Url url, Task task) {
        url.setUpdatedBy(task);
        return urlRepository.save(url);
    }

    @Override
    public Page<Url> getAll(Pageable pageRequest) {
        return urlRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return urlRepository.count();
    }

    @Override
    public Url findByUrl(String url) {
        String name = "findByUrl "+url+" ";
        if(url == null){
            throw new IllegalArgumentException("Url.findByUrl: url == null");
        }
        Url result = urlRepository.findByUrl(url);
        log.debug(name+result.toString());
        return result;
    }

}
