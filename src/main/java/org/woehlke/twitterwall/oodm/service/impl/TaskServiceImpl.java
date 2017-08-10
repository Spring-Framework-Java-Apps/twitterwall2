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
import org.woehlke.twitterwall.scheduled.mq.msg.SendType;

import java.util.Date;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
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
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task create(String msg, TaskType type, SendType sendType, CountedEntities countedEntities) {
        String descriptionTask = "start: "+msg;
        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;
        Task task = new Task(descriptionTask,type,taskStatus,sendType,timeStarted,timeLastUpdate,timeFinished);
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("create: "+msg,TaskStatus.READY, TaskStatus.READY,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task done(Task task,CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.FINISHED);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("DONE ",oldStatus,TaskStatus.FINISHED,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task error(Task task,Exception e,CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("error: "+e.getMessage(),oldStatus,TaskStatus.ERROR,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task error(Task task, Exception e, String msg,CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory(msg+", error: "+e.getMessage(),oldStatus,TaskStatus.ERROR,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task warn(Task task, Exception e,CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("warn: "+e.getMessage(),oldStatus,TaskStatus.WARN,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task warn(Task task, Exception e, String msg,CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("warn: "+msg+", "+e.getMessage(),oldStatus,TaskStatus.WARN,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task event(Task task, String msg,CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("event: "+msg,task.getTaskStatus(),oldStatus,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task warn(Task task, String msg,CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.WARN);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("warn: "+msg,oldStatus,TaskStatus.WARN,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task error(Task task, String msg,CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.ERROR);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("error: "+msg,oldStatus,TaskStatus.ERROR,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task start(Task task,CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.RUNNING);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("START",oldStatus,TaskStatus.RUNNING,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task finalError(Task task, String msg, CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.FINAL_ERROR);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("FINAL ERROR: "+msg,oldStatus,TaskStatus.FINAL_ERROR,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Task done(String logMsg, Task task, CountedEntities countedEntities) {
        TaskStatus oldStatus = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.FINISHED);
        task.setTimeLastUpdate(new Date());
        task = taskRepository.save(task);
        Date now = new Date();
        TaskHistory event = new TaskHistory("DONE "+logMsg,oldStatus,TaskStatus.FINISHED,now,task,countedEntities);
        event = taskHistoryRepository.save(event);
        log.debug(task.getUniqueId());
        log.trace(task.toString());
        return task;
    }
}
