package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithEntities;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.listener.UserListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "userprofile",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_userprofile",columnNames = {"id_twitter","screen_name_unique"}),
        @UniqueConstraint(name="unique_userprofile_id_twitter",columnNames = {"id_twitter"}),
        @UniqueConstraint(name="unique_userprofile_screen_name_unique",columnNames = {"screen_name_unique"}),
    },
    indexes = {
        @Index(name="idx_userprofile_created_date", columnList="created_date"),
        @Index(name="idx_userprofile_description", columnList="description"),
        @Index(name="idx_userprofile_location", columnList="location"),
        @Index(name="idx_userprofile_url", columnList="url")
    }
)
@NamedQueries({
    @NamedQuery(
        name = "User.findTweetingUsers",
        query = "select t from User as t where t.taskInfo.fetchTweetsFromSearch=true"
    ),
    @NamedQuery(
        name = "User.findFollower",
        query = "select t from User as t where t.taskInfo.fetchFollower=true"
    ),
    @NamedQuery(
        name = "User.findNotYetFollower",
        query = "select t from User as t where t.taskInfo.fetchFollower=false"
    ),
    @NamedQuery(
        name = "User.findFriends",
        query = "select t from User as t where t.taskInfo.fetchFriends=true"
    ),
    @NamedQuery(
        name = "User.findNotYetFriends",
        query = "select t from User as t where t.taskInfo.fetchFriends=false"
    ),
    @NamedQuery(
        name = "User.findOnList",
        query = "select t from User as t where t.taskInfo.fetchUsersFromList=true"
    ),
    @NamedQuery(
        name = "User.findNotYetOnList",
        query = "select t from User as t where t.taskInfo.fetchUsersFromList=false and t.taskInfo.fetchTweetsFromSearch=true"
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
        name="User.findByUniqueId",
        query="select t from User as t where t.idTwitter=:idTwitter and t.screenNameUnique=:screenNameUnique"
    ),
    @NamedQuery(
        name="User.findUsersWhoAreFriendsButNotFollowers",
        query="select t from User as t where t.taskInfo.fetchFollower=false and t.taskInfo.fetchFriends=true"
    ),
    @NamedQuery(
        name="User.findUsersWhoAreFollowersAndFriends",
        query="select t from User as t where t.taskInfo.fetchFollower=true and t.taskInfo.fetchFriends=true"
    ),
    @NamedQuery(
        name="User.findUsersWhoAreFollowersButNotFriends",
        query="select t from User as t where t.taskInfo.fetchFollower=true and t.taskInfo.fetchFriends=false"
    ),
    @NamedQuery(
        name="User.getUsersForMedia",
        query="select t from User t join t.entities.media medium where medium=:media"
    ),
    @NamedQuery(
        name="User.getUsersForMention",
        query="select t from User t join t.entities.mentions mention where mention=:mention"
    ),
    @NamedQuery(
        name="User.getUsersForUrl",
        query="select t from User t join t.entities.urls url where url=:url"
    ),
    @NamedQuery(
        name="User.getUsersForTickerSymbol",
        query="select t from User t join t.entities.tickerSymbols tickerSymbol where tickerSymbol=:tickerSymbol"
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
public class User extends AbstractDomainObject<User> implements DomainObjectWithUrl<User>,DomainObjectWithEntities<User>,DomainObjectWithScreenName<User>,DomainObjectWithTask<User> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    @Column(name="id_twitter",nullable = false)
    private Long idTwitter;

    @NotEmpty
    @Column(name="screen_name", nullable = false)
    private String screenName;

    @NotEmpty
    @Column(name = "screen_name_unique", nullable = false)
    private String screenNameUnique = "";

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
    private Boolean protectedUser;

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

    public User(Task createdBy, Task updatedBy, long idTwitter, String screenName, String name, String url, String profileImageUrl, String description, String location, Date createdDate) {
        super(createdBy,updatedBy);
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        this.screenNameUnique = screenName.toLowerCase();
        this.name = name;
        this.url = url;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.location = location;
        this.createdDate = createdDate;
    }

    protected User() {
    }

    @Transient
    @Override
    public boolean isValid() {
        if(this.idTwitter == null){
            return false;
        }
        if(this.screenName == null){
            return false;
        }
        if(!this.hasValidScreenName()){
            return false;
        }
        if(!this.hasValidScreenNameUnique()){
            return false;
        }
        return true;
    }

    @Transient
    @Override
    public String getUniqueId() {
        return idTwitter.toString() + "_" + this.screenNameUnique;
    }

    public final static String SCREEN_NAME_PATTERN = "\\w*";

    public final static String SCREEN_NAME_UNIQUE_PATTERN = "[a-z_0-9]*";

    public static boolean isValidScreenName(String screenName){
        if(screenName==null){
            return false;
        }
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

    public static boolean isValidScreenNameUnique(String screenNameUnique){
        if(screenNameUnique==null){
            return false;
        }
        Pattern p = Pattern.compile("^"+SCREEN_NAME_UNIQUE_PATTERN+"$");
        Matcher m = p.matcher(screenNameUnique);
        return m.matches();
    }

    @Transient
    public boolean hasValidScreenNameUnique(){
        if(screenNameUnique.compareTo(screenName.toLowerCase())!=0){
            return false;
        }
        Pattern p = Pattern.compile("^"+SCREEN_NAME_UNIQUE_PATTERN+"$");
        Matcher m = p.matcher(screenNameUnique);
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
    public String getCssProfileBannerUrl(){
        if((profileBannerUrl != null) && (!profileBannerUrl.isEmpty())){
            return "img-responsive my-background";
        } else {
            return "hidden";
        }
    }

    @Transient
    public String getCssProfileImageUrl(){
        if((profileImageUrl != null) && (!profileImageUrl.isEmpty())){
            String style ="img-circle my-profile-image";
            if((profileBannerUrl != null) && (!profileBannerUrl.isEmpty())) {
                style += " my-profile-image-with-bg";
            }
            return style;
        } else {
            return "hidden";
        }
    }

    @Transient
    public boolean isTweeting(){
        Boolean isTweeting = this.getTaskInfo().getFetchTweetsFromSearch();
        if(isTweeting == null) {return false; } else { return isTweeting; }
    }

    public static User getDummyUserForScreenName(String screenName,Task task){
        long idTwitter= new Date().getTime();
        String name="Exception Handler Dummy Username";
        String url="https://github.com/phasenraum2010/twitterwall2";
        String profileImageUrl="https://avatars2.githubusercontent.com/u/303766?v=3&s=460";
        String description="Exception Handler Dummy Description with some #HashTag an URL like https://thomas-woehlke.blogspot.de/ and an @Mention.";
        String location="Berlin, Germany";
        Date createdDate = new Date();
        User user = new User(task,null,idTwitter,screenName, name, url, profileImageUrl, description, location, createdDate);
        return user;
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
        this.screenNameUnique = screenName.toLowerCase();
    }

    @Override
    public String getScreenNameUnique() {
        return screenNameUnique;
    }

    @Override
    public void setScreenNameUnique(String screenNameUnique) {
        this.screenNameUnique = screenNameUnique.toLowerCase();
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

    public Boolean getProtectedUser() {
        if(protectedUser==null){
            return false;
        } else {
            return protectedUser;
        }
    }

    public void setProtectedUser(Boolean protectedUser) {
        this.protectedUser = protectedUser;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public Boolean getVerified() {
        if(verified==null){
            return false;
        } else {
            return verified;
        }
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
        if(this.getTaskInfo().getFetchFollower()==null){
            return false;
        } else {
            return this.getTaskInfo().getFetchFollower();
        }
    }

    public void setFollower(Boolean follower) {
        this.follower = follower;
    }

    public Boolean getFriend() {
        if(this.getTaskInfo().getFetchFriends()==null){
            return false;
        } else {
            return this.getTaskInfo().getFetchFriends();
        }
    }

    public void setFriend(Boolean friend) {
        this.friend = friend;
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public void setProfileBannerUrl(String profileBannerUrl) {
        this.profileBannerUrl = profileBannerUrl;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
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
                ", protectedUser=" + protectedUser +
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
                ", profileBannerUrl='" + profileBannerUrl + '\'' +
                    super.toString() +
                ",\n entities=" + this.entities.toString() +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (getId() != null ? !getId().equals(user.getId()) : user.getId() != null) return false;
        if (getIdTwitter() != null ? !getIdTwitter().equals(user.getIdTwitter()) : user.getIdTwitter() != null)
            return false;
        return getScreenName() != null ? getScreenName().equals(user.getScreenName()) : user.getScreenName() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getIdTwitter() != null ? getIdTwitter().hashCode() : 0);
        result = 31 * result + (getScreenName() != null ? getScreenName().hashCode() : 0);
        return result;
    }

}
