package org.woehlke.twitterwall.process;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.woehlke.twitterwall.process.tasks.ScheduledTasksFacadeTest.PROFILE_URLS;

/**
 * Created by tw on 21.06.17.
 */
public class ScheduledTasksFacadeTest {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasksFacadeTest.class);

    @Test
    public void fetchUrlsTest(){
        Map<String,String> urls = new HashMap<>();
        Map<String,String> hosts = new HashMap<>();
        for(String urlSrc :PROFILE_URLS){
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
