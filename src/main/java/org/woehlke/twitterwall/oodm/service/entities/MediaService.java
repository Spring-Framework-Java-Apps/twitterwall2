package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Media;

/**
 * Created by tw on 12.06.17.
 */
public interface MediaService {

    Media store(Media media);

    Media findByIdTwitter(long idTwitter);

    Media update(Media media);
}
