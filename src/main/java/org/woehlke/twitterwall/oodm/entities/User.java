package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.common.AbstractFormattedText;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.listener.UserListener;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "userprofile", uniqueConstraints = {
        @UniqueConstraint(name="unique_user_id",columnNames = {"id_twitter"}),
        @UniqueConstraint(name="unique_user_screen_name",columnNames = {"screen_name"})
}, indexes = {
        @Index(name="idx_userprofile_created_date", columnList="created_date"),
        @Index(name="idx_userprofile_screen_name", columnList="screen_name"),
        @Index(name="idx_userprofile_description", columnList="description"),
        @Index(name="idx_userprofile_location", columnList="location"),
        @Index(name="idx_userprofile_url", columnList="url")
})
@NamedQueries({
        @NamedQuery(
                name = "User.findByIdTwitter",
                query = "select t from User as t where t.idTwitter=:idTwitter"
        ),
        @NamedQuery(
                name = "User.findByScreenName",
                query = "select t from User as t where t.screenName=:screenName"
        ),
        @NamedQuery(
                name = "User.getAll",
                query = "select t from User as t order by t.screenName"
        ),
        @NamedQuery(
            name = "User.count",
            query = "select count(t) from User as t"
        ),
        @NamedQuery(
                name = "User.getTweetingUsers",
                query = "select t from User as t where t.tweeting=true order by t.screenName"
        ),
        @NamedQuery(
                name = "User.getNotYetFriendUsers",
                query = "select t from User as t where t.following=false order by t.screenName"
        ),
        @NamedQuery(
            name = "User.getNotYetOnList",
            query = "select t from User as t where t.onDefinedUserList=false and t.tweeting=true order by t.screenName"
        ),
        @NamedQuery(
            name = "User.getOnList",
            query = "select t from User as t where t.onDefinedUserList=true order by t.screenName"
        ),
        @NamedQuery(
                name = "User.getUsersForHashTag",
                query = "select t from User as t join t.tags tag WHERE tag.text=:hashtagText order by t.screenName"
        ),
        @NamedQuery(
                name = "User.countUsersForHashTag",
                query = "select count(t) from User as t join t.tags tag WHERE tag.text=:hashtagText"
        ),
        @NamedQuery(
                name = "User.getAllDescriptions",
                query = "select t.description from User as t where t.description is not null"
        ),
        @NamedQuery(
                name = "User.getAllTwitterIds",
                query = "select t.idTwitter from User as t"
        )
})
@EntityListeners(UserListener.class)
public class User extends AbstractFormattedText<User> implements DomainObjectWithIdTwitter<User>,DomainObjectWithScreenName<User> {

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

    @Column(name="id_twitter",nullable = false)
    private long idTwitter;

    public final static String SCREEN_NAME_PATTERN = "\\w*";

    public static boolean isValidScreenName(String screenName){
        Pattern p = Pattern.compile("^"+SCREEN_NAME_PATTERN+"$");
        Matcher m = p.matcher(screenName);
        return m.matches();
    }

    @Column(name="screen_name", nullable = false)
    private String screenName;

    @Column(nullable = false)
    private String name;

    @Column(length = 1024)
    private String url;

    @Column(length = 1024)
    private String profileImageUrl;

    @Column
    private String description;

    @Column
    private String location;

    @Column(name="created_date",nullable = false)
    private Date createdDate;

    @Column
    private String language;

    @Column
    private int statusesCount;

    @Column
    private int friendsCount;

    @Column
    private int followersCount;

    @Column
    private int favoritesCount;

    @Column
    private int listedCount;

    @Column
    private boolean following;

    @Column
    private boolean followRequestSent;

    @Column
    private boolean isProtected;

    @Column
    private boolean notificationsEnabled;

    @Column
    private boolean verified;

    @Column
    private boolean geoEnabled;

    @Column
    private boolean contributorsEnabled;

    @Column
    private boolean translator;

    @Column
    private String timeZone;

    @Column
    private int utcOffset;

    @Column
    private String sidebarBorderColor;

    @Column
    private String sidebarFillColor;

    @Column
    private String backgroundColor;

    @Column
    private boolean useBackgroundImage;

    @Column(length = 1024)
    private String backgroundImageUrl;

    @Column
    private boolean backgroundImageTiled;

    @Column
    private String textColor;

    @Column
    private String linkColor;

    @Column
    private boolean showAllInlineMedia;

    @Column
    private boolean follower;

    @Column
    private boolean friend;

    @Column
    private boolean tweeting;

    @Column
    private boolean onDefinedUserList;

    @Column(length = 1024)
    private String profileBannerUrl;

    @JoinTable(name = "userprofile_url")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Url> urls = new LinkedHashSet<Url>();

    @JoinTable(name = "userprofile_hashtag")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<HashTag> tags = new LinkedHashSet<HashTag>();

    @JoinTable(name = "userprofile_mention")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Mention> mentions = new LinkedHashSet<>();

    public User(long idTwitter, String screenName, String name, String url, String profileImageUrl, String description, String location, Date createdDate) {
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        this.name = name;
        this.url = url;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.location = location;
        this.createdDate = createdDate;
    }

    public String getBigProfileImageUrl() {
        String bigProfileImageUrl = this.profileImageUrl;
        bigProfileImageUrl = bigProfileImageUrl.replace("_normal.jpg", "_400x400.jpg");
        return bigProfileImageUrl;
    }

    private User() {
    }

    public String getFormattedDescription() {
        String formattedDescription = this.description;

        Set<Url> urls = this.getUrls();
        formattedDescription = getFormattedUrlForUrls(urls, formattedDescription);

        Set<Mention> mentions = this.getMentions();
        formattedDescription = getFormattedTextForMentions(mentions, formattedDescription);

        Set<HashTag> tags = this.getTags();
        formattedDescription = getFormattedTextForHashTags(tags, formattedDescription);

        return formattedDescription;
    }

    public String getFormattedUrl() {
        String formattedUrl = this.url;
        Set<Url> urls = this.getUrls();
        formattedUrl = getFormattedUrlForUrls(urls, formattedUrl);
        return formattedUrl;
    }

    public String getCssBackgroundImage(){
        if(useBackgroundImage && (backgroundImageUrl != null) && (!backgroundImageUrl.isEmpty())){
            return "img-responsive my-background";
        } else {
            return "hidden";
        }
    }

    public String getCssProfileBannerUrl(){
        String style ="img-circle my-profile-image";
        if(useBackgroundImage && (backgroundImageUrl != null) && (!backgroundImageUrl.isEmpty())){
            style += " my-profile-image-with-bg";
        }
        return style;
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

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(int statusesCount) {
        this.statusesCount = statusesCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public int getListedCount() {
        return listedCount;
    }

    public void setListedCount(int listedCount) {
        this.listedCount = listedCount;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public boolean isFollowRequestSent() {
        return followRequestSent;
    }

    public void setFollowRequestSent(boolean followRequestSent) {
        this.followRequestSent = followRequestSent;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isGeoEnabled() {
        return geoEnabled;
    }

    public void setGeoEnabled(boolean geoEnabled) {
        this.geoEnabled = geoEnabled;
    }

    public boolean isContributorsEnabled() {
        return contributorsEnabled;
    }

    public void setContributorsEnabled(boolean contributorsEnabled) {
        this.contributorsEnabled = contributorsEnabled;
    }

    public boolean isTranslator() {
        return translator;
    }

    public void setTranslator(boolean translator) {
        this.translator = translator;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(int utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getSidebarBorderColor() {
        return sidebarBorderColor;
    }

    public void setSidebarBorderColor(String sidebarBorderColor) {
        this.sidebarBorderColor = sidebarBorderColor;
    }

    public String getSidebarFillColor() {
        return sidebarFillColor;
    }

    public void setSidebarFillColor(String sidebarFillColor) {
        this.sidebarFillColor = sidebarFillColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isUseBackgroundImage() {
        return useBackgroundImage;
    }

    public void setUseBackgroundImage(boolean useBackgroundImage) {
        this.useBackgroundImage = useBackgroundImage;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public boolean isBackgroundImageTiled() {
        return backgroundImageTiled;
    }

    public void setBackgroundImageTiled(boolean backgroundImageTiled) {
        this.backgroundImageTiled = backgroundImageTiled;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getLinkColor() {
        return linkColor;
    }

    public void setLinkColor(String linkColor) {
        this.linkColor = linkColor;
    }

    public boolean isShowAllInlineMedia() {
        return showAllInlineMedia;
    }

    public void setShowAllInlineMedia(boolean showAllInlineMedia) {
        this.showAllInlineMedia = showAllInlineMedia;
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public void setProfileBannerUrl(String profileBannerUrl) {
        this.profileBannerUrl = profileBannerUrl;
    }

    public boolean isFollower() {
        return follower;
    }

    public void setFollower(boolean follower) {
        this.follower = follower;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public boolean isTweeting() {
        return tweeting;
    }

    public void setTweeting(boolean tweeting) {
        this.tweeting = tweeting;
    }

    public void setIdTwitter(long idTwitter) {
        this.idTwitter = idTwitter;
    }

    @Override
    public String getScreenName() {
        return this.screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isOnDefinedUserList() {
        return onDefinedUserList;
    }

    public void setOnDefinedUserList(boolean onDefinedUserList) {
        this.onDefinedUserList = onDefinedUserList;
    }

    public Set<Url> getUrls() {
        return urls;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
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

    public void setUrls(Set<Url> urls) {
        this.urls.clear();
        this.urls.addAll(urls);
    }

    public boolean addAllUrls(Set<Url> urls) {
        boolean result = false;
        for(Url url:urls){
            if(url != null){
                this.urls.add(url);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllUrls(Set<Url> urls) {
        boolean result = false;
        for(Url url:urls){
            if(url != null){
                this.urls.remove(url);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAllUrls() {
        this.urls.clear();
        return this.urls.isEmpty();
    }

    public boolean addUrl(Url url) {
        if(url == null){
            return false;
        } else {
            return this.urls.add(url);
        }
    }

    public boolean removetUrl(Url url) {
        if(url == null) {
            return true;
        } else {
            return this.urls.remove(url);
        }
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


    @Override
    public boolean equals(User o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User that = (User) o;

        return idTwitter == that.idTwitter;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (idTwitter ^ (idTwitter >>> 32));
        return result;
    }

    @Override
    public int compareTo(User other) {
        return screenName.compareTo(other.getScreenName());
    }

    @Override
    public String toString() {
        StringBuffer myUrls = new StringBuffer();
        myUrls.append("[ ");
        for (Url url : urls) {
            if(url != null) {
                myUrls.append(url.toString());
                myUrls.append(", ");
            } else {
                myUrls.append(", null");
            }
        }
        myUrls.append(" ]");
        StringBuffer myTags = new StringBuffer();
        myTags.append("[ ");
        for (HashTag tag : tags) {
            if(tag != null){
                myTags.append(tag.toString());
                myTags.append(", ");
            } else {
                myUrls.append(", null");
            }
        }
        myTags.append(" ]");
        StringBuffer myMentions = new StringBuffer();
        myMentions.append("[ ");
        for (Mention mention : mentions) {
            if(mention != null){
                myMentions.append(mention.toString());
                myMentions.append(", ");
            } else {
                myUrls.append(", null");
            }
        }
        myMentions.append(" ]");
        return "User{" +
                "id=" + id +
                ", idTwitter=" + idTwitter +
                ", screenName='" + screenName + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", createdDate=" + createdDate +
                ", language='" + language + '\'' +
                ", statusesCount=" + statusesCount +
                ", friendsCount=" + friendsCount +
                ", followersCount=" + followersCount +
                ", favoritesCount=" + favoritesCount +
                ", listedCount=" + listedCount +
                ", following=" + following +
                ", followRequestSent=" + followRequestSent +
                ", isProtected=" + isProtected +
                ", notificationsEnabled=" + notificationsEnabled +
                ", verified=" + verified +
                ", geoEnabled=" + geoEnabled +
                ", contributorsEnabled=" + contributorsEnabled +
                ", translator=" + translator +
                ", timeZone='" + timeZone + '\'' +
                ", utcOffset=" + utcOffset +
                ", sidebarBorderColor='" + sidebarBorderColor + '\'' +
                ", sidebarFillColor='" + sidebarFillColor + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", useBackgroundImage=" + useBackgroundImage +
                ", backgroundImageUrl='" + backgroundImageUrl + '\'' +
                ", backgroundImageTiled=" + backgroundImageTiled +
                ", textColor='" + textColor + '\'' +
                ", linkColor='" + linkColor + '\'' +
                ", showAllInlineMedia=" + showAllInlineMedia +
                ", follower=" + follower +
                ", friend=" + friend +
                ", tweeting=" + tweeting +
                ", profileBannerUrl='" + profileBannerUrl + '\'' +
                ",\n urls=" + myUrls.toString() +
                ",\n tags=" + myTags.toString() +
                ",\n mentions=" + myMentions.toString() +
                "\n}";
    }
}
