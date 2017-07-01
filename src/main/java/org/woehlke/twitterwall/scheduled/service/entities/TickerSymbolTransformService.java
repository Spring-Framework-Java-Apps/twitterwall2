package org.woehlke.twitterwall.scheduled.service.entities;

import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.scheduled.common.TransformService;

/**
 * Created by tw on 28.06.17.
 */
public interface TickerSymbolTransformService extends TransformService<TickerSymbol,TickerSymbolEntity> {
    
}
