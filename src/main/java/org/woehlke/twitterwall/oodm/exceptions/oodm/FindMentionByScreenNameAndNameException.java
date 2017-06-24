package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Mention Not Found") //404
public class FindMentionByScreenNameAndNameException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = FindMentionByScreenNameAndNameException.class.getCanonicalName()+" Mention not found for screenName=";

    public FindMentionByScreenNameAndNameException(String screenName, String name) {
        super(MSG + screenName + ",name=" + name);
    }

    public FindMentionByScreenNameAndNameException(NoResultException e, String screenName, String name) {
        super(MSG + screenName + ",name=" + name + " " + e.getMessage(), e);
    }

    public FindMentionByScreenNameAndNameException(LockTimeoutException e, String screenName, String name) {
        super(MSG + screenName + ",name=" + name + " " + e.getMessage(), e);
    }

    public FindMentionByScreenNameAndNameException(NonUniqueResultException e, String screenName, String name) {
        super(MSG + screenName + ",name=" + name + " " + e.getMessage(), e);
    }

    public FindMentionByScreenNameAndNameException(QueryTimeoutException e, String screenName, String name) {
        super(MSG + screenName + ",name=" + name + " " + e.getMessage(), e);
    }

    public FindMentionByScreenNameAndNameException(PersistenceException e, String screenName, String name) {
        super(MSG + screenName + ",name=" + name + " " + e.getMessage(), e);
    }

    public FindMentionByScreenNameAndNameException(RuntimeException e, String screenName, String name) {
        super(MSG + screenName + ",name=" + name + " " + e.getMessage(), e);
    }

    public FindMentionByScreenNameAndNameException(Exception e, String screenName, String name) {
        super(MSG + screenName + ",name=" + name + " " + e.getMessage(), e);
    }
}
