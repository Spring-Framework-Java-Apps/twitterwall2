package org.woehlke.twitterwall.oodm.entities.parts;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

/**
 * Created by tw on 10.06.17.
 */
@MappedSuperclass
public abstract class AbstractTwitterObject<T extends DomainObject> implements DomainObject<T> {

    @NotNull
    @Embedded
    private TaskInfo taskInfo  = new TaskInfo();

    @NotNull
    @JoinColumn(name = "fk_created_by_task",nullable = false)
    @ManyToOne(cascade = { REFRESH, DETACH }, fetch = EAGER,optional = false)
    private Task createdBy;

    @JoinColumn(name = "fk_updated_by_task",nullable = true)
    @ManyToOne(cascade = { REFRESH ,DETACH}, fetch = EAGER, optional = true)
    private Task updatedBy;

    @Transient
    private Map<String, Object> extraData = new HashMap<>();

    protected AbstractTwitterObject(Task createdBy, Task updatedBy) {
        this.createdBy=createdBy;
        this.updatedBy=updatedBy;
        this.taskInfo.setTaskInfoFromTask(createdBy);
        this.taskInfo.setTaskInfoFromTask(updatedBy);
    }

    protected AbstractTwitterObject() {}

    /**
     * @return Any fields in response from Twitter that are otherwise not mapped to any properties.
     */
    public Map<String, Object> getExtraData() {
        return extraData;
    }

    /**
     * {@link JsonAnySetter} hook. Called when an otherwise unmapped property is being processed during JSON deserialization.
     *
     * @param key   The property's key.
     * @param value The property's value.
     */
    protected void add(String key, Object value) {
        extraData.put(key, value);
    }

    public void setExtraData(Map<String, Object> extraData) {
      this.extraData = extraData;
    }

  @Override
    public boolean equals(T o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTwitterObject)) return false;

        AbstractTwitterObject that = (AbstractTwitterObject) o;

        return extraData != null ? extraData.equals(that.extraData) : that.extraData == null;
    }

    @Override
    public int hashCode() {
        return extraData != null ? extraData.hashCode() : 0;
    }

  @Override
  public String toString() {
    StringBuffer myExtraData = new StringBuffer(", extraData=");
    myExtraData.append("[ ");
    for (String extraDatumKey : extraData.keySet()) {
      myExtraData.append(extraDatumKey);
      myExtraData.append(", ");
    }
    myExtraData.append(",\n createdBy="+ toStringCreatedBy());
    myExtraData.append(",\n updatedBy=" + toStringUpdatedBy());
    myExtraData.append(",\n taskInfo="+ toStringTaskInfo());
    return   myExtraData.toString();
  }

    public abstract String getUniqueId();

    @Override
    public int compareTo(T other) {
        return this.getUniqueId().compareTo(other.getUniqueId());
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
        //this.taskInfo.setTaskInfoFrom(taskInfo);
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
}
