package org.woehlke.twitterwall.oodm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

/**
 * Created by tw on 21.06.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Url Not Found") //404
public class FindUrlByUrlException extends RuntimeException {

    private static final long serialVersionUID = -3332292346834265371L;

    public FindUrlByUrlException(NoResultException e, String url) {
        super("url not found for url="+url, e);
    }
}
