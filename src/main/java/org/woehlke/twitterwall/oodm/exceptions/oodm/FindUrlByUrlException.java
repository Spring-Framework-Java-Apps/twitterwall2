package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 21.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Url Not Found") //404
public class FindUrlByUrlException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = "url not found for url=";

    public FindUrlByUrlException(NoResultException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlByUrlException(LockTimeoutException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlByUrlException(NonUniqueResultException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlByUrlException(QueryTimeoutException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlByUrlException(PersistenceException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlByUrlException(RuntimeException e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }

    public FindUrlByUrlException(Exception e, String url) {
        super(MSG + url + " " + e.getMessage(), e);
    }
}
