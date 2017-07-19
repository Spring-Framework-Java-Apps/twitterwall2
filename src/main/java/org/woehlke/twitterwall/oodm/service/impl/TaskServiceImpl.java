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
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskStatus;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.repositories.TaskHistoryRepository;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.Date;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    private final CountedEntitiesService countedEntitiesService;

    private final TaskHistoryRepository taskHistoryRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CountedEntitiesService countedEntitiesService, TaskHistoryRepository taskHistoryRepository) {
        this.taskRepository = taskRepository;
        this.countedEntitiesService = countedEntitiesService;
        this.taskHistoryRepository = taskHistoryRepository;
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
        TaskHistory event = new TaskHistory("start",task.getTaskStatus(), TaskStatus.RUNNING);
        task.setTaskStatus(TaskStatus.RUNNING);
        task.setTimeStarted(new Date());
        task.setTimeLastUpdate(new Date());
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        event.setTask(task);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task done(Task task) {
        CountedEntities countedEntities = this.countedEntitiesService.countAll();
        task.setCountedEntitiesAtFinish(countedEntities);
        TaskHistory event = new TaskHistory("done",task.getTaskStatus(),TaskStatus.FINISHED);
        task.setTaskStatus(TaskStatus.FINISHED);
        task.setTimeLastUpdate(new Date());
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        event.setTask(task);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task,Exception e) {
        TaskHistory event = new TaskHistory(e.getMessage(),task.getTaskStatus(),TaskStatus.ERROR);
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        event.setTask(task);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task, Exception e, String msg) {
        TaskHistory event = new TaskHistory(msg+", "+e.getMessage(),task.getTaskStatus(),TaskStatus.ERROR);
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        event.setTask(task);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, Exception e) {
        TaskHistory event = new TaskHistory(e.getMessage(),task.getTaskStatus(),TaskStatus.WARN);
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        event.setTask(task);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, Exception e, String msg) {
        TaskHistory event = new TaskHistory(e.getMessage(),task.getTaskStatus(),TaskStatus.WARN);
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        event.setTask(task);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task event(Task task, String msg) {
        TaskHistory event = new TaskHistory(msg,task.getTaskStatus(),task.getTaskStatus());
        task.setTimeLastUpdate(new Date());
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        event.setTask(task);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, String msg) {
        TaskHistory event = new TaskHistory(msg,task.getTaskStatus(),TaskStatus.WARN);
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        event.setTask(task);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task, String msg) {
        TaskHistory event = new TaskHistory(msg,task.getTaskStatus(),TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task.setTaskStatus(TaskStatus.ERROR);
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        event.setTask(task);
        task = taskRepository.save(task);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findOne(id);
    }
}
