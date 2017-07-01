package org.woehlke.twitterwall.exceptions.remote;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.exceptions.common.TwitterwallException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by tw on 21.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Fetch Url Exception") //404
public class FetchUrlException extends TwitterwallException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = FetchUrlException.class.getCanonicalName() +" for urlSrc = ";

    public FetchUrlException(String urlSrc, IOException ex) {
        super(MSG + urlSrc + " " + ex.getMessage(), ex);
    }

    public FetchUrlException(String urlSrc, URISyntaxException ex) {
        super(MSG + urlSrc + " " + ex.getMessage(), ex);
    }

    public FetchUrlException(String urlSrc, NullPointerException ex) {
        super(MSG + urlSrc + " " + ex.getMessage(), ex);
    }

    public FetchUrlException(String urlSrc, RuntimeException ex) {
        super(MSG + urlSrc + " " + ex.getMessage(), ex);
    }

    public FetchUrlException(String urlSrc, Exception ex) {
        super(MSG + urlSrc + " " + ex.getMessage(), ex);
    }

    public FetchUrlException(String urlSrc) {
        super(MSG + urlSrc);
    }
}
