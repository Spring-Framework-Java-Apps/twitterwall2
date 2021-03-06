#!/bin/sh

echo "------------------------------------------"
export TWITTERWALL_CONTEXT_TEST=true
export TWITTERWALL_FRONTEND_MAX_RESULTS=60
export TWITTER_PAGE_SIZE=500
export TWITTER_FETCH_TESTDATA=false
export TWITTERWALL_WAIT_FOR_TEST=120000
export TWITTERWALL_URL_TEST_DATA_VERBOSE=true
export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_TWEETS=false
export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS=false
export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS_FROM_MENTION=true
export TWITTERWALL_SCHEDULER_ALLOW_SEARCH=true
export TWITTER_SEARCH_TERM='#hibernate OR #java OR #TYPO3'
export TWITTERWALL_INFO_WEBPAGE='https://github.com/phasenraum2010/twitterwall2'
export TWITTERWALL_THEME='typo3'
export TWITTERWALL_APP_NAME='Twitterwall'
export TWITTERWALL_INFO_IMPRINT_SCREEN_NAME='port80guru'
export TWITTERWALL_GOOGLE_ANALYTICS_ID='GOOGLE_ANALYTICS_ID'
export TWITTERWALL_SCHEDULER_HEROKU_DB_LIMIT=true
export TWITTERWALL_SCHEDULER_USER_LIST_NAME=''
export TWITTERWALL_SCHEDULER_USER_LIST_ALLOW=true

echo "------------------------------------------"
echo "TWITTERWALL_CONTEXT_TEST $TWITTERWALL_CONTEXT_TEST"
echo "TWITTERWALL_FRONTEND_MAX_RESULTS $TWITTERWALL_FRONTEND_MAX_RESULTS"
echo "TWITTER_PAGE_SIZE $TWITTER_PAGE_SIZE"
echo "TWITTER_FETCH_TESTDATA $TWITTER_FETCH_TESTDATA"
echo "TWITTERWALL_WAIT_FOR_TEST $TWITTERWALL_WAIT_FOR_TEST"
echo "TWITTERWALL_URL_TEST_DATA_VERBOSE $TWITTERWALL_URL_TEST_DATA_VERBOSE"
echo "TWITTERWALL_SCHEDULER_ALLOW_UPDATE_TWEETS $TWITTERWALL_SCHEDULER_ALLOW_UPDATE_TWEETS"
echo "TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS $TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS"
echo "TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS_FROM_MENTION $TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS_FROM_MENTION"
echo "TWITTERWALL_SCHEDULER_ALLOW_SEARCH $TWITTERWALL_SCHEDULER_ALLOW_SEARCH"
echo "TWITTER_SEARCH_TERM $TWITTER_SEARCH_TERM"
echo "TWITTERWALL_INFO_WEBPAGE $TWITTERWALL_INFO_WEBPAGE"
echo "TWITTERWALL_THEME $TWITTERWALL_THEME"
echo "TWITTERWALL_APP_NAME $TWITTERWALL_APP_NAME"
echo "TWITTERWALL_INFO_IMPRINT_SCREEN_NAME $TWITTERWALL_INFO_IMPRINT_SCREEN_NAME"
echo "TWITTERWALL_GOOGLE_ANALYTICS_ID $TWITTERWALL_GOOGLE_ANALYTICS_ID"
echo "TWITTERWALL_SCHEDULER_HEROKU_DB_LIMIT $TWITTERWALL_SCHEDULER_HEROKU_DB_LIMIT"
echo "------------------------------------------"
