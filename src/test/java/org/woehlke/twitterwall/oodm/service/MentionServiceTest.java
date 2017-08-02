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
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.entities.Mention;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class MentionServiceTest {

    private static final Logger log = LoggerFactory.getLogger(MentionServiceTest.class);

    @Autowired
    private MentionService mentionService;

    //TODO: #198 https://github.com/phasenraum2010/twitterwall2/issues/198
    @Autowired
    private TestdataProperties testdataProperties;

    @Commit
    @Test
    public void areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(mentionService);
        Assert.assertNotNull(testdataProperties);
    }

    @Commit
    @Test
    public void fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Mention> myPage = mentionService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            Mention myMedia = myPage.getContent().iterator().next();
            Assert.assertNotNull(msg,myMedia);
            Assert.assertNotNull(msg,myMedia.getUniqueId());
            log.debug(msg+" found: "+myMedia.getUniqueId());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Test
    public void createProxyMention() throws Exception {

    }

}
