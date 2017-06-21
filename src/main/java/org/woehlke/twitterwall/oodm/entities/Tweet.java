package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.entities.Url;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="tweet")
public class Tweet extends AbstractTwitterObject implements Serializable,Comparable<Tweet> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    private Tweet retweetedStatus;

    @Column
    private boolean favorited;

    @Column
    private Integer favoriteCount;

    @OneToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER,optional = true)
    private Entities entities;

    @ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER,optional = false)
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


    private String getFormattedTextForUseerProfiles(String formattedText){
        Pattern userPattern = Pattern.compile("@([a-zA-Z0-9_]{1,15})"+stopChar);
        Matcher m1 = userPattern.matcher(formattedText);
        formattedText = m1.replaceAll("<a class=\"tweet-action tweet-profile\" href=\"/profile/$1\">@$1</a> ");

        Pattern userPattern2 = Pattern.compile("@([a-zA-Z0-9_]{1,15})$");
        Matcher m2 = userPattern2.matcher(formattedText);
        formattedText = m2.replaceAll("<a class=\"tweet-action tweet-profile\" href=\"/profile/$1\">@$1</a> ");

        return formattedText;
    }

    private String getFormattedTextForHashTags(String formattedText) {
        Pattern hashTagPattern = Pattern.compile("#([a-zA-Z0-9_]*)"+stopChar);
        Matcher m3 = hashTagPattern.matcher(formattedText);
        formattedText = m3.replaceAll("<a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/$1\">#$1</a> ");

        Pattern hashTagPattern2 = Pattern.compile("#([a-zA-Z0-9_]*)$");
        Matcher m4 = hashTagPattern2.matcher(formattedText);
        formattedText = m4.replaceAll("<a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/$1\">#$1</a> ");

        return formattedText;
    }

    private String getFormattedTextForMedia(Set<Media> media ,String formattedText) {
        for(Media medium:media) {
            if (medium.getMediaType().compareTo("photo")==0) {
                Pattern myUrl1 = Pattern.compile("(" + medium.getUrl() + ")" + stopChar);
                Matcher m10 = myUrl1.matcher(formattedText);
                formattedText = m10.replaceAll("<br/><br/><a class=\"tweet-photo\" href=\"" + medium.getExpanded() + "\" target=\"_blank\"><img class=\"tweet-photo\" src=\"" + medium.getMediaHttps() + "\" /></a> ");

                Pattern myUrl2 = Pattern.compile("(" + medium.getUrl() + ")$");
                Matcher m11 = myUrl2.matcher(formattedText);
                formattedText = m11.replaceAll("<br/><br/><a class=\"tweet-photo\" href=\"" + medium.getExpanded() + "\" target=\"_blank\"><img class=\"tweet-photo\" src=\"" + medium.getMediaHttps() + "\" /></a> ");
            }
        }
        return formattedText;
    }

    private String getFormattedTextForUrls(Set<Url> urls, String formattedText) {
        for(Url url:urls){

            Pattern myUrl1 = Pattern.compile("("+url.getUrl()+")"+stopChar);
            Matcher m10  = myUrl1.matcher(formattedText);
            boolean url1 = m10.matches();
            formattedText = m10.replaceAll("<a href=\""+url.getExpanded()+"\" class=\"tw-url1\" target=\"_blank\">"+url.getDisplay()+"</a> ");

            Pattern myUrl4 = Pattern.compile("("+url.getUrl()+")$");
            Matcher m20  = myUrl4.matcher(formattedText);
            boolean url2 = m20.matches();
            formattedText = m20.replaceAll("<a href=\""+url.getExpanded()+"\" class=\"tw-url2\" target=\"_blank\">"+url.getDisplay()+"</a> ");


            Pattern myUrl2 = Pattern.compile("("+url.getDisplay()+")"+stopChar);
            Matcher m11  = myUrl2.matcher(formattedText);
            boolean display1 = m11.matches();
            formattedText = m11.replaceAll("<a href=\""+url.getExpanded()+"\" class=\"tw-display1\" target=\"_blank\">"+url.getDisplay()+"</a> ");

            Pattern myUrl5 = Pattern.compile("("+url.getDisplay()+")$");
            Matcher m21  = myUrl5.matcher(formattedText);
            boolean display2 = m21.matches();
            formattedText = m21.replaceAll("<a href=\""+url.getExpanded()+"\" class=\"tw-display2\" target=\"_blank\">"+url.getDisplay()+"</a> ");


            Pattern myUrl3 = Pattern.compile("("+url.getExpanded()+")"+stopChar);
            Matcher m12  = myUrl3.matcher(formattedText);
            boolean expanded1 = m12.matches();
            formattedText = m12.replaceAll("<a href=\""+url.getExpanded()+"\" class=\"tw-expanded1\" target=\"_blank\">"+url.getDisplay()+"</a> ");

            Pattern myUrl6 = Pattern.compile("("+url.getExpanded()+")$");
            Matcher m22  = myUrl6.matcher(formattedText);
            boolean expanded2 = m22.matches();
            formattedText = m22.replaceAll("<a href=\""+url.getExpanded()+"\" class=\"tw-expanded2\" target=\"_blank\">"+url.getDisplay()+"</a> ");

        }
        return formattedText;
    }

    public String getFormattedText(){
        String formattedText = this.text;

        formattedText = getFormattedTextForUseerProfiles(formattedText);

        formattedText = getFormattedTextForHashTags(formattedText);

        Set<Media> media = this.entities.getMedia();
        formattedText = getFormattedTextForMedia(media,formattedText);

        Set<Url> urls = this.entities.getUrls();
        formattedText = getFormattedTextForUrls(urls,formattedText);

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
        return createdAt.compareTo(other.getCreatedAt());
    }

    static private String stopChar;

    static {
        StringBuffer x = new StringBuffer("[\\s");
        x.append("\\!");
        x.append("\\%");
        x.append("\\&");
        x.append("\\'");
        x.append("\\(");
        x.append("\\)");
        x.append("\\*");
        x.append("\\+");
        x.append("\\,");
        x.append("\\-");
        x.append("\\.");
        x.append("\\/");
        x.append("\\:");
        x.append("\\;");
        //x.append("\\<");
        x.append("\\=");
        //x.append("\\>");
        x.append("\\?");
        x.append("\\[");
        x.append("\\]");
        x.append("\\^");
        x.append("\\_");
        x.append("\\`");
        x.append("\\{");
        x.append("\\|");
        x.append("\\}");
        x.append("\\~");
        x.append("\\\\z");
        x.append("]");
        stopChar = x.toString();
    }
}
