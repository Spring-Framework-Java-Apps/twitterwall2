package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

public interface TaskHistoryRepositoryCustom extends DomainObjectMinimalRepository<TaskHistory> {

    TaskHistory findByUniqueId(TaskHistory domainObject);
}
