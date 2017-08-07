package org.woehlke.twitterwall.oodm.entities.parts;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by tw on 03.07.17.
 */
@Embeddable
public class CountedEntities implements Serializable {

    @NotNull
    private Long countUser=0L;

    @NotNull
    private Long countTweets=0L;

    @NotNull
    private Long countHashTags=0L;

    @NotNull
    private Long countMedia=0L;

    @NotNull
    private Long countMention=0L;

    @NotNull
    private Long countTickerSymbol=0L;

    @NotNull
    private Long countUrl=0L;

    @NotNull
    private Long countTask=0L;

    @NotNull
    private Long countTaskHistory=0L;

    @NotNull
    private Long tweet2hashtag=0L;

    @NotNull
    private Long tweet2media=0L;

    @NotNull
    private Long tweet2mention=0L;

    @NotNull
    private Long tweet2tickersymbol=0L;

    @NotNull
    private Long tweet2url=0L;

    @NotNull
    private Long userprofile2hashtag=0L;

    @NotNull
    private Long userprofile2media=0L;

    @NotNull
    private Long userprofile2mention=0L;

    @NotNull
    private Long userprofile2tickersymbol=0L;

    @NotNull
    private Long userprofile2url=0L;

    public CountedEntities() {
    }

    @Transient
    public Long getTotalNumberOfRows(){
        return countUser +
        countTweets +
        countHashTags +
        countMedia +
        countMention +
        countTickerSymbol +
        countUrl +
        countTask +
        countTaskHistory +
        tweet2hashtag +
        tweet2media +
        tweet2mention +
        tweet2tickersymbol +
        tweet2url +
        userprofile2hashtag +
        userprofile2media +
        userprofile2mention +
        userprofile2tickersymbol +
        userprofile2url;
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

    public Long getTweet2hashtag() {
        return tweet2hashtag;
    }

    public void setTweet2hashtag(Long tweet2hashtag) {
        this.tweet2hashtag = tweet2hashtag;
    }

    public Long getTweet2media() {
        return tweet2media;
    }

    public void setTweet2media(Long tweet2media) {
        this.tweet2media = tweet2media;
    }

    public Long getTweet2mention() {
        return tweet2mention;
    }

    public void setTweet2mention(Long tweet2mention) {
        this.tweet2mention = tweet2mention;
    }

    public Long getTweet2tickersymbol() {
        return tweet2tickersymbol;
    }

    public void setTweet2tickersymbol(Long tweet2tickersymbol) {
        this.tweet2tickersymbol = tweet2tickersymbol;
    }

    public Long getTweet2url() {
        return tweet2url;
    }

    public void setTweet2url(Long tweet2url) {
        this.tweet2url = tweet2url;
    }

    public Long getUserprofile2hashtag() {
        return userprofile2hashtag;
    }

    public void setUserprofile2hashtag(Long userprofile2hashtag) {
        this.userprofile2hashtag = userprofile2hashtag;
    }

    public Long getUserprofile2media() {
        return userprofile2media;
    }

    public void setUserprofile2media(Long userprofile2media) {
        this.userprofile2media = userprofile2media;
    }

    public Long getUserprofile2mention() {
        return userprofile2mention;
    }

    public void setUserprofile2mention(Long userprofile2mention) {
        this.userprofile2mention = userprofile2mention;
    }

    public Long getUserprofile2tickersymbol() {
        return userprofile2tickersymbol;
    }

    public void setUserprofile2tickersymbol(Long userprofile2tickersymbol) {
        this.userprofile2tickersymbol = userprofile2tickersymbol;
    }

    public Long getUserprofile2url() {
        return userprofile2url;
    }

    public void setUserprofile2url(Long userprofile2url) {
        this.userprofile2url = userprofile2url;
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
            ", countTask=" + countTask +
            ", countTaskHistory=" + countTaskHistory +
            ", tweet2hashtag=" + tweet2hashtag +
            ", tweet2media=" + tweet2media +
            ", tweet2mention=" + tweet2mention +
            ", tweet2tickersymbol=" + tweet2tickersymbol +
            ", tweet2url=" + tweet2url +
            ", userprofile2hashtag=" + userprofile2hashtag +
            ", userprofile2media=" + userprofile2media +
            ", userprofile2mention=" + userprofile2mention +
            ", userprofile2tickersymbol=" + userprofile2tickersymbol +
            ", userprofile2url=" + userprofile2url +
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
        if (countTask != null ? !countTask.equals(that.countTask) : that.countTask != null) return false;
        if (countTaskHistory != null ? !countTaskHistory.equals(that.countTaskHistory) : that.countTaskHistory != null)
            return false;
        if (tweet2hashtag != null ? !tweet2hashtag.equals(that.tweet2hashtag) : that.tweet2hashtag != null)
            return false;
        if (tweet2media != null ? !tweet2media.equals(that.tweet2media) : that.tweet2media != null) return false;
        if (tweet2mention != null ? !tweet2mention.equals(that.tweet2mention) : that.tweet2mention != null)
            return false;
        if (tweet2tickersymbol != null ? !tweet2tickersymbol.equals(that.tweet2tickersymbol) : that.tweet2tickersymbol != null)
            return false;
        if (tweet2url != null ? !tweet2url.equals(that.tweet2url) : that.tweet2url != null) return false;
        if (userprofile2hashtag != null ? !userprofile2hashtag.equals(that.userprofile2hashtag) : that.userprofile2hashtag != null)
            return false;
        if (userprofile2media != null ? !userprofile2media.equals(that.userprofile2media) : that.userprofile2media != null)
            return false;
        if (userprofile2mention != null ? !userprofile2mention.equals(that.userprofile2mention) : that.userprofile2mention != null)
            return false;
        if (userprofile2tickersymbol != null ? !userprofile2tickersymbol.equals(that.userprofile2tickersymbol) : that.userprofile2tickersymbol != null)
            return false;
        return userprofile2url != null ? userprofile2url.equals(that.userprofile2url) : that.userprofile2url == null;
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
        result = 31 * result + (countTask != null ? countTask.hashCode() : 0);
        result = 31 * result + (countTaskHistory != null ? countTaskHistory.hashCode() : 0);
        result = 31 * result + (tweet2hashtag != null ? tweet2hashtag.hashCode() : 0);
        result = 31 * result + (tweet2media != null ? tweet2media.hashCode() : 0);
        result = 31 * result + (tweet2mention != null ? tweet2mention.hashCode() : 0);
        result = 31 * result + (tweet2tickersymbol != null ? tweet2tickersymbol.hashCode() : 0);
        result = 31 * result + (tweet2url != null ? tweet2url.hashCode() : 0);
        result = 31 * result + (userprofile2hashtag != null ? userprofile2hashtag.hashCode() : 0);
        result = 31 * result + (userprofile2media != null ? userprofile2media.hashCode() : 0);
        result = 31 * result + (userprofile2mention != null ? userprofile2mention.hashCode() : 0);
        result = 31 * result + (userprofile2tickersymbol != null ? userprofile2tickersymbol.hashCode() : 0);
        result = 31 * result + (userprofile2url != null ? userprofile2url.hashCode() : 0);
        return result;
    }

    private static final long serialVersionUID = 1L;
}
