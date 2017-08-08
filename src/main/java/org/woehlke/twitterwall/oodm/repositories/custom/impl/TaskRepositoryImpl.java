package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.repositories.custom.TaskRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepositoryCustom {

    private final EntityManager entityManager;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskRepositoryImpl(EntityManager entityManager,DataSource dataSource) {
        this.entityManager = entityManager;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Task findByUniqueId(Task domainObject) {
        String name="Task.findByUniqueId";
        TypedQuery<Task> query = entityManager.createNamedQuery(name,Task.class);
        query.setParameter("taskType",domainObject.getTaskType());
        query.setParameter("timeStarted",domainObject.getTimeStarted());
        List<Task> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }

    @Override
    public void deleteAllDomainData() {

        String SQL_DELETE_ALL_ROWS[] = {
            "delete from userprofile_url",
            "delete from userprofile_mention",
            "delete from userprofile_hashtag",
            "delete from userprofile_media",
            "delete from userprofile_tickersymbol",
            "delete from tweet_tickersymbol",
            "delete from tweet_mention",
            "delete from tweet_media",
            "delete from tweet_hashtag",
            "delete from tweet_url",
            "delete from url",
            "delete from tickersymbol",
            "delete from mention",
            "delete from media",
            "delete from hashtag",
            "delete from tweet",
            "delete from userprofile",
            "delete from task_history",
            "delete from task"
        };

        for(String SQL : SQL_DELETE_ALL_ROWS){
            jdbcTemplate.execute(SQL);
        }
    }
}
