package org.woehlke.twitterwall.scheduled.service.backend;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.entities.parts.TaskStatus;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.scheduled.mq.msg.SendType;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterUrlService;

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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class TwitterUrlServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TwitterUrlServiceTest.class);

    @Autowired
    private TestdataProperties testdataProperties;

    @Autowired
    private TwitterUrlService twitterUrlService;

    @Test
    public void fetchUrlTest(){
        String msg = "fetchUrlTest ";
        log.info("------------------------------------");
        log.info(msg);

        String descriptionTask = "Make it so, Scotty";
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_SEARCH;
        SendType sendType = SendType.NO_MQ;
        long taskId = 222L;

        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;

        Task task = new Task(descriptionTask,taskType,taskStatus,sendType,timeStarted,timeLastUpdate,timeFinished);

        List<String> exprectedUrls = testdataProperties.getOodm().getEntities().getUrl().getUrl();
        for(String exprectedUrl:exprectedUrls){
                log.info(msg+"expected: " + exprectedUrl);
                Url foundUrl = twitterUrlService.fetchTransientUrl(exprectedUrl,task);
                Assert.assertNotNull(foundUrl);
                log.info(msg+"found:    " + foundUrl.toString());
                Assert.assertEquals(exprectedUrl, foundUrl.getUrl());
        }
        log.info("------------------------------------");
    }

    @Test
    public void fetchTransientUrlsTest(){
        Map<String,String> urls = new HashMap<>();
        Map<String,String> hosts = new HashMap<>();
        for(String urlSrc : testdataProperties.getOodm().getEntities().getUrl().getUrl()){
            log.info(urlSrc);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlSrc);
            try {
                HttpClientContext context = HttpClientContext.create();
                CloseableHttpResponse response1 = httpclient.execute(httpGet,context);
                HttpHost target = context.getTargetHost();
                List<URI> redirectLocations = context.getRedirectLocations();
                URI location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations);
                log.info("Final HTTP location: " + location.toASCIIString());
                urls.put(urlSrc,location.toURL().toExternalForm());
                hosts.put(urlSrc,location.toURL().getHost());
                response1.close();
            } catch (URISyntaxException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        log.info("FETCHED HOST: Map<String,String> hosts = new HashMap<>()");
        log.info("FETCHED URL: Map<String,String> URLS = new HashMap<>();");
        for(Map.Entry<String,String> host:hosts.entrySet()){
            log.info("FETCHED HOST: hosts.put(\""+host.getKey()+"\",\""+host.getValue()+"\");");
        }
        for(Map.Entry<String,String> url:urls.entrySet()){
            log.info("FETCHED URL: URLS.put(\""+url.getKey()+"\",\""+url.getValue()+"\");");
        }
    }

}
