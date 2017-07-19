package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithEntities;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.parts.Entities;
import org.woehlke.twitterwall.oodm.entities.listener.TweetListener;

import javax.persistence.*;
import java.util.Date;

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
        @Index(name="idx_tweet_in_reply_to_screenName", columnList="in_reply_to_screenName"),
        @Index(name="idx_tweet_from_user_id", columnList="from_user_id")
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
public class Tweet extends AbstractTwitterObject<Tweet> implements DomainObjectWithEntities<Tweet>,DomainObjectWithTask<Tweet> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private TaskInfo taskInfo = new TaskInfo();

    @ManyToOne(cascade = { CascadeType.REFRESH , CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER,optional = false)
    private Task createdBy;

    @ManyToOne(cascade = { CascadeType.REFRESH , CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER,optional = true)
    private Task updatedBy;

    @Column(name="id_twitter", nullable = false)
    private long idTwitter;

    @Column(nullable = false)
    private String idStr;

    @Column(nullable = false,length=4096)
    private String text;

    @Column(name="created_date", nullable = false)
    private Date createdAt;

    @Column(name="from_user")
    private String fromUser;

    @Column(length=4096)
    private String profileImageUrl;

    @Column(name="to_user_id")
    private Long toUserId;

    @Column(name="in_reply_to_status_id")
    private Long inReplyToStatusId;

    @Column(name="in_reply_to_user_id")
    private Long inReplyToUserId;

    @Column(name="in_reply_to_screenName")
    private String inReplyToScreenName;

    @Column(name="from_user_id")
    private long fromUserId;

    @Column
    private String languageCode;

    @Column(length=4096)
    private String source;

    @Column
    private Integer retweetCount;

    @Column
    private boolean retweeted;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER, optional = true)
    private Tweet retweetedStatus;

    @Column
    private boolean favorited;

    @Column
    private Integer favoriteCount;

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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER, optional = false)
    private User user;

    public Tweet(long idTwitter, String idStr, String text, Date createdAt) {
        this.idTwitter = idTwitter;
        this.idStr = idStr;
        this.text = text;
        this.createdAt = createdAt;
    }

    /**
     * Constructs a Tweet
     *
     * @param idTwitter       The tweet's ID
     * @param idStr           The tweet's ID as a String
     * @param text            The tweet's text
     * @param createdAt       Date Tweet was created
     * @param fromUser        The username of the author of the tweet.
     * @param profileImageUrl The URL to the profile picture of the tweet's author.
     * @param toUserId        The user ID of the user to whom the tweet is targeted.
     * @param fromUserId      The user ID of the tweet's author.
     * @param languageCode    The language code
     * @param source          The source of the tweet.
     */
    public Tweet(long idTwitter, String idStr, String text, Date createdAt, String fromUser, String profileImageUrl, Long toUserId, long fromUserId, String languageCode, String source) {
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

    public Tweet() {
    }

    public void removeAllEntities(){
        this.entities.removeAll();
    }


    public String getFormattedText() {
        String formattedText = this.text;

        formattedText = this.entities.getFormattedText(formattedText);

        return formattedText;
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

    public Tweet getRetweetedStatus() {
        return retweetedStatus;
    }

    public void setRetweetedStatus(Tweet retweetedStatus) {
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

    public Task getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Task createdBy) {
        taskInfo.setTaskInfoFromTask(createdBy);
        this.createdBy = createdBy;
    }

    public Task getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Task updatedBy) {
        taskInfo.setTaskInfoFromTask(updatedBy);
        this.updatedBy = updatedBy;
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

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tweet)) return false;
        if (!super.equals(o)) return false;

        Tweet myTweet = (Tweet) o;

        return idTwitter == myTweet.idTwitter;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (idTwitter ^ (idTwitter >>> 32));
        return result;
    }

    @Override
    public int compareTo(Tweet other) {
        return Long.compare(idTwitter,other.getIdTwitter());
    }


    private String toStringCreatedBy(){
        if(createdBy==null){
            return " null ";
        } else {
            return createdBy.toString();
        }
    }

    private String toStringUpdatedBy(){
        if(updatedBy==null){
            return " null ";
        } else {
            return updatedBy.toString();
        }
    }

    private String toStringTaskInfo(){
        if(taskInfo==null){
            return " null ";
        } else {
            return taskInfo.toString();
        }
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
                ",\n createdBy="+ toStringCreatedBy() +
                ",\n updatedBy=" + toStringUpdatedBy() +
                ",\n taskInfo="+ toStringTaskInfo() +
                ",\n entities=" + toStringEntities() +
                ",\n user=" + toStringUser() +
                "\n}";
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
