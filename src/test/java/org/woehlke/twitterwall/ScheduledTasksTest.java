package org.woehlke.twitterwall;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class ScheduledTasksTest {

    @Autowired
    private ScheduledTasks scheduledTasks;

    public void fetchTweetsFromTwitterSearch() throws Exception {
        scheduledTasks.fetchTweetsFromTwitterSearch();
    }

    @Test
    public void updateTweets() throws Exception {
        scheduledTasks.updateTweets();
    }

    @Test
    public void updateUserProfiles() throws Exception {
        scheduledTasks.updateUserProfiles();
    }

    @Test
    public void updateUserProfilesFromMentions() throws Exception {
        scheduledTasks.updateUserProfilesFromMentions();
    }

    @Test
    public void fetchUsersFromDefinedUserList() throws Exception {
        scheduledTasks.fetchUsersFromDefinedUserList();
    }

    @Test
    public void removeOldDataFromStorage() throws Exception {
        scheduledTasks.removeOldDataFromStorage();
    }
}
