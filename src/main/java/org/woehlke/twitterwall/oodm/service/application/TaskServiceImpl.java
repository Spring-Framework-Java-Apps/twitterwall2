package org.woehlke.twitterwall.oodm.service.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.application.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskType;
import org.woehlke.twitterwall.oodm.repository.application.TaskRepository;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.List;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CountedEntitiesService countedEntitiesService) {
        this.taskRepository = taskRepository;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public Task store(Task domainObject, Task task) {
        try {
            Task taskPersistent = taskRepository.findById(domainObject.getId());
            return taskRepository.update(domainObject);
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
    public List<Task> getAll() {
        return taskRepository.getAll(Task.class);
    }

    @Override
    public long count() {
        return taskRepository.count(Task.class);
    }

    @Override
    public Task create(String msg,TaskType type) {
        CountedEntities countedEntities = this.countedEntitiesService.countAll();
        Task task = new Task(msg,type);
        task.setCountedEntitiesAtStart(countedEntities);
        task.start();
        task = taskRepository.persist(task);
        return task;
    }

    @Override
    public Task done(Task task) {
        CountedEntities countedEntities = this.countedEntitiesService.countAll();
        task.setCountedEntitiesAtFinish(countedEntities);
        task.done();
        task = taskRepository.update(task);
        return task;
    }

    @Override
    public Task error(Task task,Exception e) {
        CountedEntities countedEntities = this.countedEntitiesService.countAll();
        task.setCountedEntitiesAtFinish(countedEntities);
        task.error(e);
        task = taskRepository.update(task);
        return task;
    }
}
