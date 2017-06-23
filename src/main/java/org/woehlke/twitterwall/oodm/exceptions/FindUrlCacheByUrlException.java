package org.woehlke.twitterwall.oodm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

/**
 * Created by tw on 23.06.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="UrlCache Not Found") //404
public class FindUrlCacheByUrlException extends RuntimeException {

    private static final long serialVersionUID = -3332292346834265371L;

    public FindUrlCacheByUrlException(NoResultException e, String url) {
        super("urlCache not found for url="+url, e);
    }
}

