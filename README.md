# twitterwall2
Twitterwall with spring:boot for heroku

#Repository:
- git clone https://github.com/phasenraum2010/twitterwall2.git
- Project: https://github.com/phasenraum2010/twitterwall2/projects/1
- Issues: https://github.com/phasenraum2010/twitterwall2/issues

#heroku CLI
- heroku login
- heroku pg:psql --app {app-name}
- heroku logs -t --app {app-name}

#Setup Testing on Localhost
- Get Credentials from your Twitter-Account: Refer to "Manage your Apps" on Twitter's dev pages: https://dev.twitter.com/apps
- Edit your .profile with the Credentials from your Twitter-Account
  - export JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/postgres?user=postgres
  - export TWITTER_CONSUMER_KEY={Credentials from your Twitter-Account}
  - export TWITTER_CONSUMER_SECRET={Credentials from your Twitter-Account}
  - export TWITTER_ACCESS_TOKEN={Credentials from your Twitter-Account}
  - export TWITTER_ACCESS_TOKEN_SECRET={Credentials from your Twitter-Account}
  - export TWITTER_SEARCH_TERM='#t3cb'
  - export TWITTERWALL_FRONTEND_MAX_RESULTS=60
  - export TWITTERWALL_APP_NAME='TYPO3 Camp Berlin 2017'
  - export TWITTERWALL_SHOW_USERS_MENU=true
  - export TWITTER_PAGE_SIZE=500
  - export TWITTER_SEARCH_SINCE_ID={TWITTER STATUS ID}
  - export TWITTER_SEARCH_MAX_ID={TWITTER STATUS ID}
  - export TWITTER_FETCH_TESTDATA=true
  - export TWITTERWALL_WAIT_FOR_TEST=20000
  - export TWITTERWALL_URL_TEST_DATA_VERBOSE=true
  - export TWITTERWALL_INFO_WEBPAGE=http://lndw17.de/
  - export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_TWEETS=true
  - export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS=true
  - export TWITTERWALL_SCHEDULER_ALLOW_SEARCH=true
  - export TWITTERWALL_THEME=lndw17
- run with: mvn clean spring-boot:run


&copy; 2017 Thomas Wöhlke


