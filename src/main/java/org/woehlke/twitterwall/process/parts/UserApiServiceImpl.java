package org.woehlke.twitterwall.process.parts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.entities.common.UrlCache;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlByUrlException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlCacheByUrlException;
import org.woehlke.twitterwall.oodm.exceptions.remote.FetchUrlException;
import org.woehlke.twitterwall.oodm.service.entities.UrlCacheService;
import org.woehlke.twitterwall.oodm.service.entities.UrlService;

import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 22.06.17.
 */
@Service
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class UserApiServiceImpl implements UserApiService {

    private static final Logger log = LoggerFactory.getLogger(UserApiServiceImpl.class);

    private final UrlApiService urlApiService;

    private final UrlCacheService urlCacheService;

    private final UrlService urlService;

    @Autowired
    public UserApiServiceImpl(UrlApiService urlApiService, UrlCacheService urlCacheService, UrlService urlService) {
        this.urlApiService = urlApiService;
        this.urlCacheService = urlCacheService;
        this.urlService = urlService;
    }

    @Override
    public User getEntitiesForUrlDescription(User user) {
        String description = user.getDescription();
        user.setMentions(this.getMentions(description));
        Set<Url> urls = this.getUrls(description);
        Url myUrl = this.fetchUrl(user.getUrl());
        if (myUrl != null) {
            urls.add(myUrl);
        }
        user.setUrls(urls);
        user.setTags(this.getHashTags(description));
        log.debug("description " + description);
        log.debug("++++++++++++++++++++");
        for (HashTag hashTag : user.getTags()) {
            log.debug("found hashTag: " + hashTag.toString());
        }
        for (Url url : user.getUrls()) {
            log.debug("found url: " + url.toString());
        }
        for (Mention mention : user.getMentions()) {
            log.debug("found mention: " + mention.toString());
        }
        log.debug("++++++++++++++++++++");
        return user;
    }

    private Set<HashTag> getHashTags(String description) {
        Set<HashTag> hashTags = new LinkedHashSet<>();
        if (description != null) {
            Pattern hashTagPattern = Pattern.compile("#(\\w*)(" + stopChar + ")");
            Matcher m3 = hashTagPattern.matcher(description);
            while (m3.find()) {
                hashTags.add(new HashTag(m3.group(1), indices));
            }
            Pattern hashTagPattern2 = Pattern.compile("#(\\w*)$");
            Matcher m4 = hashTagPattern2.matcher(description);
            while (m4.find()) {
                hashTags.add(new HashTag(m4.group(1), indices));
            }
        }
        return hashTags;
    }

    private Set<Url> getUrls(String description) {
        Set<Url> urls = new LinkedHashSet<>();
        if (description != null) {
            Pattern hashTagPattern = Pattern.compile("(https://t\\.co/\\w*)(" + stopChar + ")");
            Matcher m3 = hashTagPattern.matcher(description);
            while (m3.find()) {
                urls.add(this.fetchUrl(m3.group(1)));
            }
            Pattern hashTagPattern2 = Pattern.compile("(https://t\\.co/\\w*)$");
            Matcher m4 = hashTagPattern2.matcher(description);
            while (m4.find()) {
                urls.add(this.fetchUrl(m4.group(1)));
            }
        }
        return urls;
    }

    private Url fetchUrl(String url) {
        if (url == null) {
            return null;
        } else {
            try {
                Url urlPers = urlService.findByUrl(url);
                return urlPers;
            } catch (FindUrlByUrlException ex) {
                try {
                    UrlCache urlCache = urlCacheService.findByUrl(url);
                    String displayUrl = urlCache.getExpanded();
                    try {
                        URL myURL = new URL(urlCache.getExpanded());
                        displayUrl = myURL.getHost();
                    } catch (MalformedURLException exe) {
                        log.warn(exe.getMessage());
                    }
                    Url newUrl = new Url(displayUrl, urlCache.getExpanded(), urlCache.getUrl(), indices);
                    return newUrl;
                } catch (FindUrlCacheByUrlException e) {
                    UrlCache urlCache = new UrlCache();
                    try {
                        Url myUrl = this.urlApiService.fetchUrl(url);
                        urlCache.setUrl(myUrl.getUrl());
                        urlCache.setExpanded(myUrl.getExpanded());
                    }  catch (FetchUrlException fue)  {
                        log.debug(fue.getMessage());
                        urlCache.setUrl(url);
                        urlCache.setExpanded(url);
                    }
                    urlCacheService.store(urlCache);
                    String displayUrl = urlCache.getExpanded();
                    try {
                        URL myURL = new URL(urlCache.getExpanded());
                        displayUrl = myURL.getHost();
                    } catch (MalformedURLException exe) {
                        log.warn(exe.getMessage());
                    }
                    Url newUrl = new Url(displayUrl, urlCache.getExpanded(), urlCache.getUrl(), indices);
                    return newUrl;
                }
            }
        }
    }

    private Set<Mention> getMentions(String description) {
        Set<Mention> mentions = new LinkedHashSet<>();
        if (description != null) {
            Pattern mentionPattern1 = Pattern.compile("@(\\w*)(" + stopChar + ")");
            Matcher m3 = mentionPattern1.matcher(description);
            while (m3.find()) {
                mentions.add(getMention(m3.group(1)));
            }
            Pattern mentionPattern2 = Pattern.compile("@(\\w*)$");
            Matcher m4 = mentionPattern2.matcher(description);
            while (m4.find()) {
                mentions.add(getMention(m4.group(1)));
            }
        }
        return mentions;
    }

    private Mention getMention(String mentionString) {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
        long idTwitter = new Date().getTime();
        String screenName = mentionString;
        String name = mentionString;
        int[] myindices = indices;
        return new Mention(idTwitter, screenName, name, myindices);
    }

    static private int[] indices = {};

    static private String stopChar;

    static {
        StringBuffer x = new StringBuffer("[\\s");
        x.append("\\!");
        x.append("\\%");
        x.append("\\&");
        x.append("\\'");
        x.append("\\(");
        x.append("\\)");
        x.append("\\*");
        x.append("\\+");
        x.append("\\,");
        x.append("\\-");
        x.append("\\.");
        x.append("\\/");
        x.append("\\:");
        x.append("\\;");
        x.append("\\=");
        x.append("\\?");
        x.append("\\[");
        x.append("\\]");
        x.append("\\^");
        x.append("\\…");
        x.append("\\`");
        x.append("\\{");
        x.append("\\|");
        x.append("\\}");
        x.append("\\~");
        x.append("\\\\z");
        x.append("]");
        stopChar = x.toString();
    }
}
