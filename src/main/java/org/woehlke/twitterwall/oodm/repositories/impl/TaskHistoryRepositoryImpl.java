package org.woehlke.twitterwall.oodm.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class TaskHistoryRepositoryImpl implements TaskHistoryRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public TaskHistoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TaskHistory findByUniqueId(TaskHistory domainObject) {
        String name="TaskHistory.findByUniqueId";
        TypedQuery<TaskHistory> query = entityManager.createNamedQuery(name,TaskHistory.class);
        query.setParameter("taskType",domainObject.getIdTask());
        query.setParameter("timeStarted",domainObject.getTimeEvent());
        TaskHistory result = query.getSingleResult();
        return result;
    }
}
