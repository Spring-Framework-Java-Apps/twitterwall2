package org.woehlke.twitterwall.oodm.service.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.repository.application.TaskRepository;

import java.util.List;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task store(Task domainObject) {
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
}
