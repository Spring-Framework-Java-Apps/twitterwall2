package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

import javax.persistence.*;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Media Not Found") //404
public class FindMediaByFieldsExceptionException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = "Media not found for mediaHttp=";

    public FindMediaByFieldsExceptionException(NoResultException e, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType) {
        super(MSG + mediaHttp + ", mediaHttps=" + mediaHttps + ",url=" + url + ",display=" + display + ",expanded=" + expanded + ",mediaType=" + mediaType, e);
    }

    public FindMediaByFieldsExceptionException(LockTimeoutException e, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType) {
        super(MSG + mediaHttp + ", mediaHttps=" + mediaHttps + ",url=" + url + ",display=" + display + ",expanded=" + expanded + ",mediaType=" + mediaType, e);
    }

    public FindMediaByFieldsExceptionException(NonUniqueResultException e, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType) {
        super(MSG + mediaHttp + ", mediaHttps=" + mediaHttps + ",url=" + url + ",display=" + display + ",expanded=" + expanded + ",mediaType=" + mediaType, e);
    }

    public FindMediaByFieldsExceptionException(QueryTimeoutException e, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType) {
        super(MSG + mediaHttp + ", mediaHttps=" + mediaHttps + ",url=" + url + ",display=" + display + ",expanded=" + expanded + ",mediaType=" + mediaType, e);
    }

    public FindMediaByFieldsExceptionException(PersistenceException e, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType) {
        super(MSG + mediaHttp + ", mediaHttps=" + mediaHttps + ",url=" + url + ",display=" + display + ",expanded=" + expanded + ",mediaType=" + mediaType, e);
    }

    public FindMediaByFieldsExceptionException(RuntimeException e, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType) {
        super(MSG + mediaHttp + ", mediaHttps=" + mediaHttps + ",url=" + url + ",display=" + display + ",expanded=" + expanded + ",mediaType=" + mediaType, e);
    }

    public FindMediaByFieldsExceptionException(Exception e, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType) {
        super(MSG + mediaHttp + ", mediaHttps=" + mediaHttps + ",url=" + url + ",display=" + display + ",expanded=" + expanded + ",mediaType=" + mediaType, e);
    }
}
