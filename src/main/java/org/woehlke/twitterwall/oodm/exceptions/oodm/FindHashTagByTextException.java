package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "HashTag Not Found") //404
public class FindHashTagByTextException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = FindHashTagByTextException.class.getCanonicalName()+" HashTag not found for text=";

    public FindHashTagByTextException(NoResultException e, String text) {
        super(MSG + text + " " + e.getMessage(), e);
    }

    public FindHashTagByTextException(LockTimeoutException e, String text) {
        super(MSG + text + " " + e.getMessage(), e);
    }

    public FindHashTagByTextException(NonUniqueResultException e, String text) {
        super(MSG + text + " " + e.getMessage(), e);
    }

    public FindHashTagByTextException(QueryTimeoutException e, String text) {
        super(MSG + text + " " + e.getMessage(), e);
    }

    protected FindHashTagByTextException(PersistenceException e, String text) {
        super(MSG + text + " " + e.getMessage(), e);
    }

    protected FindHashTagByTextException(RuntimeException e, String text) {
        super(MSG + text + " " + e.getMessage(), e);
    }

    protected FindHashTagByTextException(Exception e, String text) {
        super(MSG + text + " " + e.getMessage(), e);
    }
}
