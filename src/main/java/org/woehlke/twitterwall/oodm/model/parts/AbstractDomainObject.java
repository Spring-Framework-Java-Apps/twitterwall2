package org.woehlke.twitterwall.oodm.model.parts;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.hibernate.annotations.IndexColumn;
import org.springframework.validation.annotation.Validated;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.common.DomainObject;
import org.woehlke.twitterwall.oodm.model.tasks.TaskBasedCaching;
import org.woehlke.twitterwall.oodm.model.tasks.TaskInfo;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

/**
 * Created by tw on 10.06.17.
 */
@Validated
@MappedSuperclass
public abstract class AbstractDomainObject<T extends DomainObject> implements DomainObject<T> {

    @Valid
    @NotNull
    @Embedded
    private TaskInfo taskInfo = new TaskInfo();

    @Valid
    @NotNull
    @Embedded
    private TaskBasedCaching taskBasedCaching = new TaskBasedCaching();

    @NotNull
    @JoinColumn(
        name = "fk_created_by_task",
        foreignKey=@ForeignKey(name="fkey_fk_created_by_task"),
        nullable = false
    )
    @ManyToOne(cascade = {REFRESH, DETACH}, fetch = EAGER, optional = false)
    private Task createdBy;

    @JoinColumn(
        name = "fk_updated_by_task",
        foreignKey=@ForeignKey(name="fkey_fk_updated_by_task"),
        nullable = true
    )
    @ManyToOne(cascade = {REFRESH, DETACH}, fetch = EAGER, optional = true)
    private Task updatedBy;

    @Transient
    private Map<String, Object> extraData = new HashMap<>();

    @Transient
    private int[] indices;

    @Transient
    public Boolean isCached(TaskType taskType, long timeToLive){
        return this.taskBasedCaching.isCached(taskType,timeToLive);
    }

    protected AbstractDomainObject(Task createdBy, Task updatedBy) {
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.taskInfo.setTaskInfoFromTask(createdBy);
        this.taskInfo.setTaskInfoFromTask(updatedBy);
        this.taskBasedCaching.store(createdBy);
        this.taskBasedCaching.store(updatedBy);
    }

    protected AbstractDomainObject() {
        this.createdBy = null;
        this.updatedBy = null;
        this.taskInfo = new TaskInfo();
        this.taskBasedCaching = new TaskBasedCaching();
    }

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

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    @Override
    public String toString() {
        StringBuffer myExtraData = new StringBuffer(", extraData=");
        myExtraData.append("[ ");
        for (String extraDatumKey : extraData.keySet()) {
            myExtraData.append(extraDatumKey);
            myExtraData.append(", ");
            myExtraData.append(extraData.get(extraDatumKey));
        }
        myExtraData.append(" ] ");
        myExtraData.append(",\n createdBy=" + this.toStringCreatedBy());
        myExtraData.append(",\n updatedBy=" + this.toStringUpdatedBy());
        myExtraData.append(",\n taskInfo=" + this.toStringTaskInfo());
        myExtraData.append(",\n twitterApiCaching=" + this.getTaskBasedCaching().toString());
        return myExtraData.toString();
    }

    @Override
    public int compareTo(T other) {
        return this.getUniqueId().compareTo(other.getUniqueId());
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

    public TaskBasedCaching getTaskBasedCaching() {
        return taskBasedCaching;
    }

    public void setTaskBasedCaching(TaskBasedCaching taskBasedCaching) {
        this.taskBasedCaching = taskBasedCaching;
    }

    public void setCreatedBy(Task createdBy) {
        this.taskInfo.setTaskInfoFromTask(createdBy);
        this.taskBasedCaching.store(createdBy.getTaskType());
        this.createdBy = createdBy;
    }

    public Task getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Task updatedBy) {
        this.taskInfo.setTaskInfoFromTask(updatedBy);
        this.taskBasedCaching.store(updatedBy.getTaskType());
        this.updatedBy = updatedBy;
    }

    private String toStringCreatedBy() {
        if (createdBy == null) {
            return " null ";
        } else {
            return createdBy.toString();
        }
    }

    private String toStringUpdatedBy() {
        if (updatedBy == null) {
            return " null ";
        } else {
            return updatedBy.toString();
        }
    }

    private String toStringTaskInfo() {
        if (taskInfo == null) {
            return " null ";
        } else {
            return taskInfo.toString();
        }
    }

}
