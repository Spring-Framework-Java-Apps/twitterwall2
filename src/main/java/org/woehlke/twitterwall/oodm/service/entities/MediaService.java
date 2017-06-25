package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.social.twitter.api.MediaEntity;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.service.common.OodmService;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
public interface MediaService extends OodmService {

    Media create(Media media);

    Media findByIdTwitter(long idTwitter);

    Media update(Media media);

    Media findByFields(Media media);

    Media store(Media media);

    Set<Media> transformTwitterEntitiesMedia(List<MediaEntity> media);

    //Media storeMedia(Media media);
}
