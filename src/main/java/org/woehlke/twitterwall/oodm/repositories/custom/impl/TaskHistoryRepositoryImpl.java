package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.repositories.custom.TaskHistoryRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

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
        query.setParameter("idTask",domainObject.getIdTask());
        query.setParameter("timeStarted",domainObject.getTimeEvent());
        List<TaskHistory> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }
}
