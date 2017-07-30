package org.woehlke.twitterwall.oodm.entities.common;

import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;

import java.util.Map;

/**
 * Created by tw on 14.07.17.
 */
public interface DomainObjectWithTask<T extends DomainObjectWithTask> extends DomainObject<T>, UniqueId {

    Task getCreatedBy();

    void setCreatedBy(Task createdBy);

    Task getUpdatedBy();

    void setUpdatedBy(Task updatedBy);

    TaskInfo getTaskInfo();

    void setTaskInfo(TaskInfo taskInfo);

}
