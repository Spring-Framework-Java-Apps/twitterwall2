package org.woehlke.twitterwall.oodm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Tweet Not Found") //404
public class FindTweetByIdTwitterException extends RuntimeException  {

    private static final long serialVersionUID = -3332292346834265371L;

    public FindTweetByIdTwitterException(NoResultException e, long idTwitter){
        super("Tweet not found for idTwitter="+idTwitter,e);
    }
}
