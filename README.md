# twitterwall2
Twitterwall with spring:boot for heroku

#Repository:
- git clone https://github.com/phasenraum2010/twitterwall2.git
- Project: https://github.com/phasenraum2010/twitterwall2/projects/1
- Issues: https://github.com/phasenraum2010/twitterwall2/issues

#Development Docu
- Heroku
  - https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku
  - https://blog.codecentric.de/2015/10/spring-boot-anwendungen-bei-heroku-deployen/
  - https://exampledriven.wordpress.com/2016/11/04/spring-boot-heroku-example/
- Spring Boot  
  - https://github.com/spring-projects/spring-boot/tree/v1.5.3.RELEASE/spring-boot-starters
  - http://docs.spring.io/spring-boot/docs/1.5.3.RELEASE/reference/htmlsingle/#using-boot
  - https://spring.io/guides/gs/spring-boot/
  - https://www.frank-rahn.de/spring-boot-webanwendung-die-ersten-schritte-tutorial/
  - https://github.com/frank-rahn/microservices
- Thymeleaf 
  - http://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html


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
  - export TWITTER_SEARCH_SINCE_ID
  - export TWITTER_SEARCH_MAX_ID
  - export TWITTER_FETCH_TESTDATA=false
- run with: mvn clean spring-boot:run


&copy; 2017 Thomas Wöhlke


