package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.NoResultException;

/**
 * Created by tw on 25.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Mention Not Found") //404
public class FindMentionByIdTwitterAndScreenNameException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = FindMentionByIdTwitterAndScreenNameException.class.getCanonicalName()+ " Mention not found for idTwitter=";

    public FindMentionByIdTwitterAndScreenNameException(NoResultException e, long idTwitter, String screenName) {
        super(MSG + idTwitter + " " + " screenName=" + screenName + " " + e.getMessage(), e);
    }
}
