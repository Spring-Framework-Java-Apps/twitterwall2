package org.woehlke.twitterwall.oodm.repository.application.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.repository.application.TaskRepository;
import org.woehlke.twitterwall.oodm.repository.common.impl.DomainRepositoryImpl;

import javax.persistence.TypedQuery;

/**
 * Created by tw on 09.07.17.
 */
@Repository
public class TaskRepositoryImpl extends DomainRepositoryImpl<Task> implements TaskRepository {

    private static final Logger log = LoggerFactory.getLogger(TaskRepositoryImpl.class);

    @Override
    public Task findById(long id) {
        String name = "Task.findById";
        log.debug(name);
        try {
            TypedQuery<Task> query = entityManager.createNamedQuery(name, Task.class);
            query.setParameter("id", id);
            Task result = query.getSingleResult();
            log.debug(name+" found: " + id);
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(name+" not found: " + id);
            throw e;
        }
    }
}
