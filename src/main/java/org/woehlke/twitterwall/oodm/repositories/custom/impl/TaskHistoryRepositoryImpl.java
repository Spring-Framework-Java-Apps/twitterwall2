package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.repositories.custom.TaskHistoryRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TaskHistoryRepositoryImpl extends AbstractDomainRepositoryImpl<TaskHistory> implements TaskHistoryRepositoryCustom {

    @Autowired
    public TaskHistoryRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

}
