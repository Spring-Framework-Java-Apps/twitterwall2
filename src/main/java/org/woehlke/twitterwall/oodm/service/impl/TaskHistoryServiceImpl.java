package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.dao.TaskHistoryDao;
import org.woehlke.twitterwall.oodm.repositories.TaskHistoryRepository;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;

/**
 * Created by tw on 11.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private static final Logger log = LoggerFactory.getLogger(TaskHistoryServiceImpl.class);

    private final TaskHistoryDao taskHistoryDao;

    private final TaskHistoryRepository taskHistoryRepository;

    @Autowired
    public TaskHistoryServiceImpl(TaskHistoryDao taskHistoryDao, TaskHistoryRepository taskHistoryRepository) {
        this.taskHistoryDao = taskHistoryDao;
        this.taskHistoryRepository = taskHistoryRepository;
    }

    @Override
    public TaskHistory store(TaskHistory domainObject) {
        return taskHistoryRepository.save(domainObject);
        /*
        try {
            TaskHistory taskHistoryPersistent = this.taskHistoryDao.findById(domainObject.getId());
            if(domainObject.compareTo(taskHistoryPersistent)==0) {
                return taskHistoryDao.update(domainObject);
            } else {
                return taskHistoryDao.persist(domainObject);
            }
        } catch (EmptyResultDataAccessException e) {
            return taskHistoryDao.persist(domainObject);
        }
        */
    }

    @Override
    public TaskHistory create(TaskHistory domainObject) {
        return taskHistoryRepository.save(domainObject);
        //return taskHistoryDao.persist(domainObject);
    }

    @Override
    public TaskHistory update(TaskHistory domainObject) {
        return taskHistoryRepository.save(domainObject);
        //return taskHistoryDao.update(domainObject);
    }

    @Override
    public Page<TaskHistory> getAll(Pageable pageRequest) {
        return taskHistoryRepository.findAll(pageRequest);
        //return taskHistoryDao.getAll(TaskHistory.class, pageRequest);
    }

    @Override
    public long count() {
        return taskHistoryRepository.count();
        //return taskHistoryDao.count(TaskHistory.class);
    }

    @Override
    public Page<TaskHistory> findByTask(Task oneTask,Pageable pageRequest) {
        return taskHistoryDao.findByTask(oneTask,pageRequest);
    }

    @Override
    public TaskHistory findById(long id) {
        return taskHistoryRepository.findOne(id);
        //return taskHistoryDao.findById(id);
    }
}
