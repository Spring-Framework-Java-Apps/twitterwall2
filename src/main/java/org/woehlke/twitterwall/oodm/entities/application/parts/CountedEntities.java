package org.woehlke.twitterwall.oodm.entities.application.parts;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by tw on 03.07.17.
 */
@Embeddable
public class CountedEntities implements Serializable {

    private Long countUser;
    private Long countTweets;
    private Long countHashTags;
    private Long countMedia;
    private Long countMention;
    private Long countTickerSymbol;
    private Long countUrl;
    private Long countUrlCache;
    private Long countTask;
    private Long countTaskHistory;

    public CountedEntities() {
    }

    public Long getCountUser() {
        return countUser;
    }

    public void setCountUser(Long countUser) {
        this.countUser = countUser;
    }

    public Long getCountTweets() {
        return countTweets;
    }

    public void setCountTweets(Long countTweets) {
        this.countTweets = countTweets;
    }

    public Long getCountHashTags() {
        return countHashTags;
    }

    public void setCountHashTags(Long countHashTags) {
        this.countHashTags = countHashTags;
    }

    public Long getCountMedia() {
        return countMedia;
    }

    public void setCountMedia(Long countMedia) {
        this.countMedia = countMedia;
    }

    public Long getCountMention() {
        return countMention;
    }

    public void setCountMention(Long countMention) {
        this.countMention = countMention;
    }

    public Long getCountTickerSymbol() {
        return countTickerSymbol;
    }

    public void setCountTickerSymbol(Long countTickerSymbol) {
        this.countTickerSymbol = countTickerSymbol;
    }

    public Long getCountUrl() {
        return countUrl;
    }

    public void setCountUrl(Long countUrl) {
        this.countUrl = countUrl;
    }

    public Long getCountUrlCache() {
        return countUrlCache;
    }

    public void setCountUrlCache(Long countUrlCache) {
        this.countUrlCache = countUrlCache;
    }

    public Long getCountTask() {
        return countTask;
    }

    public void setCountTask(Long countTask) {
        this.countTask = countTask;
    }

    public Long getCountTaskHistory() {
        return countTaskHistory;
    }

    public void setCountTaskHistory(Long countTaskHistory) {
        this.countTaskHistory = countTaskHistory;
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
            ", countTask=" + countTask +
            ", countTaskHistory=" + countTaskHistory +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountedEntities)) return false;

        CountedEntities that = (CountedEntities) o;

        if (countUser != null ? !countUser.equals(that.countUser) : that.countUser != null) return false;
        if (countTweets != null ? !countTweets.equals(that.countTweets) : that.countTweets != null) return false;
        if (countHashTags != null ? !countHashTags.equals(that.countHashTags) : that.countHashTags != null)
            return false;
        if (countMedia != null ? !countMedia.equals(that.countMedia) : that.countMedia != null) return false;
        if (countMention != null ? !countMention.equals(that.countMention) : that.countMention != null) return false;
        if (countTickerSymbol != null ? !countTickerSymbol.equals(that.countTickerSymbol) : that.countTickerSymbol != null)
            return false;
        if (countUrl != null ? !countUrl.equals(that.countUrl) : that.countUrl != null) return false;
        if (countUrlCache != null ? !countUrlCache.equals(that.countUrlCache) : that.countUrlCache != null)
            return false;
        if (countTask != null ? !countTask.equals(that.countTask) : that.countTask != null) return false;
        return countTaskHistory != null ? countTaskHistory.equals(that.countTaskHistory) : that.countTaskHistory == null;
    }

    @Override
    public int hashCode() {
        int result = countUser != null ? countUser.hashCode() : 0;
        result = 31 * result + (countTweets != null ? countTweets.hashCode() : 0);
        result = 31 * result + (countHashTags != null ? countHashTags.hashCode() : 0);
        result = 31 * result + (countMedia != null ? countMedia.hashCode() : 0);
        result = 31 * result + (countMention != null ? countMention.hashCode() : 0);
        result = 31 * result + (countTickerSymbol != null ? countTickerSymbol.hashCode() : 0);
        result = 31 * result + (countUrl != null ? countUrl.hashCode() : 0);
        result = 31 * result + (countUrlCache != null ? countUrlCache.hashCode() : 0);
        result = 31 * result + (countTask != null ? countTask.hashCode() : 0);
        result = 31 * result + (countTaskHistory != null ? countTaskHistory.hashCode() : 0);
        return result;
    }
}
