package org.woehlke.twitterwall.process.parts;

import org.woehlke.twitterwall.oodm.entities.entities.Url;

import java.util.List;

/**
 * Created by tw on 21.06.17.
 */
public interface UrlApiService {

    Url fetchUrl(String urlSrc);

    List<Url> getTestData();

}
