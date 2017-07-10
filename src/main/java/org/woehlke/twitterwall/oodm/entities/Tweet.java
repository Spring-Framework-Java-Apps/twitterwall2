package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.common.AbstractFormattedText;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.listener.TweetListener;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "tweet", uniqueConstraints = {
        @UniqueConstraint(name="unique_tweet",columnNames = {"id_twitter"})
}, indexes = {
        @Index(name="idx_tweet_created_date", columnList="created_date"),
        @Index(name="idx_tweet_from_user", columnList="from_user"),
        @Index(name="idx_tweet_to_user_id", columnList="to_user_id")  ,
        @Index(name="idx_tweet_in_reply_to_status_id", columnList="in_reply_to_status_id"),
        @Index(name="idx_tweet_in_reply_to_user_id", columnList="in_reply_to_user_id"),
        @Index(name="idx_tweet_in_reply_to_screenName", columnList="in_reply_to_screenName"),
        @Index(name="idx_tweet_from_user_id", columnList="from_user_id")
})
@NamedQueries({
        @NamedQuery(
                name="Tweet.findByIdTwitter",
                query= "select t from Tweet as t where t.idTwitter=:idTwitter"
        ),
        @NamedQuery(
                name="Tweet.getLatestTweets",
                query="select t from Tweet as t order by t.createdAt DESC"
        ),
        @NamedQuery(
                name="Tweet.getTweetsForHashTag",
                query="select t from Tweet as t join t.tags tag WHERE tag.text=:hashtagText order by t.createdAt DESC"
        ),
        @NamedQuery(
                name="Tweet.countTweetsForHashTag",
                query="select count(t) from Tweet as t join t.tags tag WHERE tag.text=:hashtagText"
        ),
        @NamedQuery(
                name="Tweet.count",
                query="select count(t) from Tweet as t"
        ),
    @NamedQuery(
        name="Tweet.getAll",
        query="select t from Tweet as t"
    ),
        @NamedQuery(
                name="Tweet.getTweetsForUser",
                query="select t from Tweet as t WHERE t.user=:user"
        ),
        @NamedQuery(
                name="Tweet.getAllTwitterIds",
                query="select t.idTwitter from Tweet as t"
        )
})
@EntityListeners(TweetListener.class)
public class Tweet extends AbstractFormattedText<Tweet> implements DomainObjectWithIdTwitter<Tweet> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private TaskInfo taskInfo = new TaskInfo();

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Task createdBy;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Task updatedBy;

    @Column(name="id_twitter", nullable = false)
    private long idTwitter;

    @Column(nullable = false)
    private String idStr;

    @Column(nullable = false)
    private String text;

    @Column(name="created_date", nullable = false)
    private Date createdAt;

    @Column(name="from_user")
    private String fromUser;

    @Column
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

    @Column
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

    @JoinTable(name = "tweet_url")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Url> urls = new LinkedHashSet<Url>();

    @JoinTable(name = "tweet_hashtag")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<HashTag> tags = new LinkedHashSet<HashTag>();

    @JoinTable(name = "tweet_mention")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Mention> mentions = new LinkedHashSet<>();

    @JoinTable(name = "tweet_media")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Media> media = new LinkedHashSet<Media>();

    @JoinTable(name = "tweet_tickersymbol")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();

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

    private Tweet() {
    }

    public String getFormattedText() {
        String formattedText = this.text;

        formattedText = getFormattedTextForUserProfiles(formattedText);

        formattedText = getFormattedTextForHashTags(formattedText);

        Set<Media> media = this.getMedia();
        formattedText = getFormattedTextForMedia(media, formattedText);

        Set<Url> urls = this.getUrls();
        formattedText = getFormattedTextForUrls(urls, formattedText);

        Set<Mention> mentions = this.getMentions();

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
        this.createdBy = createdBy;
    }

    public Task getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Task updatedBy) {
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

    public Set<Url> getUrls() {
        return urls;
    }

    public void setUrls(Set<Url> urls) {
        this.urls.clear();
        this.urls.addAll(urls);
    }

    public boolean addAllUrls(Set<Url> urls) {
        return this.urls.addAll(urls);
    }

    public boolean removeAllUrls(Set<Url> urls) {
        return this.urls.removeAll(urls);
    }

    public boolean removeAllUrls() {
        this.urls.clear();
        return this.urls.isEmpty();
    }

    public boolean addUrl(Url url) {
        return this.urls.add(url);
    }

    public boolean removeUrl(Url url) {
        return this.urls.remove(url);
    }



    public Set<HashTag> getTags() {
        return tags;
    }

    public void setTags(Set<HashTag> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
    }

    public boolean addAllTags(Set<HashTag> tags) {
        return this.tags.addAll(tags);
    }

    public boolean removeAllTags(Set<HashTag> tags) {
        return this.tags.removeAll(tags);
    }

    public boolean removeAllTags() {
        this.tags.clear();
        return this.tags.isEmpty();
    }

    public boolean addTag(HashTag tag) {
        return this.tags.add(tag);
    }

    public boolean removeTag(HashTag tag) {
        return this.tags.remove(tag);
    }



    public Set<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(Set<Mention> mentions) {
        this.mentions.clear();
        this.mentions.addAll(mentions);
    }

    public boolean addAllMentions(Set<Mention> mentions) {
        return this.mentions.addAll(mentions);
    }

    public boolean removeAllMentions(Set<Mention> mentions) {
        return this.mentions.removeAll(mentions);
    }

    public boolean removeAllMentions() {
        this.mentions.clear();
        return this.mentions.isEmpty();
    }

    public boolean addMention(Mention mention) {
        return this.mentions.add(mention);
    }

    public boolean removeMention(Mention mention) {
        return this.mentions.remove(mention);
    }



    public Set<Media> getMedia() {
        return media;
    }

    public void setMedia(Set<Media> media) {
        this.media.clear();
        this.media.addAll(media);
    }

    public boolean addAllMedia(Set<Media> media) {
        return this.media.addAll(media);
    }

    public boolean removeAllMedia(Set<Media> media) {
        return this.media.removeAll(media);
    }

    public boolean removeAllMedia() {
        this.media.clear();
        return this.media.isEmpty();
    }

    public boolean addMedium(Media medium) {
        return this.media.add(medium);
    }

    public boolean removeMedium(Media medium) {
        return this.media.remove(medium);
    }



    public Set<TickerSymbol> getTickerSymbols() {
        return tickerSymbols;
    }

    public void setTickerSymbols(Set<TickerSymbol> tickerSymbols) {
        this.tickerSymbols.clear();
        this.tickerSymbols.addAll(tickerSymbols);
    }

    public boolean addAllTickerSymbols(Set<TickerSymbol> tickerSymbols) {
        return this.tickerSymbols.addAll(tickerSymbols);
    }

    public boolean removeAllTickerSymbols() {
        this.tickerSymbols.clear();
        return this.tickerSymbols.isEmpty();
    }

    public boolean addTickerSymbol(TickerSymbol tickerSymbols) {
        return this.tickerSymbols.add(tickerSymbols);
    }

    public boolean removeTickerSymbol(TickerSymbol tickerSymbol) {
        return this.tickerSymbols.remove(tickerSymbol);
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
        return createdAt.compareTo(other.getCreatedAt());
    }

    @Override
    public String toString() {
        StringBuffer myUrls = new StringBuffer();
        myUrls.append("[ ");
        for (Url url : urls) {
            myUrls.append(url.toString());
            myUrls.append(", ");
        }
        myUrls.append(" ]");
        StringBuffer myTags = new StringBuffer();
        myTags.append("[ ");
        for (HashTag tag : tags) {
            myTags.append(tag.toString());
            myTags.append(", ");
        }
        myTags.append(" ]");
        StringBuffer myMentions = new StringBuffer();
        myMentions.append("[ ");
        for (Mention mention : mentions) {
            myMentions.append(mention.toString());
            myMentions.append(", ");
        }
        myMentions.append(" ]");

        StringBuffer myMedia = new StringBuffer();
        myMedia.append("[ ");
        for (Media medium : media) {
            myMedia.append(medium.toString());
            myMedia.append(", ");
        }
        myMedia.append(" ]");

        StringBuffer myTickerSymbols = new StringBuffer();
        myTickerSymbols.append("[ ");
        for (TickerSymbol medium : tickerSymbols) {
            myTickerSymbols.append(medium.toString());
            myTickerSymbols.append(", ");
        }
        myTickerSymbols.append(" ]");
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
                ",\n urls=" + myUrls.toString() +
                ",\n tags=" + myTags.toString() +
                ",\n mentions=" + myMentions.toString() +
                ",\n media=" + myMedia.toString() +
                ",\n tickerSymbols=" + myTickerSymbols.toString() +
                ",\n user=" + user.toString() +
                "\n}";
    }
}
