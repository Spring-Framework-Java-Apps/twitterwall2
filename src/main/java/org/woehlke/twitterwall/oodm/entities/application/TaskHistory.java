package org.woehlke.twitterwall.oodm.entities.application;

import org.woehlke.twitterwall.oodm.entities.application.parts.TaskStatus;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.listener.application.TaskHistoryListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tw on 10.07.17.
 */
@Entity
@Table(name = "task_history")
@EntityListeners(TaskHistoryListener.class)
public class TaskHistory implements DomainObject<TaskHistory> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column
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

    public TaskHistory() {
    }

    public TaskHistory(String description, TaskStatus taskStatusBefore, TaskStatus taskStatusNow) {
        this.description = description;
        this.taskStatusBefore = taskStatusBefore;
        this.taskStatusNow = taskStatusNow;
        this.timeEvent = new Date();
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
}
