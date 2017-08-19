package org.woehlke.twitterwall.scheduled.service.remote;

import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Url;

/**
 * Created by tw on 28.06.17.
 */
public interface TwitterUrlService {

    Url fetchTransientUrl(String urlSrc,Task task);
}
