package org.woehlke.twitterwall.oodm.repository.application;

import org.woehlke.twitterwall.oodm.entities.application.TaskHistory;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepository;

/**
 * Created by tw on 11.07.17.
 */
public interface TaskHistoryRepository extends DomainRepository<TaskHistory> {

    TaskHistory findById(long id);
}
