package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User Not Found") //404
public class FindUserByScreenNameException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = "User not found for screenName=";

    public FindUserByScreenNameException(NoResultException e, String screenName) {
        super(MSG + screenName + " " + e.getMessage(), e);
    }

    public FindUserByScreenNameException(LockTimeoutException e, String screenName) {
        super(MSG + screenName + " " + e.getMessage(), e);
    }

    public FindUserByScreenNameException(NonUniqueResultException e, String screenName) {
        super(MSG + screenName + " " + e.getMessage(), e);
    }

    public FindUserByScreenNameException(QueryTimeoutException e, String screenName) {
        super(MSG + screenName + " " + e.getMessage(), e);
    }

    public FindUserByScreenNameException(PersistenceException e, String screenName) {
        super(MSG + screenName + " " + e.getMessage(), e);
    }

    public FindUserByScreenNameException(RuntimeException e, String screenName) {
        super(MSG + screenName + " " + e.getMessage(), e);
    }

    public FindUserByScreenNameException(Exception e, String screenName) {
        super(MSG + screenName + " " + e.getMessage(), e);
    }
}
