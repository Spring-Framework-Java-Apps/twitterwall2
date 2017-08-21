package org.woehlke.twitterwall.oodm.model.parts;

import org.springframework.validation.annotation.Validated;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithValidation;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by tw on 03.07.17.
 */
@Validated
@Embeddable
public class CountedEntities implements Serializable,DomainObjectWithValidation {

    @NotNull
    private Long countUser=0L;

    @NotNull
    private Long countTweets=0L;

    @NotNull
    private Long countUserLists=0L;

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

    @NotNull
    private Long userList2Members=0L;

    @NotNull
    private Long userList2Subcriber=0L;

    public CountedEntities() {
    }

    @Transient
    public Long getTotalNumberOfRows(){
        return countUser +
        countTweets +
        countUserLists +
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
        userprofile2url +
        userList2Members +
        userList2Subcriber;
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

    public Long getCountUserLists() {
        return countUserLists;
    }

    public void setCountUserLists(Long countUserLists) {
        this.countUserLists = countUserLists;
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

    public Long getUserList2Members() {
        return userList2Members;
    }

    public void setUserList2Members(Long userList2Members) {
        this.userList2Members = userList2Members;
    }

    public Long getUserList2Subcriber() {
        return userList2Subcriber;
    }

    public void setUserList2Subcriber(Long userList2Subcriber) {
        this.userList2Subcriber = userList2Subcriber;
    }

    @Override
    public String toString() {
        return "CountedEntities{" +
            "countUser=" + countUser +
            ", countTweets=" + countTweets +
            ", countUserLists=" + countUserLists +
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
            ", countUserList2Members=" + userList2Members +
            ", countUserList2Subcriber=" + userList2Subcriber +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountedEntities)) return false;

        CountedEntities that = (CountedEntities) o;

        if (getCountUser() != null ? !getCountUser().equals(that.getCountUser()) : that.getCountUser() != null)
            return false;
        if (getCountTweets() != null ? !getCountTweets().equals(that.getCountTweets()) : that.getCountTweets() != null)
            return false;
        if (getCountUserLists() != null ? !getCountUserLists().equals(that.getCountUserLists()) : that.getCountUserLists() != null)
            return false;
        if (getCountHashTags() != null ? !getCountHashTags().equals(that.getCountHashTags()) : that.getCountHashTags() != null)
            return false;
        if (getCountMedia() != null ? !getCountMedia().equals(that.getCountMedia()) : that.getCountMedia() != null)
            return false;
        if (getCountMention() != null ? !getCountMention().equals(that.getCountMention()) : that.getCountMention() != null)
            return false;
        if (getCountTickerSymbol() != null ? !getCountTickerSymbol().equals(that.getCountTickerSymbol()) : that.getCountTickerSymbol() != null)
            return false;
        if (getCountUrl() != null ? !getCountUrl().equals(that.getCountUrl()) : that.getCountUrl() != null)
            return false;
        if (getCountTask() != null ? !getCountTask().equals(that.getCountTask()) : that.getCountTask() != null)
            return false;
        if (getCountTaskHistory() != null ? !getCountTaskHistory().equals(that.getCountTaskHistory()) : that.getCountTaskHistory() != null)
            return false;
        if (getTweet2hashtag() != null ? !getTweet2hashtag().equals(that.getTweet2hashtag()) : that.getTweet2hashtag() != null)
            return false;
        if (getTweet2media() != null ? !getTweet2media().equals(that.getTweet2media()) : that.getTweet2media() != null)
            return false;
        if (getTweet2mention() != null ? !getTweet2mention().equals(that.getTweet2mention()) : that.getTweet2mention() != null)
            return false;
        if (getTweet2tickersymbol() != null ? !getTweet2tickersymbol().equals(that.getTweet2tickersymbol()) : that.getTweet2tickersymbol() != null)
            return false;
        if (getTweet2url() != null ? !getTweet2url().equals(that.getTweet2url()) : that.getTweet2url() != null)
            return false;
        if (getUserprofile2hashtag() != null ? !getUserprofile2hashtag().equals(that.getUserprofile2hashtag()) : that.getUserprofile2hashtag() != null)
            return false;
        if (getUserprofile2media() != null ? !getUserprofile2media().equals(that.getUserprofile2media()) : that.getUserprofile2media() != null)
            return false;
        if (getUserprofile2mention() != null ? !getUserprofile2mention().equals(that.getUserprofile2mention()) : that.getUserprofile2mention() != null)
            return false;
        if (getUserprofile2tickersymbol() != null ? !getUserprofile2tickersymbol().equals(that.getUserprofile2tickersymbol()) : that.getUserprofile2tickersymbol() != null)
            return false;
        if (getUserprofile2url() != null ? !getUserprofile2url().equals(that.getUserprofile2url()) : that.getUserprofile2url() != null)
            return false;
        if (getUserList2Members() != null ? !getUserList2Members().equals(that.getUserList2Members()) : that.getUserList2Members() != null)
            return false;
        return getUserList2Subcriber() != null ? getUserList2Subcriber().equals(that.getUserList2Subcriber()) : that.getUserList2Subcriber() == null;
    }

    @Override
    public int hashCode() {
        int result = getCountUser() != null ? getCountUser().hashCode() : 0;
        result = 31 * result + (getCountTweets() != null ? getCountTweets().hashCode() : 0);
        result = 31 * result + (getCountUserLists() != null ? getCountUserLists().hashCode() : 0);
        result = 31 * result + (getCountHashTags() != null ? getCountHashTags().hashCode() : 0);
        result = 31 * result + (getCountMedia() != null ? getCountMedia().hashCode() : 0);
        result = 31 * result + (getCountMention() != null ? getCountMention().hashCode() : 0);
        result = 31 * result + (getCountTickerSymbol() != null ? getCountTickerSymbol().hashCode() : 0);
        result = 31 * result + (getCountUrl() != null ? getCountUrl().hashCode() : 0);
        result = 31 * result + (getCountTask() != null ? getCountTask().hashCode() : 0);
        result = 31 * result + (getCountTaskHistory() != null ? getCountTaskHistory().hashCode() : 0);
        result = 31 * result + (getTweet2hashtag() != null ? getTweet2hashtag().hashCode() : 0);
        result = 31 * result + (getTweet2media() != null ? getTweet2media().hashCode() : 0);
        result = 31 * result + (getTweet2mention() != null ? getTweet2mention().hashCode() : 0);
        result = 31 * result + (getTweet2tickersymbol() != null ? getTweet2tickersymbol().hashCode() : 0);
        result = 31 * result + (getTweet2url() != null ? getTweet2url().hashCode() : 0);
        result = 31 * result + (getUserprofile2hashtag() != null ? getUserprofile2hashtag().hashCode() : 0);
        result = 31 * result + (getUserprofile2media() != null ? getUserprofile2media().hashCode() : 0);
        result = 31 * result + (getUserprofile2mention() != null ? getUserprofile2mention().hashCode() : 0);
        result = 31 * result + (getUserprofile2tickersymbol() != null ? getUserprofile2tickersymbol().hashCode() : 0);
        result = 31 * result + (getUserprofile2url() != null ? getUserprofile2url().hashCode() : 0);
        result = 31 * result + (getUserList2Members() != null ? getUserList2Members().hashCode() : 0);
        result = 31 * result + (getUserList2Subcriber() != null ? getUserList2Subcriber().hashCode() : 0);
        return result;
    }

    private static final long serialVersionUID = 1L;

    @AssertTrue
    @Transient
    @Override
    public boolean isValid() {
        if(countUser == null){
            return false;
        }
        if(countTweets == null){
            return false;
        }
        if(countUserLists == null){
            return false;
        }
        if(countHashTags == null){
            return false;
        }
        if(countMedia == null){
            return false;
        }
        if(countMention == null){
            return false;
        }
        if(countTickerSymbol == null){
            return false;
        }
        if(countTaskHistory == null){
            return false;
        }
        if(countTask == null){
            return false;
        }
        if(tweet2hashtag == null){
            return false;
        }
        if(tweet2media == null){
            return false;
        }
        if(tweet2mention == null){
            return false;
        }
        if(tweet2tickersymbol == null){
            return false;
        }
        if(tweet2url == null){
            return false;
        }
        if(userprofile2hashtag == null){
            return false;
        }
        if(userprofile2media == null){
            return false;
        }
        if(userprofile2mention == null){
            return false;
        }
        if(userprofile2tickersymbol == null){
            return false;
        }
        if(userprofile2url == null){
            return false;
        }
        if(userList2Members == null){
            return false;
        }
        if(userList2Subcriber == null){
            return false;
        }
        if(countUser < 0){
            return false;
        }
        if(countTweets < 0){
            return false;
        }
        if(countHashTags < 0){
            return false;
        }
        if(countMedia < 0){
            return false;
        }
        if(countMention < 0){
            return false;
        }
        if(countTickerSymbol < 0){
            return false;
        }
        if(countTaskHistory < 0){
            return false;
        }
        if(countTask < 0){
            return false;
        }
        if(tweet2hashtag < 0){
            return false;
        }
        if(tweet2media  < 0){
            return false;
        }
        if(tweet2mention < 0){
            return false;
        }
        if(tweet2tickersymbol < 0){
            return false;
        }
        if(tweet2url < 0){
            return false;
        }
        if(userprofile2hashtag < 0){
            return false;
        }
        if(userprofile2media < 0){
            return false;
        }
        if(userprofile2mention < 0){
            return false;
        }
        if(userprofile2tickersymbol < 0){
            return false;
        }
        if(userprofile2url < 0){
            return false;
        }
        if(userList2Members < 0){
            return false;
        }
        if(userList2Subcriber < 0){
            return false;
        }
        return true;
    }
}
