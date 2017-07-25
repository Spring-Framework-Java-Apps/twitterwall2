package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.common.DomainObjectMinimalService;

/**
 * Created by tw on 09.07.17.
 */
public interface TaskService extends DomainObjectMinimalService<Task> {

    Task findById(long id);

    Task create(String msg,TaskType type,CountedEntities countedEntities);

    Task done(Task task,CountedEntities countedEntities);

    Task error(Task task, Exception e,CountedEntities countedEntities);

    Task error(Task task, Exception e, String msg,CountedEntities countedEntities);

    Task warn(Task task, Exception e,CountedEntities countedEntities);

    Task warn(Task task, Exception e, String msg,CountedEntities countedEntities);

    Task event(Task task, String msg,CountedEntities countedEntities);

    Task warn(Task task, String msg,CountedEntities countedEntities);

    Task error(Task task, String msg,CountedEntities countedEntities);

    Task start(Task task,CountedEntities countedEntities);

    Task finalError(Task task, String s, CountedEntities countedEntities);

    Task done(String logMsg, Task task, CountedEntities countedEntities);

}
