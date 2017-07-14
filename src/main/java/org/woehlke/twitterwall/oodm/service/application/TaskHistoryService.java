package org.woehlke.twitterwall.oodm.service.application;

import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.TaskHistory;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

import java.util.List;

/**
 * Created by tw on 11.07.17.
 */
public interface TaskHistoryService  extends DomainService<TaskHistory> {

    TaskHistory store(TaskHistory domainObject);

    TaskHistory create(TaskHistory domainObject);

    TaskHistory update(TaskHistory domainObject);

    List<TaskHistory> findByTask(Task oneTask);

    TaskHistory findById(long id);
}
