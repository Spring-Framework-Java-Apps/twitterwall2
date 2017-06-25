package org.woehlke.twitterwall.oodm.service.entities;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.UrlEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.common.AbstractFormattedText;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlByDisplayExpandedUrlException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlByUrlException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlCacheByUrlException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.PersistUrlCacheException;
import org.woehlke.twitterwall.oodm.exceptions.remote.FetchUrlException;
import org.woehlke.twitterwall.oodm.repository.entities.UrlCacheRepository;
import org.woehlke.twitterwall.oodm.repository.entities.UrlRepository;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UrlServiceImpl implements UrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    private final UrlRepository urlRepository;

    private final UrlCacheRepository urlCacheRepository;

    @Value("${twitterwall.url.fetchTestDataVerbose}")
    private boolean fetchTestDataVerbose;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository, UrlCacheRepository urlCacheRepository) {
        this.urlRepository = urlRepository;
        this.urlCacheRepository = urlCacheRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Url create(Url url) {
        return this.urlRepository.persist(url);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Url update(Url url) {
        return this.urlRepository.update(url);
    }

    @Override
    public Url findByDisplayExpandedUrl(String display, String expanded, String url) {
        if(url == null || expanded == null || display == null){
            throw new FindUrlByDisplayExpandedUrlException(display, expanded, url);
        }
        return this.urlRepository.findByDisplayExpandedUrl(display, expanded, url);
    }

    @Override
    public Url findByUrl(String url) {
        if(url == null){
            throw new FindUrlByUrlException();
        }
        return this.urlRepository.findByUrl(url);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Url store(Url url) {
        String msg = "Url.store: ";
        log.info(msg+"try to store: "+url.toString());
        if(url == null || url.getUrl()==null){
            throw new FindUrlByDisplayExpandedUrlException(url.getDisplay(), url.getExpanded(), url.getUrl());
        }
        String urlStr = url.getUrl();
        log.info(msg+"try to store by getPersistentUrlFor url="+urlStr);
        return getPersistentUrlFor(urlStr);
    }

    public List<Url> getTestData() {
        Map<String, String> hostsTest = new HashMap<>();
        hostsTest.put("https://t.co/lQlse7u93G", "port80guru.tumblr.com");
        Map<String, String> urlsTest = new HashMap<>();
        urlsTest.put("https://t.co/lQlse7u93G", "https://port80guru.tumblr.com/");

        if (fetchTestDataVerbose) {
            hostsTest = hosts;
            urlsTest = urls;
        }

        String urlSrc;
        String display;
        String expanded;
        int[] indices = {};
        List<Url> testData = new ArrayList<>();
        for (Map.Entry<String, String> url : urlsTest.entrySet()) {
            urlSrc = url.getKey();
            expanded = url.getValue();
            display = hostsTest.get(urlSrc);
            Url myUrl = new Url(display, expanded, urlSrc, indices);
            testData.add(myUrl);
        }
        return testData;
    }

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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Url getPersistentUrlFor(String url) {
        String msg = "Url.getPersistentUrlFor url="+url+" ";
        int indices[] = {};
        if (url == null) {
            return null;
        } else {
            try {
                log.info(msg+" try to find ");
                Url urlPers = this.urlRepository.findByUrl(url);
                log.info(msg+" found: "+urlPers);
                if(urlPers.isUrlAndExpandedTheSame()){
                    log.info(msg+" urlPers.isUrlAndExpandedTheSame "+urlPers.toString());
                }
                return urlPers;
            } catch (FindUrlByUrlException ex) {
                log.info(msg+" not found ");
                try {
                    log.info(msg+" try to find UrlCache");
                    UrlCache urlCache = urlCacheRepository.findByUrl(url);
                    log.info(msg+" found: "+urlCache);
                    String displayUrl = urlCache.getExpanded();
                    try {
                        URL myURL = new URL(urlCache.getExpanded());
                        displayUrl = myURL.getHost();
                    } catch (MalformedURLException exe) {
                        log.warn(exe.getMessage());
                    }
                    Url newUrl = new Url(displayUrl, urlCache.getExpanded(), urlCache.getUrl(), indices);
                    log.info(msg+" try to persist: "+newUrl.toString());
                    newUrl = this.urlRepository.persist(newUrl);
                    log.info(msg+" persisted: "+newUrl.toString());
                    return newUrl;
                } catch (FindUrlCacheByUrlException e) {
                    UrlCache urlCache = new UrlCache();
                    try {
                        log.info(msg + " try to fetchTransientUrl");
                        Url myUrl = this.fetchTransientUrl(url);
                        log.info(msg + " found by fetchTransientUrl: " + myUrl);
                        urlCache.setUrl(myUrl.getUrl());
                        urlCache.setExpanded(myUrl.getExpanded());
                        log.info(msg + " try to persist: " + urlCache.toString());
                        if (urlCache.isUrlAndExpandedTheSame()) {
                            urlCache = urlCacheRepository.persist(urlCache);
                            log.info(msg + " persisted: " + urlCache.toString());
                        } else {
                            log.info(msg + " not persisted: " + urlCache.toString());
                        }
                    } catch (PersistUrlCacheException puce){
                        log.info(msg+puce.getMessage());
                        urlCache.setUrl(url);
                        urlCache.setExpanded(url);
                    } catch (FetchUrlException fue)  {
                        log.info(msg+fue.getMessage());
                        urlCache.setUrl(url);
                        urlCache.setExpanded(url);
                    }
                    String displayUrl = urlCache.getExpanded();
                    try {
                        URL myURL = new URL(urlCache.getExpanded());
                        displayUrl = myURL.getHost();
                    } catch (MalformedURLException exe) {
                        log.warn(exe.getMessage());
                    }
                    Url newUrl = new Url(displayUrl, urlCache.getExpanded(), urlCache.getUrl(), indices);
                    log.info(msg+" try to persist: "+newUrl.toString());
                    newUrl =this.urlRepository.persist(newUrl);
                    log.info(msg+" persisted: "+newUrl.toString());
                    return newUrl;
                }
            }
        }
    }

    @Override
    public Set<Url> transformUrls(List<UrlEntity> urls) {
        Set<Url> myUrls = new LinkedHashSet<>();
        for (UrlEntity url : urls) {
            String display = url.getDisplayUrl();
            String expanded = url.getExpandedUrl();
            String urlStr = url.getUrl();
            int[] indices = url.getIndices();
            Url myUrlEntity = new Url(display, expanded, urlStr, indices);
            myUrls.add(myUrlEntity);
        }
        return myUrls;
    }

    @Override
    public Set<Url> getUrlsFor(User user) {
        Set<Url> urls = new LinkedHashSet<>();
        String description = user.getDescription();
        if (description != null) {
            Pattern urlPattern = Pattern.compile("("+Url.URL_PATTTERN_FOR_USER+")(" + AbstractFormattedText.stopChar + ")");
            Matcher m3 = urlPattern.matcher(description);
            while (m3.find()) {
                urls.add(this.fetchTransientUrl(m3.group(1)));
            }
            Pattern urlPattern2 = Pattern.compile("("+Url.URL_PATTTERN_FOR_USER+")$");
            Matcher m4 = urlPattern2.matcher(description);
            while (m4.find()) {
                urls.add(this.fetchTransientUrl(m4.group(1)));
            }
        }
        String userWebpage = user.getUrl();
        if(userWebpage != null) {
            Pattern urlPattern3 = Pattern.compile("^("+Url.URL_PATTTERN_FOR_USER+")$");
            Matcher m5 = urlPattern3.matcher(description);
            while (m5.find()) {
                urls.add(this.fetchTransientUrl(m5.group(1)));
            }
        }
        return urls;
    }


    private static Map<String, String> hosts = new HashMap<>();

    static {
        hosts.put("https://t.co/4tDiIJ74eR", "www.ecentral.de");
        hosts.put("https://t.co/n9LlZXFjTc", "www.davitec.de");
        hosts.put("http://t.co/RzkVNLQLx6", "www.medien-kultur-it.net");
        hosts.put("https://t.co/N7Rq7beH8n", "www.quizpalme.de");
        hosts.put("https://t.co/Fb3UEeVWWk", "www.xing.com");
        hosts.put("https://t.co/Ycl7ZCbRsy", "www.thomaskieslich.de");
        hosts.put("https://t.co/feV9khihK3", "querbeet.docma.de");
        hosts.put("https://t.co/vFHaMjhQxK", "www.mittwald.de");
        hosts.put("https://t.co/4HBxydWdIE", "episch.org");
        hosts.put("https://t.co/uzzouytYxh", "www.oliver-thiele.de");
        hosts.put("http://t.co/7ccMD0G00p", "magic.hat-of-typo3.de");
        hosts.put("http://t.co/gnmbZ3qaY4", "www.pega-sus.de");
        hosts.put("https://t.co/Lp00eOlYYK", "www.chriwo.de");
        hosts.put("http://t.co/ZK8jQ2KEJD", "www.renekreupl.de");
        hosts.put("https://t.co/kM9PIn3BgS", "usetypo3.com");
        hosts.put("https://t.co/46Dz7qCB8L", "www.portachtzig.com");
        hosts.put("https://t.co/xnOcVzUTkm", "typo3worx.eu");
        hosts.put("https://t.co/hMMJBGFFKR", "www.davitec.de");
        hosts.put("http://t.co/imtLrCUAko", "christian-hellmuth.de");
        hosts.put("https://t.co/RvKQvMMpzF", "wwagner.net");
        hosts.put("https://t.co/cbsEBiSW2E", "www.web-vision.de");
        hosts.put("http://t.co/FGRp2dcyII", "www.marit.ag");
        hosts.put("https://t.co/Yfcyk3aqh1", "www.fanor.de");
        hosts.put("http://t.co/mNdNHD0dLO", "www.interactive-tools.de");
        hosts.put("http://t.co/a7oWdQhTin", "undkonsorten.com");
        hosts.put("https://t.co/Lq9HDdbM1S", "www.wapplersystems.de");
        hosts.put("https://t.co/7haKo3blhj", "www.tritum.de");
        hosts.put("https://t.co/qIDUMVN3WY", "gosna.de");
        hosts.put("https://t.co/t72OfGzzfd", "github.com");
        hosts.put("http://t.co/TC1JsRY0sY", "www.typo3-ruhr.org");
        hosts.put("http://t.co/hIxL9WeoP1", "www.schnittsteller.de");
        hosts.put("http://t.co/yspEKRBcSw", "randomprojects.de");
        hosts.put("https://t.co/lQlse7u93G", "port80guru.tumblr.com");
        hosts.put("http://t.co/75Hf3JV6JU", "www.typo3camp-berlin.de");
        hosts.put("https://t.co/gnmbZ3qaY4", "www.pega-sus.de");
        hosts.put("https://t.co/4g5pdzrWkM", "alinbu.net");
        hosts.put("http://t.co/nWxOAD6WNC", "www.agentur-brandung.de");
        hosts.put("http://t.co/5uLiz5mReg", "www.foodfindr.de");
        hosts.put("http://t.co/sgSySSyjol", "t3easy.de");
        hosts.put("http://t.co/N0wDJifTl8", "blog.sammyb.de");
        hosts.put("https://t.co/MuVYy9ahOi", "www.gkm.me");
        hosts.put("https://t.co/DWliJ3cEUT", "www.frauliessmann.de");
        hosts.put("https://t.co/OP7oBmBA3T", "spooner-web.de");
        hosts.put("https://t.co/z0lOKQCs0y", "typo3.com");
        hosts.put("https://t.co/okIuRGVRi1", "www.kay-strobach.de");
        hosts.put("https://t.co/f2vtNkiwTJ", "netz-basis.com");
        hosts.put("https://t.co/NWnpbiSrFg", "flyingcircus.io");
        hosts.put("https://t.co/HQpP0PXtCY", "www.rodejohann.de");
        hosts.put("http://t.co/tjFrOTqr1l", "www.cps-it.de");
        hosts.put("http://t.co/9Ct4HFvTSM", "www.plusb.de");
        hosts.put("http://t.co/8EuxwYs0va", "www.ideenwerft.com");
        hosts.put("http://t.co/MALJQIyYrt", "www.arndtteunissen.de");
        hosts.put("http://t.co/9YEDGUjbqK", "www.in2code.de");
        hosts.put("http://t.co/9P2qeHaGH7", "a-w.io");
        hosts.put("https://t.co/5s5ggTRpIX", "www.sgalinski.de");
        hosts.put("http://t.co/gnmbZ3HLPC", "www.pega-sus.de");
        hosts.put("http://t.co/CudjEZGLVT", "sebastian.kreideweiss.info");
    }

    private static Map<String, String> urls = new HashMap<>();

    static {
        urls.put("https://t.co/4tDiIJ74eR", "http://www.ecentral.de/");
        urls.put("https://t.co/n9LlZXFjTc", "https://www.davitec.de/");
        urls.put("http://t.co/RzkVNLQLx6", "http://www.medien-kultur-it.net/");
        urls.put("https://t.co/N7Rq7beH8n", "http://www.quizpalme.de/");
        urls.put("https://t.co/Fb3UEeVWWk", "https://www.xing.com/profile/Nicole_Cordes2");
        urls.put("https://t.co/Ycl7ZCbRsy", "https://www.thomaskieslich.de/");
        urls.put("https://t.co/feV9khihK3", "http://querbeet.docma.de/");
        urls.put("https://t.co/vFHaMjhQxK", "https://www.mittwald.de/");
        urls.put("https://t.co/4HBxydWdIE", "https://episch.org/");
        urls.put("https://t.co/uzzouytYxh", "https://www.oliver-thiele.de/");
        urls.put("http://t.co/7ccMD0G00p", "http://magic.hat-of-typo3.de/");
        urls.put("http://t.co/gnmbZ3qaY4", "https://www.pega-sus.de/home.html");
        urls.put("https://t.co/Lp00eOlYYK", "http://www.chriwo.de/");
        urls.put("http://t.co/ZK8jQ2KEJD", "http://www.renekreupl.de/");
        urls.put("https://t.co/kM9PIn3BgS", "https://usetypo3.com/");
        urls.put("https://t.co/46Dz7qCB8L", "https://www.portachtzig.com/");
        urls.put("https://t.co/xnOcVzUTkm", "http://typo3worx.eu/");
        urls.put("https://t.co/hMMJBGFFKR", "https://www.davitec.de/");
        urls.put("http://t.co/imtLrCUAko", "https://christian-hellmuth.de/");
        urls.put("https://t.co/RvKQvMMpzF", "https://wwagner.net/");
        urls.put("https://t.co/cbsEBiSW2E", "https://www.web-vision.de/");
        urls.put("http://t.co/FGRp2dcyII", "http://www.marit.ag/");
        urls.put("https://t.co/Yfcyk3aqh1", "http://www.fanor.de/");
        urls.put("http://t.co/mNdNHD0dLO", "https://www.interactive-tools.de/");
        urls.put("http://t.co/a7oWdQhTin", "http://undkonsorten.com/");
        urls.put("https://t.co/Lq9HDdbM1S", "https://www.wapplersystems.de/profil-sven-wappler/");
        urls.put("https://t.co/7haKo3blhj", "https://www.tritum.de/");
        urls.put("https://t.co/qIDUMVN3WY", "http://gosna.de/");
        urls.put("https://t.co/t72OfGzzfd", "https://github.com/markusguenther");
        urls.put("http://t.co/TC1JsRY0sY", "http://www.typo3-ruhr.org/");
        urls.put("http://t.co/hIxL9WeoP1", "http://www.schnittsteller.de/");
        urls.put("http://t.co/yspEKRBcSw", "http://randomprojects.de/ClubMate_RT/");
        urls.put("https://t.co/lQlse7u93G", "https://port80guru.tumblr.com/");
        urls.put("http://t.co/75Hf3JV6JU", "http://www.typo3camp-berlin.de/");
        urls.put("https://t.co/gnmbZ3qaY4", "https://www.pega-sus.de/home.html");
        urls.put("https://t.co/4g5pdzrWkM", "https://alinbu.net/");
        urls.put("http://t.co/nWxOAD6WNC", "https://www.agentur-brandung.de/");
        urls.put("http://t.co/5uLiz5mReg", "http://www.foodfindr.de/");
        urls.put("http://t.co/sgSySSyjol", "https://t3easy.de/index.php?id=2");
        urls.put("http://t.co/N0wDJifTl8", "https://blog.sammyb.de/");
        urls.put("https://t.co/MuVYy9ahOi", "https://www.gkm.me/");
        urls.put("https://t.co/DWliJ3cEUT", "http://www.frauliessmann.de/");
        urls.put("https://t.co/OP7oBmBA3T", "https://spooner-web.de/");
        urls.put("https://t.co/z0lOKQCs0y", "https://typo3.com/");
        urls.put("https://t.co/okIuRGVRi1", "http://www.kay-strobach.de/willkommen/");
        urls.put("https://t.co/f2vtNkiwTJ", "https://netz-basis.com/blog");
        urls.put("https://t.co/NWnpbiSrFg", "https://flyingcircus.io/");
        urls.put("https://t.co/HQpP0PXtCY", "http://www.rodejohann.de/");
        urls.put("http://t.co/tjFrOTqr1l", "http://www.cps-it.de/");
        urls.put("http://t.co/9Ct4HFvTSM", "http://www.plusb.de/");
        urls.put("http://t.co/8EuxwYs0va", "https://www.ideenwerft.com/");
        urls.put("http://t.co/MALJQIyYrt", "https://www.arndtteunissen.de/");
        urls.put("http://t.co/9YEDGUjbqK", "https://www.in2code.de/");
        urls.put("http://t.co/9P2qeHaGH7", "http://a-w.io/");
        urls.put("https://t.co/5s5ggTRpIX", "https://www.sgalinski.de/");
        urls.put("http://t.co/gnmbZ3HLPC", "https://www.pega-sus.de/home.html");
        urls.put("http://t.co/CudjEZGLVT", "http://sebastian.kreideweiss.info/");
    }
}
