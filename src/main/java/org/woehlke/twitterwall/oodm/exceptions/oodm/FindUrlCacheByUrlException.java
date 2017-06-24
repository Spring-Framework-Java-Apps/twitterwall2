package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 23.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "UrlCache Not Found") //404
public class FindUrlCacheByUrlException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = "urlCache not found for url=";

    public FindUrlCacheByUrlException(NoResultException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlCacheByUrlException(LockTimeoutException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlCacheByUrlException(NonUniqueResultException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlCacheByUrlException(QueryTimeoutException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlCacheByUrlException(PersistenceException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlCacheByUrlException(RuntimeException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlCacheByUrlException(Exception e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }
}

