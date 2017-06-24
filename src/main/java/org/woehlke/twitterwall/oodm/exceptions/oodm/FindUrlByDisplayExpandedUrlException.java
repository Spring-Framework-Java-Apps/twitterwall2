package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Url Not Found") //404
public class FindUrlByDisplayExpandedUrlException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String msg = FindUrlByDisplayExpandedUrlException.class.getCanonicalName()+ "Url not found for display=";

    public FindUrlByDisplayExpandedUrlException(String display, String expanded, String url) {
        super(msg + display + ",expanded=" + expanded + ",url=" + url);
    }

    public FindUrlByDisplayExpandedUrlException(NoResultException e, String display, String expanded, String url) {
        super(msg + display + ",expanded=" + expanded + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindUrlByDisplayExpandedUrlException(LockTimeoutException e, String display, String expanded, String url) {
        super(msg + display + ",expanded=" + expanded + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindUrlByDisplayExpandedUrlException(NonUniqueResultException e, String display, String expanded, String url) {
        super(msg + display + ",expanded=" + expanded + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindUrlByDisplayExpandedUrlException(QueryTimeoutException e, String display, String expanded, String url) {
        super(msg + display + ",expanded=" + expanded + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindUrlByDisplayExpandedUrlException(PersistenceException e, String display, String expanded, String url) {
        super(msg + display + ",expanded=" + expanded + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindUrlByDisplayExpandedUrlException(RuntimeException e, String display, String expanded, String url) {
        super(msg + display + ",expanded=" + expanded + ",url=" + url + " " + e.getMessage(), e);
    }

    public FindUrlByDisplayExpandedUrlException(Exception e, String display, String expanded, String url) {
        super(msg + display + ",expanded=" + expanded + ",url=" + url + " " + e.getMessage(), e);
    }
}
