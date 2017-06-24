package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Media Not Found") //404
public class FindMediaByIdTwitterException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = "Mention not found for idTwitter=";

    public FindMediaByIdTwitterException(NoResultException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMediaByIdTwitterException(LockTimeoutException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMediaByIdTwitterException(NonUniqueResultException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMediaByIdTwitterException(QueryTimeoutException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMediaByIdTwitterException(PersistenceException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMediaByIdTwitterException(RuntimeException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMediaByIdTwitterException(Exception e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }
}
