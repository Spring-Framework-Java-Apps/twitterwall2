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

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }

    @Override
    public int compareTo(Task o) {
        return 0;
    }

    @Override
    public boolean equals(Task o) {
        return false;
    }

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Temporal(DATETIME)
    @Column(name="time_started",nullable = false)
    private Date timeStarted;

    @Temporal(DATETIME)
    @Column(name="time_finished")
    private Date timeFinished;


    @Embedded
    private CountedEntities countedEntitiesAtStart;

    @Embedded
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



}
