package org.woehlke.twitterwall.oodm.entities.application;

import org.woehlke.twitterwall.ScheduledTasks;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.listener.application.TaskListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tw on 09.07.17.
 */
@Entity
@Table(name = "scheduled_task")
@NamedQueries({
    @NamedQuery(
        name = "Task.findById",
        query = "select t from Task as t where t.id=:id"
    ),
    @NamedQuery(
        name = "Task.count",
        query = "select count(t) from Task as t"
    ),
    @NamedQuery(
        name = "Task.getAll",
        query = "select t from Task as t"
    )
})
@EntityListeners(TaskListener.class)
public class Task implements DomainObject<Task>,ScheduledTasks {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time_started",nullable = false)
    private Date timeStarted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time_finished")
    private Date timeFinished;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "countUser", column = @Column(name = "start_count_user")),
        @AttributeOverride(name = "countTweets", column = @Column(name = "start_count_tweets")),
        @AttributeOverride(name = "countHashTags", column = @Column(name = "start_count_hashtags")),
        @AttributeOverride(name = "countMedia", column = @Column(name = "start_count_media")),
        @AttributeOverride(name = "countMention", column = @Column(name = "start_count_mention")),
        @AttributeOverride(name = "countTickerSymbol", column = @Column(name = "start_count_tickersymbol")),
        @AttributeOverride(name = "countUrl", column = @Column(name = "start_count_url")),
        @AttributeOverride(name = "countUrlCache", column = @Column(name = "start_count_urlcache"))
    })
    private CountedEntities countedEntitiesAtStart;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "countUser", column = @Column(name = "done_count_user")),
        @AttributeOverride(name = "countTweets", column = @Column(name = "done_count_tweets")),
        @AttributeOverride(name = "countHashTags", column = @Column(name = "done_count_hashtags")),
        @AttributeOverride(name = "countMedia", column = @Column(name = "done_count_media")),
        @AttributeOverride(name = "countMention", column = @Column(name = "done_count_mention")),
        @AttributeOverride(name = "countTickerSymbol", column = @Column(name = "done_count_tickersymbol")),
        @AttributeOverride(name = "countUrl", column = @Column(name = "done_count_url")),
        @AttributeOverride(name = "countUrlCache", column = @Column(name = "done_count_urlcache"))
    })
    private CountedEntities countedEntitiesAtFinish;


    public void fetchUsersFromDefinedUserList(){
        this.taskType = TaskType.FETCH_USERS_FROM_DEFINED_USER_LIST;
    }

    public void updateUserProfilesFromMentions(){
        this.taskType = TaskType.UPDATE_USER_PROFILES_FROM_MENTIONS;
    }

    public void updateUserProfiles() {
        this.taskType = TaskType.UPDATE_USER_PROFILES;
    }

    public void updateTweets() {
        this.taskType = TaskType.UPDATE_TWEETS;
    }

    public void fetchTweetsFromTwitterSearch() {
        this.taskType = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
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
    public int compareTo(Task other) {
        return id.compareTo(other.getId());
    }

    @Override
    public boolean equals(Task o) {
        return id.equals(o.getId());
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(Date timeStarted) {
        this.timeStarted = timeStarted;
    }

    public Date getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(Date timeFinished) {
        this.timeFinished = timeFinished;
    }

    public CountedEntities getCountedEntitiesAtStart() {
        return countedEntitiesAtStart;
    }

    public void setCountedEntitiesAtStart(CountedEntities countedEntitiesAtStart) {
        this.countedEntitiesAtStart = countedEntitiesAtStart;
    }

    public CountedEntities getCountedEntitiesAtFinish() {
        return countedEntitiesAtFinish;
    }

    public void setCountedEntitiesAtFinish(CountedEntities countedEntitiesAtFinish) {
        this.countedEntitiesAtFinish = countedEntitiesAtFinish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (!id.equals(task.id)) return false;
        if (taskType != task.taskType) return false;
        return timeStarted.equals(task.timeStarted);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + taskType.hashCode();
        result = 31 * result + timeStarted.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", taskType=" + taskType +
            ", timeStarted=" + timeStarted +
            ", timeFinished=" + timeFinished +
            ", countedEntitiesAtStart=" + countedEntitiesAtStart.toString() +
            ", countedEntitiesAtFinish=" + countedEntitiesAtFinish.toString() +
            '}';
    }
}
