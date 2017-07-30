package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.repositories.custom.TaskRepositoryCustom;

import javax.persistence.EntityManager;

@Repository
public class TaskRepositoryImpl extends AbstractDomainRepositoryImpl<Task> implements TaskRepositoryCustom {

    @Autowired
    public TaskRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

}
