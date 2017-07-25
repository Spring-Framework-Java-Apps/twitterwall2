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

import java.util.Date;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    private final TaskHistoryRepository taskHistoryRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskHistoryRepository taskHistoryRepository) {
        this.taskRepository = taskRepository;
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
    public Task findById(long id) {
        return taskRepository.findOne(id);
    }

    @Override
    public Task create(String msg,TaskType type,CountedEntities countedEntities) {
        Task task = new Task("start: "+msg,type);
        task.setTaskStatus(TaskStatus.RUNNING);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("create: "+msg,TaskStatus.READY, TaskStatus.READY,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task done(Task task,CountedEntities countedEntities) {
        task.setTaskStatus(TaskStatus.FINISHED);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("DONE ",task.getTaskStatus(),TaskStatus.FINISHED,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task,Exception e,CountedEntities countedEntities) {
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("error: "+e.getMessage(),task.getTaskStatus(),TaskStatus.ERROR,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task, Exception e, String msg,CountedEntities countedEntities) {
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory(msg+", error: "+e.getMessage(),task.getTaskStatus(),TaskStatus.ERROR,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, Exception e,CountedEntities countedEntities) {
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("warn: "+e.getMessage(),task.getTaskStatus(),TaskStatus.WARN,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, Exception e, String msg,CountedEntities countedEntities) {
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("warn: "+msg+", "+e.getMessage(),task.getTaskStatus(),TaskStatus.WARN,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task event(Task task, String msg,CountedEntities countedEntities) {
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("event: "+msg,task.getTaskStatus(),task.getTaskStatus(),countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task warn(Task task, String msg,CountedEntities countedEntities) {
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("warn: "+msg,task.getTaskStatus(),TaskStatus.WARN,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task error(Task task, String msg,CountedEntities countedEntities) {
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("error: "+msg,task.getTaskStatus(),TaskStatus.ERROR,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task start(Task task,CountedEntities countedEntities) {
        task.setTaskStatus(TaskStatus.RUNNING);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("START",task.getTaskStatus(),TaskStatus.RUNNING,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task finalError(Task task, String msg, CountedEntities countedEntities) {
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("FINAL ERROR: "+msg,task.getTaskStatus(),TaskStatus.ERROR,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }

    @Override
    public Task done(String logMsg, Task task, CountedEntities countedEntities) {
        task.setTaskStatus(TaskStatus.FINISHED);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        TaskHistory event = new TaskHistory("DONE "+logMsg,task.getTaskStatus(),TaskStatus.FINISHED,countedEntities);
        event.setIdTask(task.getId());
        event.setTask(task);
        event = taskHistoryRepository.save(event);
        log.debug(task.toString());
        return task;
    }
}
