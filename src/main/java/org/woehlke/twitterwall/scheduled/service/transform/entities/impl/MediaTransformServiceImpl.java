package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.scheduled.service.transform.entities.MediaTransformService;

import java.util.LinkedHashSet;
import java.util.Set;

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
        //TODO: transform getMediaFor
        return media;
    }
}
