package org.woehlke.twitterwall.oodm.entities.transients;

public enum Object2EntityTable {

    TWEET_HASHTAG,
    TWEET_MEDIA,
    TWEET_MENTION,
    TWEET_TICKERSYMBOL,
    TWEET_URL,
    USERPROFILE_HASHTAG,
    USERPROFILE_MEDIA,
    USERPROFILE_MENTION,
    USERPROFILE_TICKERSYMBOL,
    USERPROFILE_URL;

    public String getTableName() {
        return this.name().toLowerCase();
    }
}
