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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.properties.BackendProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.service.impl.UrlServiceImpl;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterUrlService;

import java.io.IOException;
import java.net.MalformedURLException;
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


    @Override
    public Url fetchTransientUrl(final String urlSource,Task task) {
        String msg = "fetchTransientUrl " + urlSource + " ";
        log.debug(msg);
        Url newUrl = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse httpResponse = null;
        if (urlSource != null) {
            String display;
            String expanded;
            final long connTimeToLive = backendProperties.getUrl().getConnTimeToLive();
            final long maxIdleTime = backendProperties.getUrl().getMaxIdleTime();
            final TimeUnit connTimeToLiveTimeUnit = TimeUnit.SECONDS;
            httpclient = HttpClients.custom().setConnectionTimeToLive(connTimeToLive, connTimeToLiveTimeUnit).disableCookieManagement().evictIdleConnections(maxIdleTime, connTimeToLiveTimeUnit).build();
            HttpGet httpGetRequest = new HttpGet(urlSource);
            HttpClientContext context = HttpClientContext.create();
            HttpHost httpTargetHost = context.getTargetHost();
            try {
                httpResponse = httpclient.execute(httpGetRequest, context);
            } catch (IOException ioe) {
                log.warn(msg + ioe.getMessage());
            }
            if(httpResponse!= null) {
                URL location = null;
                URI uri = httpGetRequest.getURI();
                List<URI> redirectLocations = context.getRedirectLocations();
                try {
                    location = URIUtils.resolve(uri, httpTargetHost, redirectLocations).toURL();
                } catch (URISyntaxException urise) {
                    log.warn(msg + urise.getMessage());
                } catch (MalformedURLException e) {
                    log.warn(msg + e.getMessage());
                }
                if(location != null){
                    display = location.getHost();
                    expanded = location.toExternalForm();
                    newUrl = new Url(task,null,display, expanded, urlSource);
                }
                try {
                    httpResponse.close();
                } catch (IOException ioe2) {
                    ioe2.printStackTrace();
                    log.warn(msg + ioe2.getMessage());
                }
            }
        }
        if(httpclient != null) {
            try {
                httpclient.close();
            } catch (IOException ioe2) {
                ioe2.printStackTrace();
                log.warn(msg + ioe2.getMessage());
            }
        }
        return newUrl;
    }


    private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    private final BackendProperties backendProperties;

    @Autowired
    public TwitterUrlServiceImpl(BackendProperties backendProperties) {
        this.backendProperties = backendProperties;
    }
}
