package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.repositories.custom.TaskRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
