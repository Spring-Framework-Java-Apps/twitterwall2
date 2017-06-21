package org.woehlke.twitterwall.process;

import org.woehlke.twitterwall.oodm.entities.entities.Url;

import java.util.List;

/**
 * Created by tw on 21.06.17.
 */
public interface UrlHelper {

    Url fetchUrl(String urlSrc);

    List<Url> getTestData();

}
