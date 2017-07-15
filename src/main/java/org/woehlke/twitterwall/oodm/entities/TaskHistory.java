package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.parts.TaskStatus;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.listener.TaskHistoryListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tw on 10.07.17.
 */
@Entity
@Table(name = "task_history", indexes = {
    @Index(name = "idx_task_history_time_event", columnList = "time_event"),
    @Index(name = "idx_task_history_task_status_before", columnList = "task_status_before"),
    @Index(name = "idx_task_history_task_status_now", columnList = "task_status_now")
})
@NamedQueries({
    @NamedQuery(
        name = "TaskHistory.findById",
        query = "select t from TaskHistory as t where t.id=:id"
    ),
    @NamedQuery(
        name = "TaskHistory.count",
        query = "select count(t) from TaskHistory as t"
    ),
    @NamedQuery(
        name = "TaskHistory.getAll",
        query = "select t from TaskHistory as t"
    ),
    @NamedQuery(
        name = "TaskHistory.findByTask",
        query = "select t from TaskHistory as t where t.task.id=:oneTaskId"
    )
})
@EntityListeners(TaskHistoryListener.class)
public class TaskHistory implements DomainObject<TaskHistory> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name="description",nullable = false,columnDefinition="text")
    private String description;

    @Column(name="task_status_before",nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatusBefore;

    @Column(name="task_status_now",nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatusNow;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time_event",nullable = false)
    private Date timeEvent;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name="task_id",nullable = false)
    private Task task;

    public TaskHistory() {
    }

    public TaskHistory(String description, TaskStatus taskStatusBefore, TaskStatus taskStatusNow) {
        this.description = description;
        this.taskStatusBefore = taskStatusBefore;
        this.taskStatusNow = taskStatusNow;
        this.timeEvent = new Date();
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
    public boolean equals(TaskHistory that) {
        if (this == that) return true;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (taskStatusBefore != that.taskStatusBefore) return false;
        if (taskStatusNow != that.taskStatusNow) return false;
        return timeEvent != null ? timeEvent.equals(that.timeEvent) : that.timeEvent == null;
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

    @Override
    public int compareTo(TaskHistory o) {
        return timeEvent.compareTo(o.getTimeEvent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskHistory)) return false;

        TaskHistory that = (TaskHistory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (taskStatusBefore != that.taskStatusBefore) return false;
        if (taskStatusNow != that.taskStatusNow) return false;
        return timeEvent != null ? timeEvent.equals(that.timeEvent) : that.timeEvent == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (taskStatusBefore != null ? taskStatusBefore.hashCode() : 0);
        result = 31 * result + (taskStatusNow != null ? taskStatusNow.hashCode() : 0);
        result = 31 * result + (timeEvent != null ? timeEvent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskHistory{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", taskStatusBefore=" + taskStatusBefore +
            ", taskStatusNow=" + taskStatusNow +
            ", timeEvent=" + timeEvent +
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
        return true;
    }
}
