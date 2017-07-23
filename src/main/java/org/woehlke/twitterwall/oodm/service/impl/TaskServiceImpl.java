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
        Task task = new Task("start: "+msg,type);
        task.setCountedEntitiesAtStart(countedEntities);
        TaskHistory event = new TaskHistory("create: "+msg,TaskStatus.READY, TaskStatus.READY);
        task.setTaskStatus(TaskStatus.RUNNING);
        task.setTimeStarted(new Date());
        task.setTimeLastUpdate(new Date());
        task.addHistory(event);
        event.setTask(task);
        task = taskRepository.save(task);
        event.setIdTask(task.getId());
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task done(Task task) {
        CountedEntities countedEntities = this.countedEntitiesService.countAll();
        task.setCountedEntitiesAtFinish(countedEntities);
        TaskHistory event = new TaskHistory("done",task.getTaskStatus(),TaskStatus.FINISHED);
        event.setIdTask(task.getId());
        task.setTaskStatus(TaskStatus.FINISHED);
        task.setTimeLastUpdate(new Date());
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        task = taskRepository.save(task);
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task,Exception e) {
        TaskHistory event = new TaskHistory("error: "+e.getMessage(),task.getTaskStatus(),TaskStatus.ERROR);
        event.setIdTask(task.getId());
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        task = taskRepository.save(task);
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task, Exception e, String msg) {
        TaskHistory event = new TaskHistory(msg+", error: "+e.getMessage(),task.getTaskStatus(),TaskStatus.ERROR);
        event.setIdTask(task.getId());
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        task = taskRepository.save(task);
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, Exception e) {
        TaskHistory event = new TaskHistory("warn: "+e.getMessage(),task.getTaskStatus(),TaskStatus.WARN);
        event.setIdTask(task.getId());
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        task = taskRepository.save(task);
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, Exception e, String msg) {
        TaskHistory event = new TaskHistory("warn: "+msg+", "+e.getMessage(),task.getTaskStatus(),TaskStatus.WARN);
        event.setIdTask(task.getId());
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        task = taskRepository.save(task);
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task event(Task task, String msg) {
        TaskHistory event = new TaskHistory("event: "+msg,task.getTaskStatus(),task.getTaskStatus());
        event.setIdTask(task.getId());
        task.setTimeLastUpdate(new Date());
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        task = taskRepository.save(task);
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, String msg) {
        TaskHistory event = new TaskHistory("warn: "+msg,task.getTaskStatus(),TaskStatus.WARN);
        event.setIdTask(task.getId());
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        task = taskRepository.save(task);
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task, String msg) {
        TaskHistory event = new TaskHistory("error: "+msg,task.getTaskStatus(),TaskStatus.ERROR);
        event.setIdTask(task.getId());
        task.setTimeLastUpdate(new Date());
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        task = taskRepository.save(task);
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findOne(id);
    }

    @Override
    public Task start(Task task) {
        CountedEntities countedEntities = this.countedEntitiesService.countAll();
        task.setCountedEntitiesAtFinish(countedEntities);
        TaskHistory event = new TaskHistory("start",task.getTaskStatus(),TaskStatus.RUNNING);
        event.setIdTask(task.getId());
        task.setTaskStatus(TaskStatus.RUNNING);
        task.setTimeLastUpdate(new Date());
        event = taskHistoryRepository.save(event);
        task.addHistory(event);
        task = taskRepository.save(task);
        event.setTask(task);
        task.addHistory(event);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }
}
