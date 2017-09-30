package org.woehlke.twitterwall.oodm.model.parts;


import org.springframework.social.twitter.api.UserList;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class User2UserList implements Serializable {

    private long idTwitterOfListOwningUser;

    private Set<UserList> ownLists = new HashSet<>();

    private Set<UserList> userListSubcriptions = new HashSet<>();

    private Set<UserList> userListMemberships = new HashSet<>();



    public long getIdTwitterOfListOwningUser() {
        return idTwitterOfListOwningUser;
    }

    public void setIdTwitterOfListOwningUser(long idTwitterOfListOwningUser) {
        this.idTwitterOfListOwningUser = idTwitterOfListOwningUser;
    }

    public Set<UserList> getOwnLists() {
        return ownLists;
    }

    public void setOwnLists(Set<UserList> ownLists) {
        this.ownLists = ownLists;
    }

    public Set<UserList> getUserListSubcriptions() {
        return userListSubcriptions;
    }

    public void setUserListSubcriptions(Set<UserList> userListSubcriptions) {
        this.userListSubcriptions = userListSubcriptions;
    }

    public Set<UserList> getUserListMemberships() {
        return userListMemberships;
    }

    public void setUserListMemberships(Set<UserList> userListMemberships) {
        this.userListMemberships = userListMemberships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User2UserList)) return false;

        User2UserList that = (User2UserList) o;

        if (idTwitterOfListOwningUser != that.idTwitterOfListOwningUser) return false;
        if (ownLists != null ? !ownLists.equals(that.ownLists) : that.ownLists != null) return false;
        if (userListSubcriptions != null ? !userListSubcriptions.equals(that.userListSubcriptions) : that.userListSubcriptions != null)
            return false;
        return userListMemberships != null ? userListMemberships.equals(that.userListMemberships) : that.userListMemberships == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTwitterOfListOwningUser ^ (idTwitterOfListOwningUser >>> 32));
        result = 31 * result + (ownLists != null ? ownLists.hashCode() : 0);
        result = 31 * result + (userListSubcriptions != null ? userListSubcriptions.hashCode() : 0);
        result = 31 * result + (userListMemberships != null ? userListMemberships.hashCode() : 0);
        return result;
    }
}
