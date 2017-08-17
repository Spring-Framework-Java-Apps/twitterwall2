package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.UserList;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithIdTwitter;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;

public interface UserListService extends DomainServiceWithIdTwitter<UserList>,DomainServiceWithTask<UserList> {

}
