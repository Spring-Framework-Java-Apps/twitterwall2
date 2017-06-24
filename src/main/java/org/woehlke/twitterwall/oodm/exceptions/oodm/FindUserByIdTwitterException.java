package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User Not Found") //404
public class FindUserByIdTwitterException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = "User not found for idTwitter=";

    public FindUserByIdTwitterException(NoResultException e, long idTwitter) {
        super(MSG + idTwitter, e);
    }

    public FindUserByIdTwitterException(LockTimeoutException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindUserByIdTwitterException(NonUniqueResultException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindUserByIdTwitterException(QueryTimeoutException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindUserByIdTwitterException(PersistenceException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindUserByIdTwitterException(RuntimeException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindUserByIdTwitterException(Exception e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

}
