package org.woehlke.twitterwall.oodm.repository.application;

import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepository;

/**
 * Created by tw on 09.07.17.
 */
public interface TaskRepository extends DomainRepository<Task> {
    Task findById(long id);
}
