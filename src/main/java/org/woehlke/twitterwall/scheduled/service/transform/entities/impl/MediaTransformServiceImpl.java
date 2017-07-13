package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.scheduled.service.transform.entities.MediaTransformService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public Set<Media> getMediaFor(User user) {
        Set<Media> media =  new LinkedHashSet<Media>();
        String description = user.getDescription();
        if (description != null) {
            Pattern urlPattern = Pattern.compile("("+ Url.URL_PATTTERN_FOR_USER+")(" + Entities.stopChar + ")");
            Matcher m3 = urlPattern.matcher(description);
            while (m3.find()) {
                media.add(Media.getMediaFactory(m3.group(1)));
            }
            Pattern urlPattern2 = Pattern.compile("("+Url.URL_PATTTERN_FOR_USER+")$");
            Matcher m4 = urlPattern2.matcher(description);
            while (m4.find()) {
                media.add(Media.getMediaFactory(m4.group(1)));
            }
        }

        return media;
    }
}
