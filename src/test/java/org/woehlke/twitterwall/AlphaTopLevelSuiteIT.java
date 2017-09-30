package org.woehlke.twitterwall;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.woehlke.twitterwall.backend.mq.tasks.TaskStartFireAndForgetTestImpl;
import org.woehlke.twitterwall.backend.mq.tasks.TaskStartTestImpl;
import org.woehlke.twitterwall.backend.service.remote.TwitterUrlServiceTest;
import org.woehlke.twitterwall.frontend.controller.*;
import org.woehlke.twitterwall.oodm.model.*;
import org.woehlke.twitterwall.oodm.service.*;

@Suite.SuiteClasses({


        HashTagTest.class,
        MediaTest.class,
        MentionTest.class,
        TaskHistoryTest.class,
        TaskTest.class,
        TickerSymbolTest.class,
        TweetTest.class,
        UrlTest.class,
        UserDescriptionTest.class,
        UserListTest.class,
        UserTest.class,

        HashTagServiceTest.class,
        MediaServiceTest.class,
        MentionServiceTest.class,
        TaskHistoryServiceTest.class,
        TaskServiceTest.class,
        TickerSymbolServiceTest.class,
        TweetServiceTest.class,
        UrlServiceTest.class,
        UserListServiceTest.class,
        UserServiceTest.class,

        ApplicationControllerTest.class,
        CountedEntitiesControllerTest.class,
        HashTagControllerTest.class,
        ImprintControllerTest.class,
         /*
        LoginControllerTest.class,
        */
        MediaControllerTest.class,
        MentionControllerTest.class,
        /*
        TaskControllerTest.class,
        TaskHistoryControllerTest.class,
        */
        TickerSymbolControllerTest.class,
        TweetControllerTest.class,
        UrlControllerTest.class,
        UserControllerTest.class,
        UserListControllerTest.class,

 /*
        TwitterUrlServiceTest.class,

        TaskStartFireAndForgetTestImpl.class,
        TaskStartTestImpl.class,
        */

        CronJobsTest.class
})
@RunWith(Suite.class)
public class AlphaTopLevelSuiteIT {
}
