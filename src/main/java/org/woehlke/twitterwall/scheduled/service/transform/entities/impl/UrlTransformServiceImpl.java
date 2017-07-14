package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UrlEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.scheduled.service.transform.entities.UrlTransformService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.oodm.entities.entities.Url.URL_PATTTERN_FOR_USER_HTTP;
import static org.woehlke.twitterwall.oodm.entities.entities.Url.URL_PATTTERN_FOR_USER_HTTPS;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UrlTransformServiceImpl implements UrlTransformService {


    @Override
    public Url transform(UrlEntity url) {
        String display = url.getDisplayUrl();
        String expanded = url.getExpandedUrl();
        String urlStr = url.getUrl();
        int[] indices = url.getIndices();
        Url myUrlEntity = new Url(display, expanded, urlStr, indices);
        return myUrlEntity;
    }

    private Set<Url> getUrlsForDescription(String description) {
        Set<Url> urls = new LinkedHashSet<>();
        if (description != null) {

            Pattern myUrl1 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + Entities.stopChar + ")");
            Matcher m1 = myUrl1.matcher(description);
            while (m1.find()) {
                urls.add(Url.getUrlFactory(m1.group(2)));
            }
            Pattern myUrl2 = Pattern.compile("^(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + Entities.stopChar + ")");
            Matcher m2 = myUrl2.matcher(description);
            while (m2.find()) {
                urls.add(Url.getUrlFactory(m2.group(1)));
            }
            Pattern myUrl3 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")$");
            Matcher m3 = myUrl3.matcher(description);
            while (m3.find()) {
                urls.add(Url.getUrlFactory(m3.group(2)));
            }
            Pattern myUrl11 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + Entities.stopChar + ")");
            Matcher m11 = myUrl11.matcher(description);
            while (m11.find()) {
                urls.add(Url.getUrlFactory(m11.group(2)));
            }
            Pattern myUrl12 = Pattern.compile("^(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + Entities.stopChar + ")");
            Matcher m12 = myUrl12.matcher(description);
            while (m12.find()) {
                urls.add(Url.getUrlFactory(m12.group(1)));
            }
            Pattern myUrl13 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")$");
            Matcher m13 = myUrl13.matcher(description);
            while (m13.find()) {
                urls.add(Url.getUrlFactory(m13.group(2)));
            }
        }
        return urls;
    }

    @Override
    public Set<Url> getUrlsFor(TwitterProfile userSource) {
        Set<Url> urlsTarget = new LinkedHashSet<Url>();
        Map<String, Object> extraData = userSource.getExtraData();
        if(extraData.containsKey("status")){
            Object o = extraData.get("status");
            if(o != null && o instanceof Map) {
                Object oo = ((Map) o).get("entities");
                if(oo != null && oo instanceof Map) {
                    Object ooo = ((Map) oo).get("urls");
                    if(ooo != null && ooo instanceof List) {
                        for (Object o4 : (List) ooo) {
                            if(o4 != null && o4 instanceof Map) {
                                String url = ((Map<String, String>) o4).get("url");
                                String expandedUrl = ((Map<String, String>) o4).get("expanded_url");
                                String displayUrl = ((Map<String, String>) o4).get("display_url");
                                List<Integer> indicesSource = (List<Integer>) ((Map<String, Object>) o4).get("indices");
                                Url urlTarget = new Url(displayUrl, expandedUrl, url, indicesSource);
                                urlsTarget.add(urlTarget);
                            }
                        }
                    }
                }
            }
        }
        if(extraData.containsKey("entities")){
            Object o = extraData.get("entities");
            if(o != null && o instanceof Map) {
                Object oo = ((Map) o).get("url");
                if(oo != null && oo instanceof Map) {
                    Object ooo = ((Map) oo).get("urls");
                    if(ooo != null && ooo instanceof List) {
                        for (Object o4 : (List) ooo) {
                            if(o4 != null && o4 instanceof Map){
                                String url = ((Map<String, String>) o4).get("url");
                                String expandedUrl = ((Map<String, String>) o4).get("expanded_url");
                                String displayUrl = ((Map<String, String>) o4).get("display_url");
                                List<Integer> indicesSource = (List<Integer>) ((Map<String, Object>) o4).get("indices");
                                Url urlTarget = new Url(displayUrl, expandedUrl, url, indicesSource);
                                urlsTarget.add(urlTarget);
                            }
                        }
                    }
                }
            }
        }
        String description = userSource.getDescription();
        Set<Url> rawUrlsFromDescription = getUrlsForDescription(description);
        urlsTarget.addAll(rawUrlsFromDescription);
        return urlsTarget;
    }


    private static final Logger log = LoggerFactory.getLogger(UrlTransformServiceImpl.class);

    public UrlTransformServiceImpl() {

    }

}
