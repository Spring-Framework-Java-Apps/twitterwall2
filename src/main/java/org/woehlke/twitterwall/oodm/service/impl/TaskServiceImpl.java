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
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.dao.TaskDao;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskDao taskRepository;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public TaskServiceImpl(TaskDao taskRepository, CountedEntitiesService countedEntitiesService) {
        this.taskRepository = taskRepository;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public Task store(Task domainObject) {
        try {
            Task taskPersistent = taskRepository.findById(domainObject.getId());
            if(domainObject.compareTo(taskPersistent)==0) {
                return taskRepository.update(domainObject);
            } else {
                return taskRepository.persist(domainObject);
            }
        } catch (EmptyResultDataAccessException e) {
            return taskRepository.persist(domainObject);
        }
    }

    @Override
    public Task create(Task domainObject) {
        return taskRepository.persist(domainObject);
    }

    @Override
    public Task update(Task domainObject) {
        return taskRepository.update(domainObject);
    }

    @Override
    public Page<Task> getAll(Pageable pageRequest) {
        return taskRepository.getAll(Task.class,pageRequest);
    }

    @Override
    public long count() {
        return taskRepository.count(Task.class);
    }

    @Override
    public Task create(String msg,TaskType type) {
        AbstractTwitterObject.CountedEntities countedEntities = this.countedEntitiesService.countAll();
        Task task = new Task(msg,type);
        task.setCountedEntitiesAtStart(countedEntities);
        task.start();
        task = taskRepository.persist(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task done(Task task) {
        AbstractTwitterObject.CountedEntities countedEntities = this.countedEntitiesService.countAll();
        task.setCountedEntitiesAtFinish(countedEntities);
        task.done();
        task = taskRepository.update(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task,Exception e) {
        task.error(e);
        task = taskRepository.update(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task, Exception e, String msg) {
        task.error(e,msg);
        task = taskRepository.update(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, Exception e) {
        task.warn(e);
        task = taskRepository.update(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, Exception e, String msg) {
        task.warn(e,msg);
        task = taskRepository.update(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task event(Task task, String msg) {
        task.event(msg);
        task = taskRepository.update(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, String msg) {
        task.warn(msg);
        task = taskRepository.update(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task, String msg) {
        task.error(msg);
        task = taskRepository.update(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findById(id);
    }
}
