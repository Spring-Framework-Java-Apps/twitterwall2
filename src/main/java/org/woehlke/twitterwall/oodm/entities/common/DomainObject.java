package org.woehlke.twitterwall.oodm.entities.common;


import org.woehlke.twitterwall.oodm.entities.parts.TaskType;

/**
 * Created by tw on 24.06.17.
 */
public interface DomainObject<T extends DomainObject> extends DomainObjectMinimal<T>  {

    Boolean isCached(TaskType taskType, long timeToLive);
}
