package org.woehlke.twitterwall.oodm.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserListServiceTest implements DomainObjectMinimalServiceTest,DomainServiceWithTaskTest  {

    private static final Logger log = LoggerFactory.getLogger(UserListServiceTest.class);

    @Autowired
    private UserListService userListService;

    @Test
    public void areDependenciesLoaded() throws Exception {
        String msg = "areDependenciesLoaded: ";
        Assert.assertNotNull(userListService);
        log.debug(msg+" YES ");
    }

    @Commit
    @Test
    @Override
    public void fetchTestData() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void findById() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void getAll() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void count() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void findByUniqueId() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void store() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void create() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void update() throws Exception {

    }
}
