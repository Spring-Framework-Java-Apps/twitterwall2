package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.Task;

public interface TaskRepositoryCustom {

    Task findByUniqueId(Task domainObject);
}
