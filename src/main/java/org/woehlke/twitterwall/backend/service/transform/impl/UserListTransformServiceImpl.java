package org.woehlke.twitterwall.backend.service.transform.impl;

import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.UserList;
import org.woehlke.twitterwall.backend.service.transform.UserListTransformService;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class UserListTransformServiceImpl implements UserListTransformService {

    @Override
    public UserList transform(org.springframework.social.twitter.api.UserList twitterObject, Task task) {
        Task createdBy = task;
        Task updatedBy = null;
        long idTwitter = twitterObject.getId();
        String name = twitterObject.getName();
        String fullName = twitterObject.getFullName();
        String uriPath = twitterObject.getUriPath();
        String description = twitterObject.getDescription();
        String slug = twitterObject.getSlug();
        boolean isPublic = twitterObject.isPublic();
        boolean isFollowing = twitterObject.isFollowing();
        int memberCount = twitterObject.getMemberCount();
        int subscriberCount = twitterObject.getSubscriberCount();
        Set<User> members = new LinkedHashSet<User>();
        Set<User> subscriber = new LinkedHashSet<User>();
        User listOwner = null;
        UserList userList = new UserList(createdBy, updatedBy, idTwitter, name, fullName, uriPath, description, slug, isPublic, isFollowing, memberCount, subscriberCount,listOwner,members,subscriber);
        userList.setExtraData(twitterObject.getExtraData());
        return userList;
    }
}
