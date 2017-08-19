package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UrlEntity;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.entities.EntitiesFilter;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.scheduled.service.transform.UrlTransformService;

import java.util.*;

/**
 * Created by tw on 28.06.17.
 */
@Component
public class UrlTransformServiceImpl extends EntitiesFilter implements UrlTransformService {

    @Override
    public Url transform(UrlEntity url, Task task) {
        String display = url.getDisplayUrl();
        String expanded = url.getExpandedUrl();
        String urlStr = url.getUrl();
        Url myUrlEntity = Url.createByTransformation(task, display, expanded, urlStr);
        return myUrlEntity;
    }

    @Override
    public Set<Url> getUrlsForTwitterProfile(TwitterProfile userSource, Task task) {
        Map<String, Object> extraData = userSource.getExtraData();
        String description = userSource.getDescription();
        return getUrlsFromExtraDataAndText(extraData,description,task);
    }

    private Set<Url> getUrlsFromExtraDataAndText(Map<String, Object> extraData, String description, Task task){
        Set<Url> urlsTarget = new LinkedHashSet<>();
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
                                Url urlTarget = new Url(task, null, displayUrl, expandedUrl, url);
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
                                Url urlTarget = new Url(task,null,displayUrl, expandedUrl, url);
                                urlsTarget.add(urlTarget);
                            }
                        }
                    }
                }
            }
        }
        Set<Url> rawUrlsFromDescription = getUrlsForDescription(description,task);
        for(Url rawUrlFromDescription:rawUrlsFromDescription){
            boolean insert = true;
            for(Url urlTarget :urlsTarget){
                if(rawUrlFromDescription.getUrl().compareTo(urlTarget.getUrl())==0){
                    insert = false;
                }
            }
            if(insert){
                urlsTarget.add(rawUrlFromDescription);
            }
        }
        return urlsTarget;
    }

    @Override
    public Set<Url> getUrlsForTweet(Tweet tweetFromTwitterApi, Task task) {
        Map<String, Object> extraData = tweetFromTwitterApi.getExtraData();
        String text = tweetFromTwitterApi.getText();
        return getUrlsFromExtraDataAndText(extraData,text,task);
    }


    private static final Logger log = LoggerFactory.getLogger(UrlTransformServiceImpl.class);

    public UrlTransformServiceImpl() {

    }

}
