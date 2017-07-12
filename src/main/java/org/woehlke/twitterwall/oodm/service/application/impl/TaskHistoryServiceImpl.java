package org.woehlke.twitterwall.oodm.service.application.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.TaskHistory;
import org.woehlke.twitterwall.oodm.repository.application.TaskHistoryRepository;
import org.woehlke.twitterwall.oodm.service.application.TaskHistoryService;

import java.util.List;

/**
 * Created by tw on 11.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private static final Logger log = LoggerFactory.getLogger(TaskHistoryServiceImpl.class);

    private final TaskHistoryRepository taskHistoryRepository;

    @Autowired
    public TaskHistoryServiceImpl(TaskHistoryRepository taskHistoryRepository) {
        this.taskHistoryRepository = taskHistoryRepository;
    }

    @Override
    public TaskHistory store(TaskHistory domainObject, Task task) {
        try {
            TaskHistory taskHistoryPersistent = this.taskHistoryRepository.findById(domainObject.getId());
            if(domainObject.compareTo(taskHistoryPersistent)==0) {
                return taskHistoryRepository.update(domainObject);
            } else {
                return taskHistoryRepository.persist(domainObject);
            }
        } catch (EmptyResultDataAccessException e) {
            return taskHistoryRepository.persist(domainObject);
        }
    }

    @Override
    public TaskHistory create(TaskHistory domainObject) {
        return taskHistoryRepository.persist(domainObject);
    }

    @Override
    public TaskHistory update(TaskHistory domainObject) {
        return taskHistoryRepository.update(domainObject);
    }

    @Override
    public List<TaskHistory> getAll() {
        return taskHistoryRepository.getAll(TaskHistory.class);
    }

    @Override
    public long count() {
        return taskHistoryRepository.count(TaskHistory.class);
    }

    @Override
    public List<TaskHistory> findByTask(Task oneTask) {
        return taskHistoryRepository.findByTask(oneTask);
    }
}
