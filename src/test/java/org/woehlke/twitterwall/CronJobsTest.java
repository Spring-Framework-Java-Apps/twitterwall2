package org.woehlke.twitterwall;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CronJobsTest {

    @Autowired
    private CronJobs cronJobs;

    @Test
    public void test001createImprintUserAsync() throws Exception {
        cronJobs.createImprintUserAsync();
    }

    @Test
    public void test002fetchTweetsFromTwitterSearch() throws Exception {
        cronJobs.fetchTweetsFromTwitterSearch();
    }

    @Test
    public void test003updateTweets() throws Exception {
        cronJobs.updateTweets();
    }

    @Test
    public void test004updateUserProfiles() throws Exception {
        cronJobs.updateUserProfiles();
    }

    @Test
    public void test005updateUserProfilesFromMentions() throws Exception {
        cronJobs.updateUserProfilesFromMentions();
    }

    @Test
    public void test006fetchUsersFromDefinedUserList() throws Exception {
        cronJobs.fetchUsersFromDefinedUserList();
    }

    @Test
    public void test007removeOldDataFromStorage() throws Exception {
        cronJobs.removeOldDataFromStorage();
    }

    @Test
    public void test008fetchFollower() throws Exception {
        cronJobs.fetchFollower();
    }

    @Test
    public void test009getHomeTimeline() throws Exception {
        cronJobs.getHomeTimeline();
    }

    @Test
    public void test010getUserTimeline() throws Exception {
        cronJobs.getUserTimeline();
    }

    @Test
    public void test011getMentions() throws Exception {
        cronJobs.getMentions();
    }

    @Test
    public void test012getFavorites() throws Exception {
        cronJobs.getFavorites();
    }

    @Test
    public void test013getRetweetsOfMe() throws Exception {
        cronJobs.getRetweetsOfMe();
    }

    @Test
    public void test014getLists() throws Exception {
        cronJobs.getLists();
    }
}
