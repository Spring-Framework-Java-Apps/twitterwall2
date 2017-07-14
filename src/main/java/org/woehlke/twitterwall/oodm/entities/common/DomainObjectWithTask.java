package org.woehlke.twitterwall.oodm.entities.common;

import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskInfo;

/**
 * Created by tw on 14.07.17.
 */
public interface DomainObjectWithTask<T extends DomainObjectWithTask> extends DomainObject<T>{

    Task getCreatedBy();

    void setCreatedBy(Task createdBy);

    Task getUpdatedBy();

    void setUpdatedBy(Task updatedBy);

    TaskInfo getTaskInfo();

    void setTaskInfo(TaskInfo taskInfo);
}
