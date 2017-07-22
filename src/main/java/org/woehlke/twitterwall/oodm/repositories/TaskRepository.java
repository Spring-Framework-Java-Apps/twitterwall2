package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TaskRepository extends DomainObjectMinimalRepository<Task> {

    @Query(name="Task.findByUniqueId")
    Task findByUniqueId(@Param("domainObject") Task domainObject);
}
