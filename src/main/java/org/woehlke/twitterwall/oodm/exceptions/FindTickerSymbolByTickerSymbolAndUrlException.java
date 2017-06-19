package org.woehlke.twitterwall.oodm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="TickerSymbol Not Found") //404
public class FindTickerSymbolByTickerSymbolAndUrlException extends RuntimeException {

    private static final long serialVersionUID = -3332292346834265371L;

    public FindTickerSymbolByTickerSymbolAndUrlException(NoResultException e, String tickerSymbol, String url){
        super("TickerSymbol not found for tickerSymbol="+tickerSymbol+",url="+url,e);
    }
}
