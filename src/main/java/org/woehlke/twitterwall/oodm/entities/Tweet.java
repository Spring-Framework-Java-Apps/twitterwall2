package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithEntities;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.parts.Entities;
import org.woehlke.twitterwall.oodm.entities.listener.TweetListener;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "tweet",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_tweet",columnNames = {"id_twitter"})
    },
    indexes = {
        @Index(name="idx_tweet_created_date", columnList="created_date"),
        @Index(name="idx_tweet_from_user", columnList="from_user"),
        @Index(name="idx_tweet_to_user_id", columnList="to_user_id")  ,
        @Index(name="idx_tweet_in_reply_to_status_id", columnList="in_reply_to_status_id"),
        @Index(name="idx_tweet_in_reply_to_user_id", columnList="in_reply_to_user_id"),
        @Index(name="idx_tweet_in_reply_to_screen_name", columnList="in_reply_to_screen_name"),
        @Index(name="idx_tweet_from_user_id", columnList="from_user_id"),
        @Index(name="idx_tweet_id_str", columnList="id_str")
    }
)
@NamedQueries({
    @NamedQuery(
        name="Tweet.getTweetsForHashTag",
        query="select t from Tweet as t join t.entities.hashTags hashTag WHERE hashTag.text=:hashtagText"
    ),
    @NamedQuery(
        name="Tweet.countTweetsForHashTag",
        query="select count(t) from Tweet as t join t.entities.hashTags hashTag WHERE hashTag.text=:hashtagText"
    ),
    @NamedQuery(
        name="Tweet.findAllTwitterIds",
        query="select t.idTwitter from Tweet as t"
    ),
    @NamedQuery(
        name="Tweet.findByUniqueId",
        query="select t from Tweet t where t.idTwitter=:idTwitter"
    )
})
@NamedNativeQueries({
    @NamedNativeQuery(
        name="Tweet.countAllUser2HashTag",
        query="select count(*) as z from tweet_hashtag"
    ),
    @NamedNativeQuery(
        name="Tweet.countAllUser2Media",
        query="select count(*) as z from tweet_media"
    ),
    @NamedNativeQuery(
        name="Tweet.countAllUser2Mention",
        query="select count(*) as z from tweet_mention"
    ),
    @NamedNativeQuery(
        name="Tweet.countAllUser2TickerSymbol",
        query="select count(*) as z from tweet_tickersymbol"
    ),
    @NamedNativeQuery(
        name="Tweet.countAllUser2Url",
        query="select count(*) as z from tweet_url"
    )
})
@EntityListeners(TweetListener.class)
public class Tweet extends AbstractDomainObject<Tweet> implements DomainObjectWithEntities<Tweet>,DomainObjectWithTask<Tweet> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name="id_twitter", nullable = false)
    private Long idTwitter;

    @NotNull
    @Column(name="id_str",nullable = false)
    private String idStr = "";

    @NotEmpty
    @Column(name="text", nullable = false,length=4096)
    private String text = "";

    @Column(name="created_date", nullable = false)
    private Date createdAt;

    @Column(name="from_user")
    private String fromUser;

    @Column(name = "profile_image_url", length=4096)
    private String profileImageUrl;

    @Column(name="to_user_id")
    private Long toUserId;

    @Column(name="in_reply_to_status_id")
    private Long inReplyToStatusId;

    @Column(name="in_reply_to_user_id")
    private Long inReplyToUserId;

    @Column(name="in_reply_to_screen_name")
    private String inReplyToScreenName;

    @Column(name="from_user_id")
    private Long fromUserId;

    @Column(name="language_code")
    private String languageCode;

    @Column(name="source", length=4096)
    private String source;

    @Column(name="retweet_count")
    private Integer retweetCount;

    @Column(name="retweeted")
    private Boolean retweeted;

    @JoinColumn(name="fk_tweet_retweeted")
    @ManyToOne(cascade = {DETACH, REFRESH, REMOVE}, fetch = EAGER, optional = true)
    private Tweet retweetedStatus;

    @Column(name="favorited")
    private Boolean favorited;

    @Column(name="favorite_count")
    private Integer favoriteCount;

    @Valid
    @NotNull
    @Embedded
    @AssociationOverrides({
        @AssociationOverride(
            name = "urls",
            joinTable = @JoinTable(
                name="tweet_url"
            )
        ),
        @AssociationOverride(
            name = "hashTags",
            joinTable = @JoinTable(
                name="tweet_hashtag"
            )
        ),
        @AssociationOverride(
            name = "mentions",
            joinTable = @JoinTable(
                name="tweet_mention"
            )
        ),
        @AssociationOverride(
            name = "media",
            joinTable = @JoinTable(
                name="tweet_media"
            )
        ),
        @AssociationOverride(
            name = "tickerSymbols",
            joinTable = @JoinTable(
                name="tweet_tickersymbol"
            )
        )
    })
    private Entities entities;

    @Valid
    @NotNull
    @JoinColumn(name="fk_user")
    @ManyToOne(cascade = {DETACH, REFRESH, REMOVE}, fetch = EAGER, optional = false)
    private User user;

    @AssertTrue
    @Transient
    @Override
    public boolean isValid() {
        return true;
    }

    @Transient
    @Override
    public String getUniqueId() {
        return idTwitter.toString();
    }

    @Transient
    @Override
    public Map<String, Object> getParametersForFindByUniqueId() {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("idTwitter",this.idTwitter);
        return parameters;
    }
    @Transient
    @Override
    public String getQueryNameForFindByUniqueId() {
        return "Tweet.findByUniqueId";
    }

    public Tweet(Task createdBy, Task updatedBy, long idTwitter, String idStr, String text, Date createdAt) {
        super(createdBy,updatedBy);
        this.idTwitter = idTwitter;
        this.idStr = idStr;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Tweet(Task createdBy, Task updatedBy, long idTwitter, String idStr, String text, Date createdAt, String fromUser, String profileImageUrl, Long toUserId, long fromUserId, String languageCode, String source) {
        super(createdBy,updatedBy);
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

    private Tweet() {
    }

    public void removeAllEntities(){
        this.entities.removeAll();
    }

    @Transient
    public String getFormattedText() {
        return this.entities.getFormattedText(this.text);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getIdTwitter() {
        return idTwitter;
    }

    @Override
    public void setIdTwitter(Long idTwitter) {
        this.idTwitter = idTwitter;
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

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
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

    public Boolean getRetweeted() {
        return retweeted;
    }

    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    public Tweet getRetweetedStatus() {
        return retweetedStatus;
    }

    public void setRetweetedStatus(Tweet retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setIdTwitter(long idTwitter) {
        this.idTwitter = idTwitter;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String toStringRetweetedStatus(){
        if(retweetedStatus==null){
            return " null ";
        } else {
            return retweetedStatus.toString();
        }
    }

    private String toStringUser(){
        if(user==null){
            return " null ";
        } else {
            return user.toString();
        }
    }

    private String toStringEntities(){
        if(user==null){
            return " null ";
        } else {
            return entities.toString();
        }
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", idTwitter=" + idTwitter +
                ", idStr='" + idStr + '\'' +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", fromUser='" + fromUser + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", toUserId=" + toUserId +
                ", inReplyToStatusId=" + inReplyToStatusId +
                ", inReplyToUserId=" + inReplyToUserId +
                ", inReplyToScreenName='" + inReplyToScreenName + '\'' +
                ", fromUserId=" + fromUserId +
                ", languageCode='" + languageCode + '\'' +
                ", source='" + source + '\'' +
                ", retweetCount=" + retweetCount +
                ", retweeted=" + retweeted +
                ", retweetedStatus=" + retweetedStatus +
                ", favorited=" + favorited +
                ", favoriteCount=" + favoriteCount +
                ",\n retweetedStatus=" + toStringRetweetedStatus() +
                    super.toString() +
                ",\n entities=" + toStringEntities() +
                ",\n user=" + toStringUser() +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tweet)) return false;
        if (!super.equals(o)) return false;

        Tweet tweet = (Tweet) o;

        if (getId() != null ? !getId().equals(tweet.getId()) : tweet.getId() != null) return false;
        return getIdTwitter() != null ? getIdTwitter().equals(tweet.getIdTwitter()) : tweet.getIdTwitter() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getIdTwitter() != null ? getIdTwitter().hashCode() : 0);
        return result;
    }
}
