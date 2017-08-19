package org.woehlke.twitterwall.backend.service.transform.impl;

import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.UserList;
import org.woehlke.twitterwall.backend.service.transform.UserListTransformService;

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
        UserList userList = new UserList(createdBy, updatedBy, idTwitter, name, fullName, uriPath, description, slug, isPublic, isFollowing, memberCount, subscriberCount);
        return userList;
    }
}
