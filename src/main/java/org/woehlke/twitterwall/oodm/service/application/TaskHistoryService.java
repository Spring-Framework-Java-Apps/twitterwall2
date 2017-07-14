package org.woehlke.twitterwall.oodm.service.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.TaskHistory;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

/**
 * Created by tw on 11.07.17.
 */
public interface TaskHistoryService  extends DomainService<TaskHistory> {

    TaskHistory store(TaskHistory domainObject);

    TaskHistory create(TaskHistory domainObject);

    TaskHistory update(TaskHistory domainObject);

    Page<TaskHistory> findByTask(Task oneTask, Pageable pageRequest);

    TaskHistory findById(long id);
}
