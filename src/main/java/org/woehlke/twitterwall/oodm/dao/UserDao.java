package org.woehlke.twitterwall.oodm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDaoWithIdTwitter;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDaoWithScreenName;

/**
 * Created by tw on 11.06.17.
 */
public interface UserDao extends DomainDaoWithIdTwitter<User>,DomainDaoWithScreenName<User> {

    Page<User> getTweetingUsers(Pageable pageRequest);

    Page<User> getNotYetFriendUsers(Pageable pageRequest);

    Page<User> getNotYetOnList(Pageable pageRequest);

    Page<User> getOnList(Pageable pageRequest);

    Page<String> getAllDescriptions(Pageable pageRequest);

    Page<Long> getAllTwitterIds(Pageable pageRequest);

    Page<User> getUsersForHashTag(String hashtagText, Pageable pageRequest);

    long countUsersForHashTag(String hashtagText);

}
