package org.woehlke.twitterwall.oodm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitter;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithScreenName;

/**
 * Created by tw on 11.06.17.
 */
public interface UserRepository extends DomainRepositoryWithIdTwitter<User>,DomainRepositoryWithScreenName<User> {

    Page<User> getTweetingUsers(Pageable pageRequest);

    Page<User> getNotYetFriendUsers(Pageable pageRequest);

    Page<User> getNotYetOnList(Pageable pageRequest);

    Page<User> getOnList(Pageable pageRequest);

    Page<String> getAllDescriptions(Pageable pageRequest);

    Page<Long> getAllTwitterIds(Pageable pageRequest);

    Page<User> getUsersForHashTag(String hashtagText, Pageable pageRequest);

    long countUsersForHashTag(String hashtagText);

}
