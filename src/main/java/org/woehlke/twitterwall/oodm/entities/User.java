package org.woehlke.twitterwall.oodm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="userprofile")
public class User extends AbstractTwitterObject implements Serializable,Comparable<User>  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique=true)
    private long idTwitter;

    @Column(nullable = false)
    private String screenName;

    @Column(nullable = false)
    private String name;

    @Column
    private String url;

    @Column
    private String profileImageUrl;

    @Column
    private String description;

    @Column
    private String location;

    @Column(nullable = false)
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

    @Column
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
    private String profileBannerUrl;

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

    public String getBigProfileImageUrl(){
        String bigProfileImageUrl = this.profileImageUrl;
        bigProfileImageUrl = bigProfileImageUrl.replace("_normal.jpg","_400x400.jpg");
        return bigProfileImageUrl;
    }

    private User() {
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

    public String getScreenName() {
        return screenName;
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

    @Override
    public boolean equals(Object o) {
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
}
