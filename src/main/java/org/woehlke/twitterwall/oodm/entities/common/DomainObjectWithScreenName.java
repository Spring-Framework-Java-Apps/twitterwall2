package org.woehlke.twitterwall.oodm.entities.common;

import org.woehlke.twitterwall.oodm.entities.parts.ScreenName;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainObjectWithScreenName<T extends DomainObjectWithScreenName> extends DomainObject<T>{

    ScreenName getScreenName();

    void setScreenName(ScreenName screenName);
}
