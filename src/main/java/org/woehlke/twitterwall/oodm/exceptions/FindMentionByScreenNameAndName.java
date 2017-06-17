package org.woehlke.twitterwall.oodm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Mention Not Found") //404
public class FindMentionByScreenNameAndName extends RuntimeException {

    private static final long serialVersionUID = -3332292346834265371L;

    public FindMentionByScreenNameAndName(NoResultException e, String screenName,String name){
        super("Mention not found for screenName="+screenName+",name="+name,e);
    }
}
