package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Tweet Not Found") //404
public class FindTweetByIdTwitterException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = FindTweetByIdTwitterException.class.getCanonicalName()+"Tweet not found for idTwitter=";

    public FindTweetByIdTwitterException(NoResultException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindTweetByIdTwitterException(LockTimeoutException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindTweetByIdTwitterException(NonUniqueResultException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindTweetByIdTwitterException(QueryTimeoutException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindTweetByIdTwitterException(PersistenceException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindTweetByIdTwitterException(RuntimeException e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }

    public FindTweetByIdTwitterException(Exception e, long idTwitter) {
        super(MSG + idTwitter + " " + e.getMessage(), e);
    }
}
