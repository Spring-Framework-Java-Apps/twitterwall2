package org.woehlke.twitterwall;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class CronJobsTest {

    @Autowired
    private CronJobs cronJobs;

    @Test
    public void createImprintUserAsync() throws Exception {
        cronJobs.createImprintUserAsync();
    }

    @Test
    public void fetchTweetsFromTwitterSearch() throws Exception {
        cronJobs.fetchTweetsFromTwitterSearch();
    }

    @Test
    public void updateTweets() throws Exception {
        cronJobs.updateTweets();
    }

    @Test
    public void updateUserProfiles() throws Exception {
        cronJobs.updateUserProfiles();
    }

    @Test
    public void updateUserProfilesFromMentions() throws Exception {
        cronJobs.updateUserProfilesFromMentions();
    }

    @Test
    public void fetchUsersFromDefinedUserList() throws Exception {
        cronJobs.fetchUsersFromDefinedUserList();
    }

    @Test
    public void removeOldDataFromStorage() throws Exception {
        cronJobs.removeOldDataFromStorage();
    }

    @Test
    public void fetchFollower() throws Exception {
        cronJobs.fetchFollower();
    }

    @Test
    public void getHomeTimeline() throws Exception {
        cronJobs.getHomeTimeline();
    }

    @Test
    public void getUserTimeline() throws Exception {
        cronJobs.getUserTimeline();
    }

    @Test
    public void getMentions() throws Exception {
        cronJobs.getMentions();
    }

    @Test
    public void getFavorites() throws Exception {
        cronJobs.getFavorites();
    }

    @Test
    public void getRetweetsOfMe() throws Exception {
        cronJobs.getRetweetsOfMe();
    }

    @Test
    public void getLists() throws Exception {
        cronJobs.getLists();
    }
}
