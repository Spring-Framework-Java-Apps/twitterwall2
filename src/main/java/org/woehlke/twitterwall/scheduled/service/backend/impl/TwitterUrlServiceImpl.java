package org.woehlke.twitterwall.scheduled.service.backend.impl;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.service.impl.UrlServiceImpl;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterUrlService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TwitterUrlServiceImpl implements TwitterUrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    @Value("${twitterwall.backend.url.connTimeToLive}")
    private long connTimeToLive;

    @Value("${twitterwall.backend.url.maxIdleTime}")
    private long maxIdleTime;

    @Override
    public Url fetchTransientUrl(String urlSrc) {
        String msg = "fetchTransientUrl "+urlSrc+" ";
        log.debug(msg);
        if(urlSrc == null) {
            throw new IllegalArgumentException(msg);
        }
        Url newUrl = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response1 = null;
        try {
            String display;
            String expanded;
            int[] indices = {};
            httpclient = HttpClients.custom().setConnectionTimeToLive(connTimeToLive, TimeUnit.SECONDS).disableCookieManagement().evictIdleConnections(maxIdleTime, TimeUnit.SECONDS).build();
            HttpGet httpGet = new HttpGet(urlSrc);
            HttpClientContext context = HttpClientContext.create();
            response1 = httpclient.execute(httpGet, context);
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URL location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations).toURL();
            display = location.getHost();
            expanded = location.toExternalForm();
            newUrl = new Url(display, expanded, urlSrc, indices);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            log.warn(msg+ioe.getMessage());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            log.warn(msg+npe.getMessage());
        } catch (RuntimeException re){
            re.printStackTrace();
            log.warn(msg+re.getMessage());
        } catch (URISyntaxException urise) {
            urise.printStackTrace();
            log.warn(msg+urise.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.warn(msg+e.getMessage());
        } finally {
            if(response1 != null){
                try {
                    response1.close();
                } catch (IOException ioe2){
                    ioe2.printStackTrace();
                    log.warn(msg+ioe2.getMessage());
                }
            }
            if(httpclient!=null) {
                try {
                    httpclient.close();
                } catch (IOException ioe2){
                    ioe2.printStackTrace();
                    log.warn(msg+ioe2.getMessage());
                }
            }
        }
        return newUrl;
    }
}
