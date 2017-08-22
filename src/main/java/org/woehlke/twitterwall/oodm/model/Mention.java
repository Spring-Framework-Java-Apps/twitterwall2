package org.woehlke.twitterwall.oodm.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.model.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithScreenName;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.model.listener.MentionListener;
import org.woehlke.twitterwall.oodm.model.parts.MentionStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "mention",
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_mention", columnNames = {"screen_name_unique", "id_twitter"}),
        @UniqueConstraint(name = "unique_mention_screen_name_unique", columnNames = {"screen_name_unique"}),
    },
    indexes = {
        @Index(name = "idx_mention_name", columnList = "name"),
        @Index(name = "idx_mention_screen_name", columnList = "screen_name"),
        @Index(name = "idx_mention_id_twitter_of_user", columnList = "id_twitter_of_user"),
        @Index(name = "idx_mention_fk_user", columnList = "fk_user")
    }
)
@NamedQueries({
    @NamedQuery(
        name="Mention.findByUniqueId",
        query="select t from Mention t where t.idTwitter=:idTwitter and t.screenNameUnique=:screenNameUnique"
    ),
    @NamedQuery(
        name="Mention.findAllWithoutUser",
        query="select t from Mention t where t.idTwitterOfUser=0L or t.idOfUser=0L"
    ),
    @NamedQuery(
        name="Mention.countAllWithoutUser",
        query="select count(t) from Mention t where t.idTwitterOfUser=0L or t.idOfUser=0L"
    ),
    @NamedQuery(
        name="Mention.findByUserId",
        query="select t from Mention t where t.idOfUser=:idOfUser order by t.screenName"
    ),
    @NamedQuery(
        name="Mention.countByUserId",
        query="select count(t) from Mention t where t.idOfUser=:idOfUser"
    ),
    @NamedQuery(
        name="Mention.findByScreenNameUnique",
        query="select t from Mention t where t.screenNameUnique=:screenNameUnique order by t.screenName"
    ),
    @NamedQuery(
        name="Mention.countByScreenNameUnique",
        query="select count(t) from Mention t where t.screenNameUnique=:screenNameUnique"
    ),
    @NamedQuery(
        name="Mention.findByIdTwitterOfUser",
        query="select t from Mention t where t.idTwitterOfUser=:idTwitterOfUser order by t.screenName"
    ),
    @NamedQuery(
        name="Mention.countByIdTwitterOfUser",
        query="select count(t) from Mention t where t.idTwitterOfUser=:idTwitterOfUser"
    )
})
@EntityListeners(MentionListener.class)
public class Mention extends AbstractDomainObject<Mention> implements DomainObjectEntity<Mention>,DomainObjectWithIdTwitter<Mention>, DomainObjectWithScreenName<Mention>,DomainObjectWithTask<Mention> {

    private static final long serialVersionUID = 1L;

    public static final long ID_TWITTER_UNDEFINED = -1L;

    public static final long HAS_NO_USER = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "id_twitter")
    private Long idTwitter;

    @NotEmpty
    @Column(name = "screen_name", nullable = false)
    private String screenName = "";

    @NotEmpty
    @Column(name = "screen_name_unique", nullable = false)
    private String screenNameUnique = "";

    @Column(name = "name",length=4096, nullable = false)
    private String name = "";

    @NotNull
    @Column(name = "id_twitter_of_user",nullable = false)
    private Long idTwitterOfUser = HAS_NO_USER;

    @NotNull
    @Column(name = "fk_user",nullable = false)
    private Long idOfUser = HAS_NO_USER;

    @NotNull
    @Column(name="mention_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private MentionStatus mentionStatus = MentionStatus.NULL;

    public Mention(Task createdBy, Task updatedBy, long idTwitter, String screenName, String name) {
        super(createdBy,updatedBy);
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        if(screenName!=null) {
            this.screenNameUnique = screenName.toLowerCase();
        }
        this.name = name;
    }

    public Mention(Task createdBy, Task updatedBy, String mentionString) {
        super(createdBy,updatedBy);
        this.idTwitter = ID_TWITTER_UNDEFINED;
        this.screenName = mentionString;
        if(mentionString!=null) {
            this.screenNameUnique = mentionString.toLowerCase();
        }
        this.name = mentionString;
    }

    private Mention() {
    }

    @Transient
    @Override
    public String getUniqueId() {

        return "" + idTwitter +"_"+ screenNameUnique;
    }

    @Transient
    @Override
    public boolean isValid() {
        if(screenName == null){
            return false;
        }
        if(screenName.isEmpty()){
            return false;
        }
        if(!this.hasValidScreenName()){
            return false;
        }
        if(screenNameUnique == null){
            return false;
        }
        if(screenNameUnique.isEmpty()){
            return false;
        }
        if(!this.hasValidScreenNameUnique()){
            return false;
        }
        if(idTwitter == null){
            return false;
        }
        if(idTwitterOfUser < 0L){
            return false;
        }
        if(this.getScreenName().toLowerCase().compareTo(this.getScreenNameUnique())!=0){
            return false;
        }
        return true;
    }

    @Transient
    public boolean hasValidScreenName() {
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_PATTERN + "$");
        Matcher m = p.matcher(screenName);
        return m.matches();
    }

    public static boolean isValidScreenName(String screenName) {
        if(screenName==null){
            return false;
        }
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_PATTERN + "$");
        Matcher m = p.matcher(screenName);
        return m.matches();
    }

    @Transient
    public boolean hasValidScreenNameUnique() {
        if(screenNameUnique.compareTo(screenName.toLowerCase())!=0){
            return false;
        }
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_UNIQUE_PATTERN+ "$");
        Matcher m = p.matcher(screenNameUnique);
        return m.matches();
    }

    public static boolean isValidScreenNameUnique(String screenNameUnique) {
        if(screenNameUnique==null){
            return false;
        }
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_UNIQUE_PATTERN + "$");
        Matcher m = p.matcher(screenNameUnique);
        return m.matches();
    }

    @Transient
    public Boolean isProxy(){
        return idTwitter == ID_TWITTER_UNDEFINED;
    }

    @Transient
    public boolean hasUser() {
        return idTwitterOfUser > 0L;
    }

    @Transient
    public boolean isNotFetchedFromTwitter() {
        return (this.getIdTwitter() == ID_TWITTER_UNDEFINED);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
    public String getScreenName() {
        return screenName;
    }

    @Override
    public void setScreenName(String screenName) {
        this.screenName = screenName;
        if(screenName!=null) {
            this.screenNameUnique = screenName.toLowerCase();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getIdTwitter() {
        return idTwitter;
    }

    @Override
    public void setIdTwitter(Long idTwitter) {
        this.idTwitter = idTwitter;
    }

    @Transient
    public void setIdTwitteToUndefined() {
        this.idTwitter = ID_TWITTER_UNDEFINED;
    }

    @Transient
    public boolean isIdTwitterUndefined(){
        return (this.idTwitter == ID_TWITTER_UNDEFINED);
    }

    public Long getIdTwitterOfUser() {
        return idTwitterOfUser;
    }

    public void setIdTwitterOfUser(Long idTwitterOfUser) {
        this.idTwitterOfUser = idTwitterOfUser;
    }

    @Override
    public String getScreenNameUnique() {
        return screenNameUnique;
    }

    @Override
    public void setScreenNameUnique(String screenNameUnique) {
        this.screenNameUnique = screenNameUnique.toLowerCase();
    }

    public Long getIdOfUser() {
        return idOfUser;
    }

    public void setIdOfUser(Long idOfUser) {
        this.idOfUser = idOfUser;
    }

    public MentionStatus getMentionStatus() {
        return mentionStatus;
    }

    public void setMentionStatus(MentionStatus mentionStatus) {
        this.mentionStatus = mentionStatus;
    }


    @Override
    public String toString() {
        return "Mention{" +
                "id=" + id +
                ", idTwitter=" + idTwitter +
                ", screenName='" + screenName + '\'' +
                ", screenNameUnique='" + screenNameUnique + '\'' +
                ", name='" + name + '\'' +
                ", idTwitterOfUser=" + idTwitterOfUser +
                ", idOfUser=" + idOfUser +
                ", mentionStatus=" + mentionStatus +
                super.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mention)) return false;

        Mention mention = (Mention) o;

        if (id != null ? !id.equals(mention.id) : mention.id != null) return false;
        if (idTwitter != null ? !idTwitter.equals(mention.idTwitter) : mention.idTwitter != null) return false;
        return screenNameUnique != null ? screenNameUnique.equals(mention.screenNameUnique) : mention.screenNameUnique == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idTwitter != null ? idTwitter.hashCode() : 0);
        result = 31 * result + (screenNameUnique != null ? screenNameUnique.hashCode() : 0);
        return result;
    }

    public static Mention createByTransformService(Task task, long idTwitter, String screenName, String name) {
        Mention mention = new Mention();
        mention.setIdTwitter(idTwitter);
        mention.setCreatedBy(task);
        mention.setScreenName(screenName);
        mention.setName(name);
        mention.setMentionStatus(MentionStatus.FETCHED_FROM_TWITTER);
        mention.setIdOfUser(Mention.HAS_NO_USER);
        mention.setIdTwitterOfUser(Mention.HAS_NO_USER);
        return mention;
    }

    public static Mention createFromUserDescriptionOrTweetText(Task task, String screenName) {
        Mention mention = new Mention();
        mention.setCreatedBy(task);
        mention.setScreenName(screenName);
        mention.setName(screenName);
        mention.setMentionStatus(MentionStatus.CREATED_BY_TEXT);
        mention.setIdTwitteToUndefined();
        mention.setIdOfUser(Mention.HAS_NO_USER);
        mention.setIdTwitterOfUser(Mention.HAS_NO_USER);
        return mention;
    }
}
