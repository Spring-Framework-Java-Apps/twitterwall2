package org.woehlke.twitterwall.oodm.entities.transients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 12.07.17.
 */
public class HashTagOverview implements Serializable {

    private List<HashTagCounted> hashTagsTweets = new ArrayList<>();
    private List<HashTagCounted> hashTagsUsers = new ArrayList<>();

    public HashTagOverview(List<HashTagCounted> hashTagsTweets, List<HashTagCounted> hashTagsUsers) {
        this.hashTagsTweets = hashTagsTweets;
        this.hashTagsUsers = hashTagsUsers;
    }

    public HashTagOverview() {
    }

    public List<HashTagCounted> getHashTagsTweets() {
        return hashTagsTweets;
    }

    public void setHashTagsTweets(List<HashTagCounted> hashTagsTweets) {
        this.hashTagsTweets = hashTagsTweets;
    }

    public List<HashTagCounted> getHashTagsUsers() {
        return hashTagsUsers;
    }

    public void setHashTagsUsers(List<HashTagCounted> hashTagsUsers) {
        this.hashTagsUsers = hashTagsUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTagOverview)) return false;

        HashTagOverview that = (HashTagOverview) o;

        if (hashTagsTweets != null ? !hashTagsTweets.equals(that.hashTagsTweets) : that.hashTagsTweets != null)
            return false;
        return hashTagsUsers != null ? hashTagsUsers.equals(that.hashTagsUsers) : that.hashTagsUsers == null;
    }

    @Override
    public int hashCode() {
        int result = hashTagsTweets != null ? hashTagsTweets.hashCode() : 0;
        result = 31 * result + (hashTagsUsers != null ? hashTagsUsers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HashTagOverview{" +
                "hashTagsTweets=" + hashTagsTweets +
                ", hashTagsUsers=" + hashTagsUsers +
                '}';
    }
}
