package org.woehlke.twitterwall.oodm.dao;

import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDao;

/**
 * Created by tw on 09.07.17.
 */
public interface TaskDao extends DomainDao<Task> {
    Task findById(long id);
}
