package org.woehlke.twitterwall.oodm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="HashTag Not Found") //404
public class FindHashTagByTextException extends RuntimeException {

    private static final long serialVersionUID = -3332292346834265371L;

    public FindHashTagByTextException(NoResultException e, String text){
        super("HashTag not found for text="+text,e);
    }
}
