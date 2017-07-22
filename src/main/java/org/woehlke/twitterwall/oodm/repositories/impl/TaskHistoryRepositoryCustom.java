package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.TaskHistory;

public interface TaskHistoryRepositoryCustom {

    TaskHistory findByUniqueId(TaskHistory domainObject);
}
