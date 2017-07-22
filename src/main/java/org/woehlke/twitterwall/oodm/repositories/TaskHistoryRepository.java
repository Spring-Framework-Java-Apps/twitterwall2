package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TaskHistoryRepository extends DomainObjectMinimalRepository<TaskHistory> {
    Page<TaskHistory> findByTask(Task task, Pageable pageRequest);

    @Query(name="TaskHistory.findByUniqueId")
    TaskHistory findByUniqueId(@Param("domainObject") TaskHistory domainObject);
}
