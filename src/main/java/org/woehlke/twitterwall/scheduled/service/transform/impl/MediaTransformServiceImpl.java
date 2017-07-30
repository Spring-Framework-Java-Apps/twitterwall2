package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.EntitiesFilter;
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.entities.parts.UrlField;
import org.woehlke.twitterwall.scheduled.service.transform.MediaTransformService;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class MediaTransformServiceImpl extends EntitiesFilter implements MediaTransformService {

    @Override
    public Media transform(MediaEntity medium,Task task) {
        long idTwitter = medium.getId();
        String mediaHttp = medium.getMediaUrl();
        String mediaHttps = medium.getMediaSecureUrl();
        String url = medium.getUrl();
        String display = medium.getDisplayUrl();
        String expanded = medium.getExpandedUrl();
        String type = medium.getType();
        UrlField urlField = new UrlField(url);
        Media myMediaEntity = new Media(task, null, idTwitter, mediaHttp, mediaHttps, urlField, display, expanded, type);
        return myMediaEntity;
    }

    @Override
    public Set<Media> getMediaFor(TwitterProfile userSource,Task task) {
        return new LinkedHashSet<Media>();
    }
}
