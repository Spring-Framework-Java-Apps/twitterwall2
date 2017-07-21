package org.woehlke.twitterwall.scheduled.service.transform;

import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.scheduled.service.transform.common.TransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
public interface TickerSymbolTransformService extends TransformService<TickerSymbol,TickerSymbolEntity> {

    //Set<TickerSymbol> getTickerSymbolsFor(User user);

    Set<TickerSymbol> getTickerSymbolsFor(TwitterProfile userSource, Task task);

}
