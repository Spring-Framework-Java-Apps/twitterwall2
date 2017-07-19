package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.parts.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.listener.MentionListener;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "mention",
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_mention", columnNames = {"screen_name", "id_twitter"})
    },
    indexes = {
        @Index(name = "idx_mention_name", columnList = "name")
    }
)
@EntityListeners(MentionListener.class)
public class Mention extends AbstractTwitterObject<Mention> implements DomainObjectWithIdTwitter<Mention>, DomainObjectWithScreenName<Mention>,DomainObjectWithTask<Mention> {

    private static final long serialVersionUID = 1L;

    private static final long ID_TWITTER_UNDEFINED = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private TaskInfo taskInfo  = new TaskInfo();

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER,optional = true)
    private Task createdBy;

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER,optional = true)
    private Task updatedBy;

    @Column(name = "id_twitter")
    private long idTwitter;

    public boolean isProxy(){
       return idTwitter < 0;
    }

    public static boolean isValidScreenName(String screenName) {
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_PATTERN + "$");
        Matcher m = p.matcher(screenName);
        return m.matches();
    }

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "name",length=4096)
    private String name;

    public boolean hasPersistentUser(){
        boolean result = false;
        User myUser = this.getUser();
        if(myUser != null){
            result =
                (myUser.getScreenName().compareTo(this.getScreenName())==0) &&
                (idTwitterOfUser != null ) &&
                (myUser.getIdTwitter() == idTwitterOfUser);
        }
        return result;
    }

    @OneToOne(optional=true, fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private User user;

    @Column(name = "id_twitte_of_user",nullable = true)
    private Long idTwitterOfUser;

    public Mention(long idTwitter, String screenName, String name) {
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        this.name = name;
    }

    public Mention(TaskInfo taskInfo, Task createdBy, Task updatedBy, long idTwitter, String screenName, String name, User user, Long idTwitterOfUser) {
        this.taskInfo = taskInfo;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        this.name = name;
        this.user = user;
        this.idTwitterOfUser = idTwitterOfUser;
    }

    public Mention() {
    }

    public boolean hasUser() {
        return user != null;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public void setIdTwitter(long idTwitter) {
        this.idTwitter = idTwitter;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getIdTwitterOfUser() {
        return idTwitterOfUser;
    }

    public void setIdTwitterOfUser(Long idTwitterOfUser) {
        this.idTwitterOfUser = idTwitterOfUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mention)) return false;
        if (!super.equals(o)) return false;

        Mention mention = (Mention) o;

        if (screenName != null ? !screenName.equals(mention.screenName) : mention.screenName != null) return false;
        return name != null ? name.equals(mention.name) : mention.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Mention other) {
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
        return "Mention{" +
            " id=" + id +
            ", idTwitter=" + idTwitter +
            ", screenName='" + screenName + '\'' +
            ", name='" + name + '\'' +
            ",\n createdBy="+ toStringCreatedBy() +
            ",\n updatedBy=" + toStringUpdatedBy() +
            ",\n taskInfo="+ toStringTaskInfo() +
            " }\n";
    }

    public boolean isValid() {
        if((screenName == null) ||(screenName.isEmpty())|| isRawMentionFromUserDescription()){
            return false;
        }
        if(idTwitter <= 1L){
            return false;
        }
        return true;
    }

    public boolean isRawMentionFromUserDescription() {
        return (this.getIdTwitter() == ID_TWITTER_UNDEFINED);
    }

    public static Mention getMention(String mentionString) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
        }
        long idTwitter = ID_TWITTER_UNDEFINED;
        String screenName = mentionString;
        String name = mentionString;
        return new Mention(idTwitter, screenName, name);
    }

}
