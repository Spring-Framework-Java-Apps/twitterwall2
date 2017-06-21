package org.woehlke.twitterwall.process;

/**
 * Created by tw on 19.06.17.
 */
public interface ScheduledTasksFacade {

    void fetchTweetsFromTwitterSearch();

    void fetchFollowersFromTwitter();

    void fetchFriendsFromTwitter();
    
    long ID_TWITTER_TO_FETCH_FOR_TWEET_TEST[] = {
            876329508009279488L,
            876356335784394752L,
            876676270913880066L,
            876566077836337152L,
            876563676395962368L,
            876514968933478400L,
            876514568671023104L,
            876513930478313472L,
            876510758632386563L,
            876496934676180992L
    };

    long ID_TWITTER_TO_FETCH_FOR_PROFILE_CONTROLLER_TEST[] = {
            876433563561938944L, // t3c_berlin
            876456051016597504L, // Codemonkey1988
            876420491329892354L, // Walter_kran
            876425220147564544L, // tobenschmidt
            876819177746649088L, // Oliver1973
            876514968933478400L, // wowa_TYPO3
            876441863191920641L, // dirscherl17
            876441015523192832L, // Markus306
            876440419416109056L  // mattLefaux
    };
}
