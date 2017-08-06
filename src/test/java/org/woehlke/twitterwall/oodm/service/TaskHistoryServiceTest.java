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
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TaskHistoryServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TaskHistoryServiceTest.class);

    @Autowired
    private TaskHistoryService taskHistoryService;

    @Autowired
    private TestdataProperties testdataProperties;

    @Test
    public void areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(taskHistoryService);
        Assert.assertNotNull(testdataProperties);
    }

    @Test
    public void fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<TaskHistory> myPage = taskHistoryService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            TaskHistory myTaskHistory = myPage.getContent().iterator().next();
            Assert.assertNotNull(msg,myTaskHistory);
            Assert.assertNotNull(msg,myTaskHistory.getUniqueId());
            log.debug(msg+" found: "+myTaskHistory.getUniqueId());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Test
    public void store() throws Exception {}

}
