package org.woehlke.twitterwall.oodm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tw on 10.06.17.
 */
@Entity
public class MyTweet extends MyTwitterObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique=true)
    private long idTwitter;

    @Column(nullable = false)
    private String idStr;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Date createdAt;

    @Column
    private String fromUser;

    @Column
    private String profileImageUrl;

    @Column
    private Long toUserId;

    @Column
    private Long inReplyToStatusId;

    @Column
    private Long inReplyToUserId;

    @Column
    private String inReplyToScreenName;

    @Column
    private long fromUserId;

    @Column
    private String languageCode;

    @Column
    private String source;

    @Column
    private Integer retweetCount;

    @Column
    private boolean retweeted;

    @ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER,optional = true)
    private MyTweet retweetedStatus;

    @Column
    private boolean favorited;

    @Column
    private Integer favoriteCount;

    @OneToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER,optional = true)
    private MyEntities entities;

    @ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER,optional = false)
    private MyTwitterProfile user;

    public MyTweet(long idTwitter, String idStr, String text, Date createdAt) {
        this.idTwitter = idTwitter;
        this.idStr = idStr;
        this.text = text;
        this.createdAt = createdAt;
    }

    /**
     * Constructs a Tweet
     *
     * @param idTwitter The tweet's ID
     * @param idStr The tweet's ID as a String
     * @param text The tweet's text
     * @param createdAt Date Tweet was created
     * @param fromUser The username of the author of the tweet.
     * @param profileImageUrl The URL to the profile picture of the tweet's author.
     * @param toUserId The user ID of the user to whom the tweet is targeted.
     * @param fromUserId The user ID of the tweet's author.
     * @param languageCode The language code
     * @param source The source of the tweet.
     */
    public MyTweet(long idTwitter, String idStr, String text, Date createdAt, String fromUser, String profileImageUrl, Long toUserId, long fromUserId, String languageCode, String source) {
        this.idTwitter = idTwitter;
        this.idStr = idStr;
        this.text = text;
        this.createdAt = createdAt;
        this.fromUser = fromUser;
        this.profileImageUrl = profileImageUrl;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.languageCode = languageCode;
        this.source = source;
    }

    private MyTweet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdTwitter() {
        return idTwitter;
    }

    public String getIdStr() {
        return idStr;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public void setInReplyToStatusId(Long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public Long getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(Long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public MyTweet getRetweetedStatus() {
        return retweetedStatus;
    }

    public void setRetweetedStatus(MyTweet retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public MyEntities getEntities() {
        return entities;
    }

    public void setEntities(MyEntities entities) {
        this.entities = entities;
    }

    public MyTwitterProfile getUser() {
        return user;
    }

    public void setUser(MyTwitterProfile user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyTweet)) return false;

        MyTweet myTweet = (MyTweet) o;

        if (idTwitter != myTweet.idTwitter) return false;
        if (fromUserId != myTweet.fromUserId) return false;
        if (retweeted != myTweet.retweeted) return false;
        if (favorited != myTweet.favorited) return false;
        if (id != null ? !id.equals(myTweet.id) : myTweet.id != null) return false;
        if (idStr != null ? !idStr.equals(myTweet.idStr) : myTweet.idStr != null) return false;
        if (text != null ? !text.equals(myTweet.text) : myTweet.text != null) return false;
        if (createdAt != null ? !createdAt.equals(myTweet.createdAt) : myTweet.createdAt != null) return false;
        if (fromUser != null ? !fromUser.equals(myTweet.fromUser) : myTweet.fromUser != null) return false;
        if (profileImageUrl != null ? !profileImageUrl.equals(myTweet.profileImageUrl) : myTweet.profileImageUrl != null)
            return false;
        if (toUserId != null ? !toUserId.equals(myTweet.toUserId) : myTweet.toUserId != null) return false;
        if (inReplyToStatusId != null ? !inReplyToStatusId.equals(myTweet.inReplyToStatusId) : myTweet.inReplyToStatusId != null)
            return false;
        if (inReplyToUserId != null ? !inReplyToUserId.equals(myTweet.inReplyToUserId) : myTweet.inReplyToUserId != null)
            return false;
        if (inReplyToScreenName != null ? !inReplyToScreenName.equals(myTweet.inReplyToScreenName) : myTweet.inReplyToScreenName != null)
            return false;
        if (languageCode != null ? !languageCode.equals(myTweet.languageCode) : myTweet.languageCode != null)
            return false;
        if (source != null ? !source.equals(myTweet.source) : myTweet.source != null) return false;
        if (retweetCount != null ? !retweetCount.equals(myTweet.retweetCount) : myTweet.retweetCount != null)
            return false;
        if (retweetedStatus != null ? !retweetedStatus.equals(myTweet.retweetedStatus) : myTweet.retweetedStatus != null)
            return false;
        if (favoriteCount != null ? !favoriteCount.equals(myTweet.favoriteCount) : myTweet.favoriteCount != null)
            return false;
        if (entities != null ? !entities.equals(myTweet.entities) : myTweet.entities != null) return false;
        return user != null ? user.equals(myTweet.user) : myTweet.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (idTwitter ^ (idTwitter >>> 32));
        result = 31 * result + (idStr != null ? idStr.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (fromUser != null ? fromUser.hashCode() : 0);
        result = 31 * result + (profileImageUrl != null ? profileImageUrl.hashCode() : 0);
        result = 31 * result + (toUserId != null ? toUserId.hashCode() : 0);
        result = 31 * result + (inReplyToStatusId != null ? inReplyToStatusId.hashCode() : 0);
        result = 31 * result + (inReplyToUserId != null ? inReplyToUserId.hashCode() : 0);
        result = 31 * result + (inReplyToScreenName != null ? inReplyToScreenName.hashCode() : 0);
        result = 31 * result + (int) (fromUserId ^ (fromUserId >>> 32));
        result = 31 * result + (languageCode != null ? languageCode.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (retweetCount != null ? retweetCount.hashCode() : 0);
        result = 31 * result + (retweeted ? 1 : 0);
        result = 31 * result + (retweetedStatus != null ? retweetedStatus.hashCode() : 0);
        result = 31 * result + (favorited ? 1 : 0);
        result = 31 * result + (favoriteCount != null ? favoriteCount.hashCode() : 0);
        result = 31 * result + (entities != null ? entities.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
