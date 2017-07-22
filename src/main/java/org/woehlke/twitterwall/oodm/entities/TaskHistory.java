package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.SafeHtml;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectMinimal;
import org.woehlke.twitterwall.oodm.entities.parts.TaskStatus;
import org.woehlke.twitterwall.oodm.entities.listener.TaskHistoryListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by tw on 10.07.17.
 */
@Entity
@Table(
    name = "task_history",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_task_history",columnNames = {"id_task","time_event"})
    },
    indexes = {
        @Index(name = "idx_task_history_status_before", columnList = "status_before"),
        @Index(name = "idx_task_history_status_now", columnList = "status_now")
    }
)
@NamedQueries({
    @NamedQuery(
        name="TaskHistory.findByUniqueId",
        query="select t from TaskHistory t where t.idTask=:idTask and t.timeEvent=:timeEvent"
    )
})
@EntityListeners(TaskHistoryListener.class)
public class TaskHistory implements DomainObjectMinimal<TaskHistory> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @SafeHtml
    @NotNull
    @Column(name="description",nullable = false,columnDefinition="text")
    private String description = "NULL";

    @NotNull
    @Column(name="status_before",nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatusBefore = TaskStatus.NULL;

    @NotNull
    @Column(name="status_now",nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatusNow = TaskStatus.NULL;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time_event",nullable = false)
    private Date timeEvent = new Date();

    @NotNull
    @Column(name="id_task",nullable = false)
    private Long idTask = 0L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="task_id")
    private Task task;

    private TaskHistory() {
    }

    public TaskHistory(String description, TaskStatus taskStatusBefore, TaskStatus taskStatusNow) {
        this.description = description;
        this.taskStatusBefore = taskStatusBefore;
        this.taskStatusNow = taskStatusNow;
    }

    public TaskHistory(String description, TaskStatus taskStatusBefore, TaskStatus taskStatusNow, Date timeEvent, Task task) {
        this.description = description;
        this.taskStatusBefore = taskStatusBefore;
        this.taskStatusNow = taskStatusNow;
        this.timeEvent = timeEvent;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUniqueId() {
        return "" + task.getId().toString()  +"_"+  timeEvent.getTime() ;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getTaskStatusBefore() {
        return taskStatusBefore;
    }

    public void setTaskStatusBefore(TaskStatus taskStatusBefore) {
        this.taskStatusBefore = taskStatusBefore;
    }

    public TaskStatus getTaskStatusNow() {
        return taskStatusNow;
    }

    public void setTaskStatusNow(TaskStatus taskStatusNow) {
        this.taskStatusNow = taskStatusNow;
    }

    public Date getTimeEvent() {
        return timeEvent;
    }

    public void setTimeEvent(Date timeEvent) {
        this.timeEvent = timeEvent;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    @Override
    public int compareTo(TaskHistory other) {
        return getUniqueId().compareTo(other.getUniqueId());
    }

    @Override
    public String toString() {
        return "TaskHistory{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", taskStatusBefore=" + taskStatusBefore +
            ", taskStatusNow=" + taskStatusNow +
            ", timeEvent=" + timeEvent +
            ", task.id=" + idTask +
            '}';
    }

    @Override
    public boolean isValid() {
        if(taskStatusBefore == null){
            return false;
        }
        if(taskStatusNow == null){
            return false;
        }
        if(timeEvent == null){
            return false;
        }
        if((description == null)||(description.isEmpty())){
            return false;
        }
        if(idTask == null){
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskHistory)) return false;

        TaskHistory that = (TaskHistory) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getTimeEvent() != null ? !getTimeEvent().equals(that.getTimeEvent()) : that.getTimeEvent() != null)
            return false;
        return getIdTask() != null ? getIdTask().equals(that.getIdTask()) : that.getIdTask() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTimeEvent() != null ? getTimeEvent().hashCode() : 0);
        result = 31 * result + (getIdTask() != null ? getIdTask().hashCode() : 0);
        return result;
    }
}
