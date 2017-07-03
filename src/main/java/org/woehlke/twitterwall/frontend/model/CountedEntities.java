package org.woehlke.twitterwall.frontend.model;

/**
 * Created by tw on 03.07.17.
 */
public class CountedEntities {

    private long countUser;
    private long countTweets;
    private long countHashTags;
    private long countMedia;
    private long countMention;
    private long countTickerSymbol;
    private long countUrl;
    private long countUrlCache;

    public long getCountUser() {
        return countUser;
    }

    public void setCountUser(long countUser) {
        this.countUser = countUser;
    }

    public long getCountTweets() {
        return countTweets;
    }

    public void setCountTweets(long countTweets) {
        this.countTweets = countTweets;
    }

    public long getCountHashTags() {
        return countHashTags;
    }

    public void setCountHashTags(long countHashTags) {
        this.countHashTags = countHashTags;
    }

    public long getCountMedia() {
        return countMedia;
    }

    public void setCountMedia(long countMedia) {
        this.countMedia = countMedia;
    }

    public long getCountMention() {
        return countMention;
    }

    public void setCountMention(long countMention) {
        this.countMention = countMention;
    }

    public long getCountTickerSymbol() {
        return countTickerSymbol;
    }

    public void setCountTickerSymbol(long countTickerSymbol) {
        this.countTickerSymbol = countTickerSymbol;
    }

    public long getCountUrl() {
        return countUrl;
    }

    public void setCountUrl(long countUrl) {
        this.countUrl = countUrl;
    }

    public long getCountUrlCache() {
        return countUrlCache;
    }

    public void setCountUrlCache(long countUrlCache) {
        this.countUrlCache = countUrlCache;
    }

    @Override
    public String toString() {
        return "CountedEntities{" +
            "countUser=" + countUser +
            ", countTweets=" + countTweets +
            ", countHashTags=" + countHashTags +
            ", countMedia=" + countMedia +
            ", countMention=" + countMention +
            ", countTickerSymbol=" + countTickerSymbol +
            ", countUrl=" + countUrl +
            ", countUrlCache=" + countUrlCache +
            '}';
    }
}
