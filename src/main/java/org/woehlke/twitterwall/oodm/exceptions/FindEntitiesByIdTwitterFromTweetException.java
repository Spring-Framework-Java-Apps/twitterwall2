package org.woehlke.twitterwall.oodm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Entities Not Found") //404
public class FindEntitiesByIdTwitterFromTweetException extends RuntimeException {

    private static final long serialVersionUID = -3332292346834265371L;

    public FindEntitiesByIdTwitterFromTweetException(NoResultException e, long idTwitterFromTweet){
        super("Entities not found for idTwitterFromTweet="+idTwitterFromTweet,e);
    }

}
