package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TaskHistoryRepository extends PagingAndSortingRepository<TaskHistory,Long> {
    Page<TaskHistory> findByTask(Task task, Pageable pageRequest);
}
