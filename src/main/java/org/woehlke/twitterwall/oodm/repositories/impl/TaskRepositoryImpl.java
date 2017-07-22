package org.woehlke.twitterwall.oodm.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Task;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class TaskRepositoryImpl implements TaskRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public TaskRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Task findByUniqueId(Task domainObject) {
        String name="Task.findByUniqueId";
        TypedQuery<Task> query = entityManager.createNamedQuery(name,Task.class);
        query.setParameter("taskType",domainObject.getTaskType());
        query.setParameter("timeStarted",domainObject.getTimeStarted());
        Task result = query.getSingleResult();
        return result;
    }
}
