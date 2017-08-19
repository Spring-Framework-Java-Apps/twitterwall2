package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.TaskHistory;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.TaskHistoryRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TaskHistoryRepository extends
    DomainObjectMinimalRepository<TaskHistory>,
    TaskHistoryRepositoryCustom,
    PagingAndSortingRepository<TaskHistory,Long> {

    Page<TaskHistory> findByTask(Task task, Pageable pageRequest);

}
