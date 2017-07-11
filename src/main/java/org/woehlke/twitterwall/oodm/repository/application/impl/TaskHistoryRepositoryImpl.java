package org.woehlke.twitterwall.oodm.repository.application.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.application.TaskHistory;
import org.woehlke.twitterwall.oodm.repository.application.TaskHistoryRepository;
import org.woehlke.twitterwall.oodm.repository.common.impl.DomainRepositoryImpl;

import javax.persistence.TypedQuery;

/**
 * Created by tw on 11.07.17.
 */
@Repository
public class TaskHistoryRepositoryImpl extends DomainRepositoryImpl<TaskHistory> implements TaskHistoryRepository {

    private static final Logger log = LoggerFactory.getLogger(TaskHistoryRepositoryImpl.class);

    @Override
    public TaskHistory findById(long id) {
        String name = "TaskHistory.findById";
        log.debug(name);
        try {
            TypedQuery<TaskHistory> query = entityManager.createNamedQuery(name, TaskHistory.class);
            query.setParameter("id", id);
            TaskHistory result = query.getSingleResult();
            log.debug(name+" found: " + id);
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(name+" not found: " + id);
            throw e;
        }
    }
}
