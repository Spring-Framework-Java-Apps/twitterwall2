package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UrlEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterUrlService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.scheduled.service.transform.entities.UrlTransformService;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UrlTransformServiceImpl implements UrlTransformService {

    private static final Logger log = LoggerFactory.getLogger(UrlTransformServiceImpl.class);

    //private final TwitterUrlService twitterUrlService;

    public UrlTransformServiceImpl() {

    }

    @Override
    public Url transform(UrlEntity url) {
        String display = url.getDisplayUrl();
        String expanded = url.getExpandedUrl();
        String urlStr = url.getUrl();
        int[] indices = url.getIndices();
        Url myUrlEntity = new Url(display, expanded, urlStr, indices);
        return myUrlEntity;
    }
/*
    @Override
    public Set<Url> getUrlsFor(User user) {
        Set<Url> urls = new LinkedHashSet<>();
        String description = user.getDescription();
        if (description != null) {
            Pattern urlPattern = Pattern.compile("("+Url.URL_PATTTERN_FOR_USER+")(" + Entities.stopChar + ")");
            Matcher m3 = urlPattern.matcher(description);
            while (m3.find()) {
                urls.add(Url.getUrlFactory(m3.group(1)));
            }
            Pattern urlPattern2 = Pattern.compile("("+Url.URL_PATTTERN_FOR_USER+")$");
            Matcher m4 = urlPattern2.matcher(description);
            while (m4.find()) {
                urls.add(Url.getUrlFactory(m4.group(1)));
            }
            String userWebpage = user.getUrl();
            if(userWebpage != null) {
                Pattern urlPattern3 = Pattern.compile("^("+Url.URL_PATTTERN_FOR_USER+")$");
                Matcher m5 = urlPattern3.matcher(description);
                while (m5.find()) {
                    urls.add(Url.getUrlFactory(m5.group(1)));
                }
            }
        }
        return urls;
    }
    */

    @Override
    public Set<Url> getUrlsFor(TwitterProfile userSource) {
        Set<Url> urlsTarget = new LinkedHashSet<Url>();
        Map<String, Object> extraData = userSource.getExtraData();
        //TODO: see Debugger, Target: Application
        //TODO: bla
        return urlsTarget;
    }
}
