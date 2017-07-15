package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;

/**
 * Created by tw on 15.07.17.
 */
public interface TaskHistoryRepository extends PagingAndSortingRepository<TaskHistory,Long> {
}
