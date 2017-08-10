package org.woehlke.twitterwall.oodm.entities.common;

import org.woehlke.twitterwall.oodm.entities.parts.TaskType;

public interface DomainObjectWithApiCaching {

    Boolean isCached(TaskType taskType, long timeToLive);
}
