package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithEntities;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.parts.Entities;
import org.woehlke.twitterwall.oodm.entities.listener.UserListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "userprofile",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_userprofile",columnNames = {"id_twitter","screen_name"}),
    },
    indexes = {
        @Index(name="idx_userprofile_created_date", columnList="created_date"),
        @Index(name="idx_userprofile_screen_name", columnList="screen_name"),
        @Index(name="idx_userprofile_description", columnList="description"),
        @Index(name="idx_userprofile_location", columnList="location"),
        @Index(name="idx_userprofile_url", columnList="url")
    }
)
@NamedQueries({
        @NamedQuery(
            name = "User.findTweetingUsers",
            query = "select t from User as t where t.taskInfo.updatedByFetchTweetsFromTwitterSearch=true"
        ),
        @NamedQuery(
            name = "User.findNotYetFriendUsers",
            query = "select t from User as t where t.following=false"
        ),
        @NamedQuery(
            name = "User.findNotYetOnList",
            query = "select t from User as t where t.taskInfo.updatedByFetchUsersFromDefinedUserList=false and t.taskInfo.updatedByFetchTweetsFromTwitterSearch=true"
        ),
        @NamedQuery(
            name = "User.findOnList",
            query = "select t from User as t where t.taskInfo.updatedByFetchUsersFromDefinedUserList=true"
        ),
        @NamedQuery(
            name="User.getUsersForHashTag",
            query="select t from User as t join t.entities.hashTags hashTag WHERE hashTag.text=:hashtagText"
        ),
        @NamedQuery(
            name="User.countUsersForHashTag",
            query="select count(t) from User as t join t.entities.hashTags hashTag WHERE hashTag.text=:hashtagText"
        ),
        @NamedQuery(
            name = "User.findAllDescriptions",
            query = "select t.description from User as t where t.description is not null"
        ),
        @NamedQuery(
            name = "User.findAllTwitterIds",
            query = "select t.idTwitter from User as t"
        )
})
@NamedNativeQueries({
    @NamedNativeQuery(
        name="User.countAllUser2HashTag",
        query="select count(*) as z from userprofile_hashtag"
    ),
    @NamedNativeQuery(
        name="User.countAllUser2Media",
        query="select count(*) as z from userprofile_media"
    ),
    @NamedNativeQuery(
        name="User.countAllUser2Mention",
        query="select count(*) as z from userprofile_mention"
    ),
    @NamedNativeQuery(
        name="User.countAllUser2TickerSymbol",
        query="select count(*) as z from userprofile_tickersymbol"
    ),
    @NamedNativeQuery(
        name="User.countAllUser2Url",
        query="select count(*) as z from userprofile_url"
    )
})
@EntityListeners(UserListener.class)
public class User extends AbstractTwitterObject<User> implements DomainObjectWithEntities<User>,DomainObjectWithScreenName<User>,DomainObjectWithTask<User> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    @Embedded
    private TaskInfo taskInfo  = new TaskInfo();

    @NotNull
    @JoinColumn(name = "fk_user_created_by")
    @ManyToOne(cascade = { REFRESH, DETACH }, fetch = EAGER,optional = false)
    private Task createdBy;

    @JoinColumn(name = "fk_user_updated_by")
    @ManyToOne(cascade = { REFRESH ,DETACH}, fetch = EAGER,optional = true)
    private Task updatedBy;

    @NotNull
    @Column(name="id_twitter",nullable = false)
    private Long idTwitter;

    @NotEmpty
    @Column(name="screen_name", nullable = false)
    private String screenName;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Column(name="url", length = 4096)
    private String url;

    @Column(length = 4096)
    private String profileImageUrl;

    @Column(name="description", length = 4096)
    private String description;

    @Column(name="location")
    private String location;

    @NotNull
    @Column(name="created_date",nullable = false)
    private Date createdDate;

    @Column
    private String language;

    @Column
    private Integer statusesCount;

    @Column
    private Integer friendsCount;

    @Column
    private Integer followersCount;

    @Column
    private Integer favoritesCount;

    @Column
    private Integer listedCount;

    @Column
    private Boolean following;

    @Column
    private Boolean followRequestSent;

    @Column
    private Boolean isProtected;

    @Column
    private Boolean notificationsEnabled;

    @Column
    private Boolean verified;

    @Column
    private Boolean geoEnabled;

    @Column
    private Boolean contributorsEnabled;

    @Column
    private Boolean translator;

    @Column
    private String timeZone;

    @Column
    private Integer utcOffset;

    @Column
    private String sidebarBorderColor;

    @Column
    private String sidebarFillColor;

    @Column
    private String backgroundColor;

    @Column
    private Boolean useBackgroundImage;

    @Column(length = 4096)
    private String backgroundImageUrl;

    @Column
    private Boolean backgroundImageTiled;

    @Column
    private String textColor;

    @Column
    private String linkColor;

    @Column
    private Boolean showAllInlineMedia;

    @Column
    private Boolean follower;

    @Column
    private Boolean friend;

    @Column
    private Boolean tweeting;

    @Column(length = 4096)
    private String profileBannerUrl;

    @NotNull
    @Embedded
    @AssociationOverrides({
        @AssociationOverride(
            name = "urls",
            joinTable = @JoinTable(
                name="userprofile_url"
            )
        ),
        @AssociationOverride(
            name = "hashTags",
            joinTable = @JoinTable(
                name="userprofile_hashtag"
            )
        ),
        @AssociationOverride(
            name = "mentions",
            joinTable = @JoinTable(
                name="userprofile_mention"
            )
        ),
        @AssociationOverride(
            name = "media",
            joinTable = @JoinTable(
                name="userprofile_media"
            )
        ),
        @AssociationOverride(
            name = "tickerSymbols",
            joinTable = @JoinTable(
                name="userprofile_tickersymbol"
            )
        )
    })
    private Entities entities = new Entities();

    public User(long idTwitter, String screenName, String name, String url, String profileImageUrl, String description, String location, Date createdDate,Task task) {
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        this.name = name;
        this.url = url;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.location = location;
        this.createdDate = createdDate;
        this.createdBy = task;
        this.updatedBy =task;
        this.taskInfo.setTaskInfoFromTask(task);
    }

    private User() {
    }

    public final static String SCREEN_NAME_PATTERN = "\\w*";

    public static boolean isValidScreenName(String screenName){
        Pattern p = Pattern.compile("^"+SCREEN_NAME_PATTERN+"$");
        Matcher m = p.matcher(screenName);
        return m.matches();
    }

    @Transient
    public boolean hasValidScreenName(){
        Pattern p = Pattern.compile("^"+SCREEN_NAME_PATTERN+"$");
        Matcher m = p.matcher(screenName);
        return m.matches();
    }

    public void removeAllEntities(){
        this.entities.removeAll();
    }

    @Transient
    public String getBigProfileImageUrl() {
        String bigProfileImageUrl = this.profileImageUrl;
        bigProfileImageUrl = bigProfileImageUrl.replace("_normal.jpg", "_400x400.jpg");
        return bigProfileImageUrl;
    }

    @Transient
    public String getFormattedDescription() {
        String formattedDescription = this.description;
        formattedDescription = this.entities.getFormattedText(formattedDescription);
        return formattedDescription;
    }


    @Transient
    public String getFormattedUrl() {
        String formattedUrl = this.url;
        Set<Url> urls = this.entities.getUrls();
        formattedUrl = this.entities.getFormattedUrlForUrls(urls, formattedUrl);
        return formattedUrl;
    }

    @Transient
    public String getCssBackgroundImage(){
        if(useBackgroundImage && (backgroundImageUrl != null) && (!backgroundImageUrl.isEmpty())){
            return "img-responsive my-background";
        } else {
            return "hidden";
        }
    }

    @Transient
    public String getCssProfileBannerUrl(){
        String style ="img-circle my-profile-image";
        if(useBackgroundImage && (backgroundImageUrl != null) && (!backgroundImageUrl.isEmpty())){
            style += " my-profile-image-with-bg";
        }
        return style;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    @Override
    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public Long getIdTwitter() {
        return idTwitter;
    }

    public void setIdTwitter(Long idTwitter) {
        this.idTwitter = idTwitter;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(Integer statusesCount) {
        this.statusesCount = statusesCount;
    }

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public Integer getListedCount() {
        return listedCount;
    }

    public void setListedCount(Integer listedCount) {
        this.listedCount = listedCount;
    }

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public Boolean getFollowRequestSent() {
        return followRequestSent;
    }

    public void setFollowRequestSent(Boolean followRequestSent) {
        this.followRequestSent = followRequestSent;
    }

    public Boolean getProtected() {
        return isProtected;
    }

    public void setProtected(Boolean aProtected) {
        isProtected = aProtected;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getGeoEnabled() {
        return geoEnabled;
    }

    public void setGeoEnabled(Boolean geoEnabled) {
        this.geoEnabled = geoEnabled;
    }

    public Boolean getContributorsEnabled() {
        return contributorsEnabled;
    }

    public void setContributorsEnabled(Boolean contributorsEnabled) {
        this.contributorsEnabled = contributorsEnabled;
    }

    public Boolean getTranslator() {
        return translator;
    }

    public void setTranslator(Boolean translator) {
        this.translator = translator;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(Integer utcOffset) {
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

    public Boolean getUseBackgroundImage() {
        return useBackgroundImage;
    }

    public void setUseBackgroundImage(Boolean useBackgroundImage) {
        this.useBackgroundImage = useBackgroundImage;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public Boolean getBackgroundImageTiled() {
        return backgroundImageTiled;
    }

    public void setBackgroundImageTiled(Boolean backgroundImageTiled) {
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

    public Boolean getShowAllInlineMedia() {
        return showAllInlineMedia;
    }

    public void setShowAllInlineMedia(Boolean showAllInlineMedia) {
        this.showAllInlineMedia = showAllInlineMedia;
    }

    public Boolean getFollower() {
        return follower;
    }

    public void setFollower(Boolean follower) {
        this.follower = follower;
    }

    public Boolean getFriend() {
        return friend;
    }

    public void setFriend(Boolean friend) {
        this.friend = friend;
    }

    public Boolean getTweeting() {
        return tweeting;
    }

    public void setTweeting(Boolean tweeting) {
        this.tweeting = tweeting;
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public void setProfileBannerUrl(String profileBannerUrl) {
        this.profileBannerUrl = profileBannerUrl;
    }

    public Task getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Task createdBy) {
        this.taskInfo.setTaskInfoFromTask(createdBy);
        this.createdBy = createdBy;
    }

    public Task getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Task updatedBy) {
        this.taskInfo.setTaskInfoFromTask(updatedBy);
        this.updatedBy = updatedBy;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
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

    @Override
    public String toString() {
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
                ",\n createdBy="+ toStringCreatedBy() +
                ",\n updatedBy=" + toStringUpdatedBy() +
                ",\n taskInfo="+ toStringTaskInfo() +
                ",\n entities=" + this.entities.toString() +
                "\n}";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public static User getDummyUserForScreenName(String screenName,Task task){
        long idTwitter= new Date().getTime();
        String name="Exception Handler Dummy Username";
        String url="https://github.com/phasenraum2010/twitterwall2";
        String profileImageUrl="https://avatars2.githubusercontent.com/u/303766?v=3&s=460";
        String description="Exception Handler Dummy Description with some #HashTag an URL like https://thomas-woehlke.blogspot.de/ and an @Mention.";
        String location="Berlin, Germany";
        Date createdDate = new Date();
        User user = new User(idTwitter,screenName, name, url, profileImageUrl, description, location, createdDate, task);
        return user;
    }
}
