package org.woehlke.twitterwall.backend.service.remote;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.configuration.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskStatus;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tw on 21.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment = SpringBootTest.WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TwitterUrlServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TwitterUrlServiceTest.class);

    @Autowired
    private TestdataProperties testdataProperties;

    @Autowired
    private TwitterUrlService twitterUrlService;

    @Test
    public void test001fetchUrlTest(){
        String msg = "fetchUrlTest ";
        log.debug(msg+"------------------------------------");

        String descriptionTask = "Make it so, Scotty";
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        long taskId = 222L;

        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;

        Task task = new Task(descriptionTask,taskType,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);

        List<String> exprectedUrls = testdataProperties.getOodm().getEntities().getUrl().getUrl();
        for(String exprectedUrl:exprectedUrls){
                log.debug(msg+"expected: " + exprectedUrl);
                Url foundUrl = twitterUrlService.fetchTransientUrl(exprectedUrl,task);
                Assert.assertNotNull(foundUrl);
                log.debug(msg+"found:    " + foundUrl.toString());
                Assert.assertEquals(exprectedUrl, foundUrl.getUrl());
        }
        log.debug(msg+"------------------------------------");
    }

    @Test
    public void test002fetchTransientUrlsTest(){
        String msg = "fetchTransientUrlsTest: ";
        log.debug(msg+"------------------------------------");
        Map<String,String> urls = new HashMap<>();
        Map<String,String> hosts = new HashMap<>();
        for(String urlSrc : testdataProperties.getOodm().getEntities().getUrl().getUrl()){
            log.debug(msg+urlSrc);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlSrc);
            try {
                HttpClientContext context = HttpClientContext.create();
                CloseableHttpResponse response1 = httpclient.execute(httpGet,context);
                HttpHost target = context.getTargetHost();
                List<URI> redirectLocations = context.getRedirectLocations();
                URI location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations);
                log.debug(msg+"Final HTTP location: " + location.toASCIIString());
                urls.put(urlSrc,location.toURL().toExternalForm());
                hosts.put(urlSrc,location.toURL().getHost());
                response1.close();
            } catch (URISyntaxException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        log.debug(msg+"FETCHED HOST: Map<String,String> hosts = new HashMap<>()");
        log.debug(msg+"FETCHED URL: Map<String,String> URLS = new HashMap<>();");
        for(Map.Entry<String,String> host:hosts.entrySet()){
            log.debug(msg+"FETCHED HOST: hosts.put(\""+host.getKey()+"\",\""+host.getValue()+"\");");
        }
        for(Map.Entry<String,String> url:urls.entrySet()){
            log.debug(msg+"FETCHED URL: URLS.put(\""+url.getKey()+"\",\""+url.getValue()+"\");");
        }
        log.debug(msg+"------------------------------------");
    }

}
