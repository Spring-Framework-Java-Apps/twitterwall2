package org.woehlke.twitterwall.backend.mq.userlist.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.UserList;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.userlist.endpoint.splitter.FetchUserListsForUsers;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessageBuilder;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.model.parts.User2UserList;
import org.woehlke.twitterwall.oodm.model.parts.UserListType;

import java.util.ArrayList;
import java.util.List;

@Component("mqFetchUserListsForUsers")
public class FetchUserListsForUsersImpl implements FetchUserListsForUsers {

    @Override
    public List<Message<UserListMessage>> splitUserListMessage(Message<UserMessage> incomingUserMessage) {
        List<Message<UserListMessage>> result = new ArrayList<>();
        UserMessage in = incomingUserMessage.getPayload();
        long userIdTwitter = in.getTwitterProfileId();
        User2UserList user2UserList = twitterApiService.getUserListForUser(userIdTwitter);
        for(UserList userList:user2UserList.getOwnLists()){
            UserListType type = UserListType.USERS_OWN_LIST;
            Message<UserListMessage> out = userListMessageBuilder.buildUserListMessageFromUser(incomingUserMessage,userList,userIdTwitter,type);
            result.add(out);
        }
        for(UserList userList:user2UserList.getUserListMemberships()){
            UserListType type = UserListType.USER_IS_MEMBER;
            Message<UserListMessage> out = userListMessageBuilder.buildUserListMessageFromUser(incomingUserMessage,userList,userIdTwitter,type);
            result.add(out);
        }
        for(UserList userList:user2UserList.getUserListSubcriptions()){
            UserListType type = UserListType.USER_IS_SUBSCRIBER;
            Message<UserListMessage> out = userListMessageBuilder.buildUserListMessageFromUser(incomingUserMessage,userList,userIdTwitter,type);
            result.add(out);
        }
        return result;
    }

    private final TwitterApiService twitterApiService;

    private final UserListMessageBuilder userListMessageBuilder;

    @Autowired
    public FetchUserListsForUsersImpl(TwitterApiService twitterApiService, UserListMessageBuilder userListMessageBuilder) {
        this.twitterApiService = twitterApiService;
        this.userListMessageBuilder = userListMessageBuilder;
    }
}
