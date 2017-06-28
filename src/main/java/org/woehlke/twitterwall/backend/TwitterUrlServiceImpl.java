package org.woehlke.twitterwall.backend;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.exceptions.remote.FetchUrlException;
import org.woehlke.twitterwall.oodm.service.entities.UrlServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TwitterUrlServiceImpl implements TwitterUrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    @Override
    public Url fetchTransientUrl(String urlSrc) {
        Url newUrl = null;
        try {
            String display;
            String expanded;
            int[] indices = {};
            CloseableHttpClient httpclient = HttpClients.createMinimal();
            HttpGet httpGet = new HttpGet(urlSrc);
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse response1 = httpclient.execute(httpGet, context);
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URL location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations).toURL();
            display = location.getHost();
            expanded = location.toExternalForm();
            newUrl = new Url(display, expanded, urlSrc, indices);
            response1.close();
        } catch (ClientProtocolException e) {
            //throw new FetchUrlException(urlSrc, e);
            log.warn(e.getMessage());
        } catch (NullPointerException e) {
            //throw new FetchUrlException(urlSrc, e);
            log.warn(e.getMessage());
        } catch (IllegalArgumentException e){
            //throw new FetchUrlException(urlSrc, e);
            log.warn(e.getMessage());
        } catch (URISyntaxException e) {
            //throw new FetchUrlException(urlSrc, e);
        } catch (IOException e) {
            log.warn(e.getMessage());
            if(newUrl != null){
                return newUrl;
            }
            //throw new FetchUrlException(urlSrc, e);
        } catch (RuntimeException ex) {
            log.warn(ex.getMessage());
            //throw new FetchUrlException(urlSrc, ex);
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            //throw new FetchUrlException(urlSrc, ex);
        }
        if(newUrl == null){
            throw new FetchUrlException(urlSrc);
        } else {
            return newUrl;
        }
    }
}
