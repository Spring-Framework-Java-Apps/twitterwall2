package org.woehlke.twitterwall.oodm.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskStatus;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class TaskServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceTest.class);

    @Autowired
    private TaskService taskService;

    //TODO: #198 https://github.com/phasenraum2010/twitterwall2/issues/198
    @Autowired
    private TestdataProperties testdataProperties;

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Commit
    @Test
    public void areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(taskService);
        Assert.assertNotNull(testdataProperties);
    }

    @Commit
    @Test
    public void fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Task> myPage = taskService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            Task myTask = myPage.getContent().iterator().next();
            Assert.assertNotNull(msg,myTask);
            Assert.assertNotNull(msg,myTask.getUniqueId());
            log.debug(msg+" found: "+myTask.getUniqueId());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void create() throws Exception {
        String msg = "TaskServiceTest.create";
        TaskType type = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task createdTask = taskService.create(msg,type,countedEntities);
        Assert.assertEquals(createdTask.getTaskStatus(),TaskStatus.READY);
    }

    @Test
    public void done() throws Exception {
        String msg = "TaskServiceTest.done";
        TaskType type = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task myTask = taskService.create(msg,type,countedEntities);
        Assert.assertEquals(myTask.getTaskStatus(),TaskStatus.READY);
        countedEntities = countedEntitiesService.countAll();
        Task createdTask = taskService.done(myTask,countedEntities);
        Assert.assertEquals(createdTask.getTaskStatus(),TaskStatus.FINISHED);
        Assert.assertEquals(TaskStatus.FINISHED,createdTask.getTaskStatus());
    }

    @Test
    public void error() throws Exception {
        String msg = "TaskServiceTest.error";
        TaskType type = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task myTask = taskService.create(msg,type,countedEntities);
        Assert.assertEquals(myTask.getTaskStatus(),TaskStatus.READY);
        countedEntities = countedEntitiesService.countAll();
        Task createdTask = taskService.error(myTask,msg,countedEntities);
        Assert.assertEquals(createdTask.getTaskStatus(),TaskStatus.ERROR);
        Assert.assertEquals(TaskStatus.ERROR,createdTask.getTaskStatus());
    }

    @Test
    public void warn() throws Exception {
        String msg = "TaskServiceTest.error";
        TaskType type = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task myTask = taskService.create(msg,type,countedEntities);
        Assert.assertEquals(myTask.getTaskStatus(),TaskStatus.READY);
        countedEntities = countedEntitiesService.countAll();
        Task createdTask = taskService.warn(myTask,msg,countedEntities);
        Assert.assertEquals(createdTask.getTaskStatus(),TaskStatus.WARN);
        Assert.assertEquals(TaskStatus.WARN,createdTask.getTaskStatus());
    }

    @Test
    public void event() throws Exception {
        String msg = "TaskServiceTest.error";
        TaskType type = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task myTask = taskService.create(msg,type,countedEntities);
        TaskStatus oldStatus = myTask.getTaskStatus();
        Assert.assertEquals(myTask.getTaskStatus(),TaskStatus.READY);
        countedEntities = countedEntitiesService.countAll();
        Task createdTask = taskService.event(myTask,msg,countedEntities);
        Assert.assertEquals(createdTask.getTaskStatus(),oldStatus);
        Assert.assertEquals(oldStatus,createdTask.getTaskStatus());
    }

    @Test
    public void start() throws Exception {
        String msg = "TaskServiceTest.error";
        TaskType type = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task myTask = taskService.create(msg,type,countedEntities);
        Assert.assertEquals(myTask.getTaskStatus(),TaskStatus.READY);
        countedEntities = countedEntitiesService.countAll();
        Task createdTask = taskService.start(myTask,countedEntities);
        Assert.assertEquals(createdTask.getTaskStatus(),TaskStatus.RUNNING);
        Assert.assertEquals(TaskStatus.RUNNING,createdTask.getTaskStatus());
    }

    @Test
    public void finalError() throws Exception {
        String msg = "TaskServiceTest.error";
        TaskType type = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task myTask = taskService.create(msg,type,countedEntities);
        Assert.assertEquals(myTask.getTaskStatus(),TaskStatus.READY);
        countedEntities = countedEntitiesService.countAll();
        Task createdTask = taskService.finalError(myTask,msg,countedEntities);
        Assert.assertEquals(createdTask.getTaskStatus(),TaskStatus.FINAL_ERROR);
        Assert.assertEquals(TaskStatus.FINAL_ERROR,createdTask.getTaskStatus());
    }

}