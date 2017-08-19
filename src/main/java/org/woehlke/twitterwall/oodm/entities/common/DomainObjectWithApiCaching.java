package org.woehlke.twitterwall.oodm.entities.common;

import org.woehlke.twitterwall.oodm.entities.tasks.TaskType;

public interface DomainObjectWithApiCaching {

    Boolean isCached(TaskType taskType, long timeToLive);
}
