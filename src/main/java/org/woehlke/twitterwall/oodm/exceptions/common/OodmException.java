package org.woehlke.twitterwall.oodm.exceptions.common;

import javax.persistence.*;

/**
 * Created by tw on 24.06.17.
 */
public class OodmException extends TwitterwallException {

    public OodmException(String urlSrc){ super(urlSrc); }

    protected OodmException(String urlSrc, NoResultException ex) {
        super(urlSrc, ex);
    }

    protected OodmException(String urlSrc, LockTimeoutException ex) {
        super(urlSrc, ex);
    }

    protected OodmException(String urlSrc, NonUniqueResultException ex) {
        super(urlSrc, ex);
    }

    protected OodmException(String urlSrc, QueryTimeoutException ex) {
        super(urlSrc, ex);
    }

    protected OodmException(String urlSrc, PersistenceException ex) {
        super(urlSrc, ex);
    }

    protected OodmException(String urlSrc, RuntimeException ex) {
        super(urlSrc, ex);
    }

    protected OodmException(String urlSrc, Exception ex) {
        super(urlSrc, ex);
    }
}
