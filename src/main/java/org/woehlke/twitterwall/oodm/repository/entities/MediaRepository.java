package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Media;

/**
 * Created by tw on 12.06.17.
 */
public interface MediaRepository {

    Media persist(Media myMediaEntity);

    Media update(Media myMediaEntity);

    Media findByIdTwitter(long idTwitter);

    Media findByFields(String mediaHttp,String mediaHttps,String url,String display,String expanded, String type);
}
