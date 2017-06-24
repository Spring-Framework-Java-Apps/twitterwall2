package org.woehlke.twitterwall.oodm.exceptions.remote;

import org.springframework.http.HttpStatus;
import org.springframework.social.RateLimitExceededException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.exceptions.common.TwitterwallException;

/**
 * Created by tw on 24.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Twitter Api Exception") //404
public class TwitterApiException extends TwitterwallException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = TwitterApiException.class.getCanonicalName()+" Twitter Api Exception = ";

    public TwitterApiException(String urlSrc, RuntimeException ex) {
        super(MSG + urlSrc + " " + ex.getMessage(), ex);
    }

    public TwitterApiException(String urlSrc, Exception ex) {
        super(MSG + urlSrc + " " + ex.getMessage(), ex);
    }

    public TwitterApiException(String urlSrc, ResourceAccessException ex) {
        super(MSG + urlSrc + " " + ex.getMessage(), ex);
    }

    public TwitterApiException(String urlSrc, RateLimitExceededException ex) {
        super(MSG + urlSrc + " " + ex.getMessage(), ex);
    }

}