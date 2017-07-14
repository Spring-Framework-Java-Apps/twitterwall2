package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.scheduled.service.transform.entities.MediaTransformService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.oodm.entities.entities.Url.URL_PATTTERN_FOR_USER_HTTP;
import static org.woehlke.twitterwall.oodm.entities.entities.Url.URL_PATTTERN_FOR_USER_HTTPS;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class MediaTransformServiceImpl implements MediaTransformService {

    @Override
    public Media transform(MediaEntity medium) {
        long idTwitter = medium.getId();
        String mediaHttp = medium.getMediaUrl();
        String mediaHttps = medium.getMediaSecureUrl();
        String url = medium.getUrl();
        String display = medium.getDisplayUrl();
        String expanded = medium.getExpandedUrl();
        String type = medium.getType();
        int[] indices = medium.getIndices();
        Media myMediaEntity = new Media(idTwitter, mediaHttp, mediaHttps, url, display, expanded, type, indices);
        return myMediaEntity;
    }

    private Set<Media> getMediaForDescription(String description) {
        Set<Media> media =  new LinkedHashSet<Media>();
        if (description != null) {

            Pattern myUrl1 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + Entities.stopChar + ")");
            Matcher m1 = myUrl1.matcher(description);
            while (m1.find()) {
                media.add(Media.getMediaFactory(m1.group(2)));
            }
            Pattern myUrl2 = Pattern.compile("^(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + Entities.stopChar + ")");
            Matcher m2 = myUrl2.matcher(description);
            while (m2.find()) {
                media.add(Media.getMediaFactory(m2.group(1)));
            }
            Pattern myUrl3 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")$");
            Matcher m3 = myUrl3.matcher(description);
            while (m3.find()) {
                media.add(Media.getMediaFactory(m3.group(2)));
            }
            Pattern myUrl11 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + Entities.stopChar + ")");
            Matcher m11 = myUrl11.matcher(description);
            while (m11.find()) {
                media.add(Media.getMediaFactory(m11.group(2)));
            }
            Pattern myUrl12 = Pattern.compile("^(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + Entities.stopChar + ")");
            Matcher m12 = myUrl12.matcher(description);
            while (m12.find()) {
                media.add(Media.getMediaFactory(m12.group(1)));
            }
            Pattern myUrl13 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")$");
            Matcher m13 = myUrl13.matcher(description);
            while (m13.find()) {
                media.add(Media.getMediaFactory(m13.group(2)));
            }
        }
        return media;
    }

    @Override
    public Set<Media> getMediaFor(TwitterProfile userSource) {
        Set<Media> mediaTarget = new LinkedHashSet<Media>();
        //TODO: bla
        String description = userSource.getDescription();
        //Set<Media> mediaTarget = getMediaForDescription(description);
        return mediaTarget;
    }
}
