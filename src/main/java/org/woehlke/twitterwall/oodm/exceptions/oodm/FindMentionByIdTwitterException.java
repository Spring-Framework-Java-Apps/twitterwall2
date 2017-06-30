package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Mention Not Found") //404
public class FindMentionByIdTwitterException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = FindMentionByIdTwitterException.class.getCanonicalName()+ " Mention not found for idTwitter=";

    public FindMentionByIdTwitterException(EmptyResultDataAccessException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMentionByIdTwitterException(LockTimeoutException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMentionByIdTwitterException(NonUniqueResultException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMentionByIdTwitterException(QueryTimeoutException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMentionByIdTwitterException(PersistenceException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMentionByIdTwitterException(RuntimeException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindMentionByIdTwitterException(Exception e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }
}
