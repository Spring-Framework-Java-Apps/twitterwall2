package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Url;

/**
 * Created by tw on 09.07.17.
 */
public interface CreatePersistentUrl {

    Url createPersistentUrlFor(String url, Task task);
}
