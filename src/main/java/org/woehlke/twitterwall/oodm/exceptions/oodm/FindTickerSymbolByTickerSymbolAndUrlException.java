package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "TickerSymbol Not Found") //404
public class FindTickerSymbolByTickerSymbolAndUrlException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = "TickerSymbol not found for tickerSymbol=";

    public FindTickerSymbolByTickerSymbolAndUrlException(NoResultException e, String tickerSymbol, String url) {
        super(MSG + tickerSymbol + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindTickerSymbolByTickerSymbolAndUrlException(LockTimeoutException e, String tickerSymbol, String url) {
        super(MSG + tickerSymbol + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindTickerSymbolByTickerSymbolAndUrlException(NonUniqueResultException e, String tickerSymbol, String url) {
        super(MSG + tickerSymbol + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindTickerSymbolByTickerSymbolAndUrlException(QueryTimeoutException e, String tickerSymbol, String url) {
        super(MSG + tickerSymbol + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindTickerSymbolByTickerSymbolAndUrlException(PersistenceException e, String tickerSymbol, String url) {
        super(MSG + tickerSymbol + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindTickerSymbolByTickerSymbolAndUrlException(RuntimeException e, String tickerSymbol, String url) {
        super(MSG + tickerSymbol + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindTickerSymbolByTickerSymbolAndUrlException(Exception e, String tickerSymbol, String url) {
        super(MSG + tickerSymbol + ",url=" + url + " " + e.getMessage(), e);
    }
}
