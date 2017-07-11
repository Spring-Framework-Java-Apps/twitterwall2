package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.common.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.listener.entities.MentionListener;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "mention", uniqueConstraints = {
    @UniqueConstraint(name = "unique_mention", columnNames = {"screen_name", "id_twitter"})
}, indexes = {
    @Index(name = "idx_mention_name", columnList = "name")
})
@NamedQueries({
    @NamedQuery(
        name = "Mention.findByIdTwitter",
        query = "select t from Mention as t where t.idTwitter=:idTwitter"
    ),
    @NamedQuery(
        name = "Mention.findByScreenName",
        query = "select t from Mention as t where t.screenName=:screenName"
    ),
    @NamedQuery(
        name = "Mention.findByIdTwitterAndScreenName",
        query = "select t from Mention as t where t.idTwitter=:idTwitter and t.screenName=:screenName"
    ),
    @NamedQuery(
        name = "Mention.count",
        query = "select count(t) from Mention as t"
    ),
    @NamedQuery(
        name = "Mention.getAll",
        query = "select t from Mention as t"
    )
})
@EntityListeners(MentionListener.class)
public class Mention extends AbstractTwitterObject<Mention> implements DomainObjectWithIdTwitter<Mention>, DomainObjectWithScreenName<Mention> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private TaskInfo taskInfo  = new TaskInfo();

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Task createdBy;

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Task updatedBy;

    @Column(name = "id_twitter")
    private long idTwitter;

    public static boolean isValidScreenName(String screenName) {
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_PATTERN + "$");
        Matcher m = p.matcher(screenName);
        return m.matches();
    }

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "name")
    private String name;

/*
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mention_indices", joinColumns = @JoinColumn(name = "id"))
    protected List<Integer> indices = new ArrayList<>();

    public void setIndices(int[] indices) {
        this.indices.clear();
        for(Integer index: indices){
            this.indices.add(index);
        }
    }*/

    public Mention(long idTwitter, String screenName, String name, int[] indices) {
        //setIndices(indices);
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        this.name = name;
    }

    private Mention() {
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
/*
    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }
*/
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
        /*
        StringBuffer myIndieces = new StringBuffer();
        myIndieces.append("[ ");
        for (Integer index : indices) {
            myIndieces.append(index.toString());
            myIndieces.append(", ");
        }
        myIndieces.append(" ]");*/
        return "Mention{" +
            " id=" + id +
            ", idTwitter=" + idTwitter +
            ", screenName='" + screenName + '\'' +
            ", name='" + name + '\'' +
            ",\n createdBy="+ toStringCreatedBy() +
            ",\n updatedBy=" + toStringUpdatedBy() +
            ",\n taskInfo="+ toStringTaskInfo() +
            //", indices=" + myIndieces.toString() +
            " }\n";
    }

    public boolean isValid() {
        if((screenName == null) ||(screenName.isEmpty())){
            return false;
        }
        if(idTwitter <= 1L){
            return false;
        }
        return true;
    }
}
