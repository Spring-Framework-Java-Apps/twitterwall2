package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.SafeHtml;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.listener.HashTagListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "hashtag",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_hashtag",columnNames = {"text"})
    }
)
@EntityListeners(HashTagListener.class)
public class HashTag extends AbstractTwitterObject<HashTag> implements DomainObject<HashTag>,DomainObjectWithTask<HashTag> {

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

    @SafeHtml
    @Column(name="text", nullable = false,length=4096)
    private String text = "";

    public HashTag(Task createdBy, Task updatedBy, String text) {
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.text = text;
    }

    public HashTag(String text, Task task) {
        this.text = text;
        this.createdBy = task;
        this.updatedBy = task;
        this.taskInfo.setTaskInfoFromTask(task);
    }

    private HashTag() {
    }

    public final static String HASHTAG_TEXT_PATTERN = "[öÖäÄüÜßa-zA-Z0-9_]{1,139}";

    @Transient
    public boolean hasValidText(){
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(text);
        return m.matches();
    }

    public static boolean isValidText(String hashtagText){
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(hashtagText);
        return m.matches();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getText() {
        return this.text;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTag)) return false;
        if (!super.equals(o)) return false;

        HashTag hashTag = (HashTag) o;

        return text != null ? text.equals(hashTag.text) : hashTag.text == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(HashTag other) {
        return text.compareTo(other.getText());
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
        return "HashTag{" +
                " id=" + id +
                ", text='" + text + '\'' +
            ",\n createdBy="+ toStringCreatedBy() +
            ",\n updatedBy=" + toStringUpdatedBy() +
            ",\n taskInfo="+ toStringTaskInfo() +
                " }\n";
    }

    @Override
    public boolean isValid() {
        if((text == null)||(text.isEmpty())){
            return false;
        }
        return true;
    }
}
