package org.woehlke.twitterwall.oodm.entities.application;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by tw on 03.07.17.
 */
@Embeddable
public class CountedEntities implements Serializable {

    private long countUser;
    private long countTweets;
    private long countHashTags;
    private long countMedia;
    private long countMention;
    private long countTickerSymbol;
    private long countUrl;
    private long countUrlCache;

    public CountedEntities() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountedEntities)) return false;

        CountedEntities that = (CountedEntities) o;

        if (countUser != that.countUser) return false;
        if (countTweets != that.countTweets) return false;
        if (countHashTags != that.countHashTags) return false;
        if (countMedia != that.countMedia) return false;
        if (countMention != that.countMention) return false;
        if (countTickerSymbol != that.countTickerSymbol) return false;
        if (countUrl != that.countUrl) return false;
        return countUrlCache == that.countUrlCache;
    }

    @Override
    public int hashCode() {
        int result = (int) (countUser ^ (countUser >>> 32));
        result = 31 * result + (int) (countTweets ^ (countTweets >>> 32));
        result = 31 * result + (int) (countHashTags ^ (countHashTags >>> 32));
        result = 31 * result + (int) (countMedia ^ (countMedia >>> 32));
        result = 31 * result + (int) (countMention ^ (countMention >>> 32));
        result = 31 * result + (int) (countTickerSymbol ^ (countTickerSymbol >>> 32));
        result = 31 * result + (int) (countUrl ^ (countUrl >>> 32));
        result = 31 * result + (int) (countUrlCache ^ (countUrlCache >>> 32));
        return result;
    }
}
