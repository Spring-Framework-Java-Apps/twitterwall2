package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.common.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.listener.entities.HashTagListener;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "hashtag", uniqueConstraints = {
        @UniqueConstraint(name="unique_hashtag",columnNames = {"text"})
})
@NamedQueries({
        @NamedQuery(
                name = "HashTag.findByText",
                query = "select t from HashTag as t where t.text=:text"
        ),
        @NamedQuery(
                name = "HashTag.getAll",
                query = "select h from HashTag as h"
        ),
        @NamedQuery(
                name = "HashTag.count" ,
                query = "select count(h) from HashTag as h"
        )
})
@EntityListeners(HashTagListener.class)
public class HashTag extends AbstractTwitterObject<HashTag> implements DomainObject<HashTag> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private TaskInfo taskInfo = new TaskInfo();

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Task createdBy;

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Task updatedBy;

    public final static String HASHTAG_TEXT_PATTERN = "[öÖäÄüÜßa-zA-Z0-9_]{1,139}";

    public static boolean isValidText(String hashtagText){
        //Pattern p = Pattern.compile("^"+HASHTAG_TEXT_PATTERN+"$");
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(hashtagText);
        return m.matches();
    }

    @Column(nullable = false)
    private String text;

    /*
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hashtag_indices", joinColumns = @JoinColumn(name = "id"))
    protected List<Integer> indices = new ArrayList<>();

    public void setIndices(int[] indices) {
        this.indices.clear();
        for(Integer index: indices){
            this.indices.add(index);
        }
    }
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getText() {
        return this.text;
    }

    public HashTag(String text, int[] indices) {
        //setIndices(indices);
        this.text = text;
    }

    private HashTag() {
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
        /*
        StringBuffer myIndieces = new StringBuffer();
        myIndieces.append("[ ");
        for (Integer index : indices) {
            myIndieces.append(index.toString());
            myIndieces.append(", ");
        }
        myIndieces.append(" ]");
        */
        return "HashTag{" +
                " id=" + id +
                ", text='" + text + '\'' +
            ",\n createdBy="+ toStringCreatedBy() +
            ",\n updatedBy=" + toStringUpdatedBy() +
            ",\n taskInfo="+ toStringTaskInfo() +
                  //", indices=" + myIndieces.toString() +
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
