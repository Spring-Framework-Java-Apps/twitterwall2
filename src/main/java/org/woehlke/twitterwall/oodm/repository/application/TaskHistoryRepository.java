package org.woehlke.twitterwall.oodm.repository.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.TaskHistory;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepository;

/**
 * Created by tw on 11.07.17.
 */
public interface TaskHistoryRepository extends DomainRepository<TaskHistory> {

    TaskHistory findById(long id);

    Page<TaskHistory> findByTask(Task oneTask, Pageable pageRequest);
}
