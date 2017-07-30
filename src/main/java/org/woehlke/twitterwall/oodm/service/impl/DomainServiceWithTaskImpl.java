package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.service.common.DomainService;


@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public abstract class DomainServiceWithTaskImpl<T extends DomainObjectWithTask> implements DomainService<T> {

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
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public T store(T domainObject, Task task) {
        String msg = "store "+domainObject.getUniqueId()+" in Task "+task.getUniqueId()+" ";
        T domainObjectResult = null;
        try {
            task.setTimeLastUpdate();
            task = this.taskRepository.save(task);
            Long id = domainObject.getId();
            T storedObject = null;
            if(id != null){
                storedObject = domainRepository.findOne(id);
            }
            if(storedObject!=null){
                try {
                    domainObject.setId(storedObject.getId());
                    domainObject.setTaskInfo(storedObject.getTaskInfo());
                    domainObject.setCreatedBy(storedObject.getCreatedBy());
                    domainObject.setUpdatedBy(task);
                    domainObjectResult = domainRepository.save(domainObject);
                    log.debug(msg + "merged: " + domainObjectResult.toString());
                    return domainObjectResult;
                } catch (Exception e){
                    return null;
                }
            }
            T domainObjectPersistent = domainRepository.findByUniqueId(domainObject);
            if (domainObjectPersistent != null) {
                try {
                    domainObject.setId(domainObjectPersistent.getId());
                    domainObject.setTaskInfo(domainObjectPersistent.getTaskInfo());
                    domainObject.setCreatedBy(domainObjectPersistent.getCreatedBy());
                    domainObject.setUpdatedBy(task);
                    domainObjectResult = domainRepository.save(domainObject);
                    log.debug(msg + "merged: " + domainObjectResult.toString());
                    return domainObjectResult;
                } catch (Exception e) {
                    return null;
                }
            } else {
                try {
                    domainObject.setCreatedBy(task);
                    domainObject.setUpdatedBy(task);
                    log.debug("try to persist: " + domainObject.toString());
                    domainObjectResult = domainRepository.save(domainObject);
                    log.debug("persisted: " + domainObjectResult.toString());
                    return domainObjectResult;
                } catch (Exception e){
                    return null;
                }
            }
        } catch (Exception e)  {
            log.warn(msg,e.getMessage());
            //e.printStackTrace();
        }
        return null;
    }

    /*
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
    */
}
