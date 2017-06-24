package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.service.common.OodmService;

/**
 * Created by tw on 12.06.17.
 */
public interface MediaService extends OodmService {

    Media store(Media media);

    Media findByIdTwitter(long idTwitter);

    Media update(Media media);

    Media findByFields(Media media);
}
