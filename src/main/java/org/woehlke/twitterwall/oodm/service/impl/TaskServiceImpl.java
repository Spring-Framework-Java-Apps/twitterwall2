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
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CountedEntitiesService countedEntitiesService) {
        this.taskRepository = taskRepository;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public Task store(Task domainObject) {
        return taskRepository.save(domainObject);
    }

    @Override
    public Task create(Task domainObject) {
        return taskRepository.save(domainObject);
    }

    @Override
    public Task update(Task domainObject) {
        return taskRepository.save(domainObject);
    }

    @Override
    public Page<Task> getAll(Pageable pageRequest) {
        return taskRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return taskRepository.count();
    }

    @Override
    public Task create(String msg,TaskType type) {
        CountedEntities countedEntities = this.countedEntitiesService.countAll();
        Task task = new Task(msg,type);
        task.setCountedEntitiesAtStart(countedEntities);
        task.start();
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task done(Task task) {
        CountedEntities countedEntities = this.countedEntitiesService.countAll();
        task.setCountedEntitiesAtFinish(countedEntities);
        task.done();
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task,Exception e) {
        task.error(e);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task, Exception e, String msg) {
        task.error(e,msg);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, Exception e) {
        task.warn(e);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, Exception e, String msg) {
        task.warn(e,msg);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task event(Task task, String msg) {
        task.event(msg);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, String msg) {
        task.warn(msg);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task, String msg) {
        task.error(msg);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findOne(id);
    }
}
