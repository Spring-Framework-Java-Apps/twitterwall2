package org.woehlke.twitterwall.oodm.entities.parts;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tw on 10.06.17.
 */
public abstract class AbstractTwitterObject<T extends DomainObject> implements DomainObject<T> {

    @Transient
    private Map<String, Object> extraData = new HashMap<>();

    public AbstractTwitterObject() {
    }

    /**
     * @return Any fields in response from Twitter that are otherwise not mapped to any properties.
     */
    public Map<String, Object> getExtraData() {
        return extraData;
    }

    /**
     * {@link JsonAnySetter} hook. Called when an otherwise unmapped property is being processed during JSON deserialization.
     *
     * @param key   The property's key.
     * @param value The property's value.
     */
    protected void add(String key, Object value) {
        extraData.put(key, value);
    }

    public void setExtraData(Map<String, Object> extraData) {
      this.extraData = extraData;
    }

  @Override
    public boolean equals(T o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTwitterObject)) return false;

        AbstractTwitterObject that = (AbstractTwitterObject) o;

        return extraData != null ? extraData.equals(that.extraData) : that.extraData == null;
    }

    @Override
    public int hashCode() {
        return extraData != null ? extraData.hashCode() : 0;
    }

  @Override
  public String toString() {
    StringBuffer myExtraData = new StringBuffer();
    myExtraData.append("[ ");
    for (String extraDatumKey : extraData.keySet()) {
      myExtraData.append(extraDatumKey);
      myExtraData.append(", ");
    }
    myExtraData.append(" ]");
    return  ", extraData=" + myExtraData.toString();
  }

    /**
     * Created by tw on 03.07.17.
     */
    @Embeddable
    public static class CountedEntities implements Serializable {

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

        private Long tweet2hashtag;
        private Long tweet2media;
        private Long tweet2mention;
        private Long tweet2tickersymbol;
        private Long tweet2url;
        private Long userprofile2hashtag;
        private Long userprofile2media;
        private Long userprofile2mention;
        private Long userprofile2tickersymbol;
        private Long userprofile2url;

        public CountedEntities() {
        }

        public Long getTotalNumberOfRows(){
            return countUser +
            countTweets +
            countHashTags +
            countMedia +
            countMention +
            countTickerSymbol +
            countUrl +
            countUrlCache +
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
                ", countUrlCache=" + countUrlCache +
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
            if (!(o instanceof AbstractTwitterObject.CountedEntities)) return false;

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
            result = 31 * result + (countUrlCache != null ? countUrlCache.hashCode() : 0);
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
    }
}
