package org.woehlke.twitterwall.scheduled.service.backend;

import org.woehlke.twitterwall.oodm.entities.Url;

/**
 * Created by tw on 28.06.17.
 */
public interface TwitterUrlService {

    Url fetchTransientUrl(String urlSrc);
}
