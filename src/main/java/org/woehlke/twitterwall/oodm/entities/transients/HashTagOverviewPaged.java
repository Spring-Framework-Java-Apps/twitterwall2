package org.woehlke.twitterwall.oodm.entities.transients;

import org.springframework.data.domain.Page;

/**
 * @author Thomas Woehlke <thomas@woehlke.org>
 */
public class HashTagOverviewPaged {

    private Page<HashTagCounted> hashTagsTweets;
    private Page<HashTagCounted> hashTagsUsers;

    public HashTagOverviewPaged(Page<HashTagCounted> hashTagsTweets, Page<HashTagCounted> hashTagsUsers) {
        this.hashTagsTweets = hashTagsTweets;
        this.hashTagsUsers = hashTagsUsers;
    }

    public Page<HashTagCounted> getHashTagsTweets() {
        return hashTagsTweets;
    }

    public void setHashTagsTweets(Page<HashTagCounted> hashTagsTweets) {
        this.hashTagsTweets = hashTagsTweets;
    }

    public Page<HashTagCounted> getHashTagsUsers() {
        return hashTagsUsers;
    }

    public void setHashTagsUsers(Page<HashTagCounted> hashTagsUsers) {
        this.hashTagsUsers = hashTagsUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTagOverviewPaged)) return false;

        HashTagOverviewPaged that = (HashTagOverviewPaged) o;

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
        return "HashTagOverviewPaged{" +
                "hashTagsTweets=" + hashTagsTweets +
                ", hashTagsUsers=" + hashTagsUsers +
                '}';
    }
}
