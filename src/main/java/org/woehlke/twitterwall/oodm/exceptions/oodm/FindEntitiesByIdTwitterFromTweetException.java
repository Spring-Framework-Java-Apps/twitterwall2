package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entities Not Found") //404
public class FindEntitiesByIdTwitterFromTweetException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = FindEntitiesByIdTwitterFromTweetException.class.getCanonicalName()+" Entities not found for idTwitterFromTweet=";

    public FindEntitiesByIdTwitterFromTweetException(NoResultException e, long idTwitterFromTweet) {
        super(MSG + idTwitterFromTweet + " " + e.getMessage(), e);
    }

    public FindEntitiesByIdTwitterFromTweetException(LockTimeoutException e, long idTwitterFromTweet) {
        super(MSG + idTwitterFromTweet + " " + e.getMessage(), e);
    }

    public FindEntitiesByIdTwitterFromTweetException(NonUniqueResultException e, long idTwitterFromTweet) {
        super(MSG + idTwitterFromTweet + " " + e.getMessage(), e);
    }

    public FindEntitiesByIdTwitterFromTweetException(QueryTimeoutException e, long idTwitterFromTweet) {
        super(MSG + idTwitterFromTweet + " " + e.getMessage(), e);
    }

    protected FindEntitiesByIdTwitterFromTweetException(PersistenceException e, long idTwitterFromTweet) {
        super(MSG + idTwitterFromTweet + " " + e.getMessage(), e);
    }

    protected FindEntitiesByIdTwitterFromTweetException(RuntimeException e, long idTwitterFromTweet) {
        super(MSG + idTwitterFromTweet + " " + e.getMessage(), e);
    }

    protected FindEntitiesByIdTwitterFromTweetException(Exception e, long idTwitterFromTweet) {
        super(MSG + idTwitterFromTweet + " " + e.getMessage(), e);
    }
}
