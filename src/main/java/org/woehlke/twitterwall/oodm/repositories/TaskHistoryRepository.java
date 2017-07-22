package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;
import org.woehlke.twitterwall.oodm.repositories.impl.TaskHistoryRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TaskHistoryRepository extends DomainObjectMinimalRepository<TaskHistory>,TaskHistoryRepositoryCustom {

    Page<TaskHistory> findByTask(Task task, Pageable pageRequest);

}
