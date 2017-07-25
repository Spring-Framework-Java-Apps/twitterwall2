package org.woehlke.twitterwall.oodm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.transients.*;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithIdTwitter;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithScreenName;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;


/**
 * Created by tw on 11.06.17.
 */
public interface UserService extends DomainServiceWithIdTwitter<User>,DomainServiceWithScreenName<User>,DomainServiceWithTask<User> {

    Page<User> getTweetingUsers(Pageable pageRequest);

    Page<User> getNotYetFriendUsers(Pageable pageRequest);

    Page<User> getNotYetOnList(Pageable pageRequest);

    Page<User> getOnList(Pageable pageRequest);

    Page<String> getAllDescriptions(Pageable pageRequest);

    Page<Long> getAllTwitterIds(Pageable pageRequest);

    Page<User> getUsersForHashTag(HashTag hashTag,Pageable pageRequest);

    Page<Object2Entity> findAllUser2HashTag(Pageable pageRequest);

    Page<Object2Entity> findAllUser2Media(Pageable pageRequest);

    Page<Object2Entity> findAllUser2Mentiong(Pageable pageRequest);

    Page<Object2Entity> findAllUser2Url(Pageable pageRequest);

    Page<Object2Entity> findAllUser2TickerSymbol(Pageable pageRequest);
}
