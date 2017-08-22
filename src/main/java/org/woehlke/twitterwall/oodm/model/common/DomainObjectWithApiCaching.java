package org.woehlke.twitterwall.oodm.model.common;

import org.woehlke.twitterwall.oodm.model.tasks.TaskType;

public interface DomainObjectWithApiCaching {

    Boolean isCached(TaskType taskType, long timeToLive);
}
