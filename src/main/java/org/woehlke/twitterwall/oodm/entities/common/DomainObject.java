package org.woehlke.twitterwall.oodm.entities.common;

import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;

/**
 * Created by tw on 24.06.17.
 */
public interface DomainObject<T extends DomainObject> extends DomainObjectMinimal<T>  {

    TaskInfo getTaskInfo();

    void setTaskInfo(TaskInfo taskInfo);

    Task getCreatedBy();

    void setCreatedBy(Task createdBy);

    Task getUpdatedBy();

    void setUpdatedBy(Task updatedBy);

}
