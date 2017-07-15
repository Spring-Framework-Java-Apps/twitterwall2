package org.woehlke.twitterwall.oodm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDao;

/**
 * Created by tw on 11.07.17.
 */
public interface TaskHistoryDao extends DomainDao<TaskHistory> {

    TaskHistory findById(long id);

    Page<TaskHistory> findByTask(Task oneTask, Pageable pageRequest);
}
