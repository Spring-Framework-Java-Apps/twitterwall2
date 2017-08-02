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
import org.woehlke.twitterwall.oodm.repositories.TaskHistoryRepository;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;

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
    public TaskHistory store(TaskHistory domainObject) {
        return taskHistoryRepository.save(domainObject);
    }

    @Override
    public Page<TaskHistory> getAll(Pageable pageRequest) {
        return taskHistoryRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return taskHistoryRepository.count();
    }

    @Override
    public Page<TaskHistory> findByTask(Task oneTask,Pageable pageRequest) {
        return taskHistoryRepository.findByTask(oneTask,pageRequest);
    }

    @Override
    public TaskHistory findById(long id) {
        return taskHistoryRepository.findOne(id);
    }
}
