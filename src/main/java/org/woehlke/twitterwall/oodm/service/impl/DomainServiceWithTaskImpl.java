package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;

public class DomainServiceWithTaskImpl<T extends DomainObjectWithTask> implements DomainServiceWithTask<T> {

    private static final Logger log = LoggerFactory.getLogger(DomainServiceWithTaskImpl.class);

    private final DomainRepository<T> domainRepository;

    private final TaskRepository taskRepository;

    protected DomainServiceWithTaskImpl(DomainRepository<T> domainRepository, TaskRepository taskRepository) {
        this.domainRepository = domainRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public T findById(long id) {
        return domainRepository.findOne(id);
    }

    @Override
    public Page<T> getAll(Pageable pageRequest) {
        return domainRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return domainRepository.count();
    }

    @Override
    public T store(T domainObject, Task task) {
        String msg = "store "+domainObject.getUniqueId()+" in Task "+task.getId()+" "+task.getTaskType()+" ";
        T domainObjectResult = null;
        try {
            task.setTimeLastUpdate();
            task = this.taskRepository.save(task);
            T domainObjectPersistent = null;
            T storedObject = null;
            if(domainObject.getId() != null){
                storedObject = domainRepository.findOne(domainObject.getId());
                if(storedObject!=null)  {
                    if(storedObject.getUniqueId().compareTo(domainObject.getUniqueId())==0) {
                        domainObjectPersistent = storedObject;
                    } else {
                        domainObjectPersistent = null;
                        log.error(msg+"Something strange happened!");
                        log.debug(msg+"tried to persist: " + domainObject.toString());
                        log.debug(msg+"found: " + domainObjectPersistent.toString());
                    }
                }
            }
            if(domainObjectPersistent==null){
                domainObjectPersistent = domainRepository.findByUniqueId(domainObject);
            }
            if (domainObjectPersistent != null) {
                domainObject.setId(domainObjectPersistent.getId());
                domainObject.setTaskInfo(domainObjectPersistent.getTaskInfo());
                domainObject.setCreatedBy(domainObjectPersistent.getCreatedBy());
                domainObject.setUpdatedBy(task);
                storedObject = domainRepository.save(domainObject);
                domainObjectResult = storedObject;
                log.debug(msg+"merged: " + domainObjectResult.toString());
            } else {
                domainObject.setCreatedBy(task);
                domainObject.setUpdatedBy(task);
                log.debug("try to persist: " + domainObject.toString());
                storedObject = domainRepository.save(domainObject);
                domainObjectResult = storedObject;
                log.debug("persisted: " + domainObjectResult.toString());
            }
        } catch (Exception e)  {
            log.warn(msg,e.getMessage());
        }
        return domainObjectResult;
    }

    @Override
    public T create(T domainObject, Task task) {
        domainObject.setCreatedBy(task);
        return domainRepository.save(domainObject);
    }

    @Override
    public T update(T domainObject, Task task) {
        domainObject.setUpdatedBy(task);
        return domainRepository.save(domainObject);
    }

}
