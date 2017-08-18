# [Twitterwall2](https://github.com/phasenraum2010/twitterwall2)

+ Twitterwall with [spring:boot](https://projects.spring.io/spring-boot/) for [heroku](https://heroku.com)
+ More Info: [https://phasenraum2010.github.io/twitterwall2/](https://phasenraum2010.github.io/twitterwall2/)

## Development 

### Repository
- Get Source Code: git clone [https://github.com/phasenraum2010/twitterwall2.git](https://github.com/phasenraum2010/twitterwall2.git)
- Project: [https://github.com/phasenraum2010/twitterwall2/projects/1](https://github.com/phasenraum2010/twitterwall2/projects/1)
- Issues: [https://github.com/phasenraum2010/twitterwall2/issues](https://github.com/phasenraum2010/twitterwall2/issues)

### heroku CLI
- heroku login
- heroku pg:psql --app {app-name}
- heroku logs -t --app {app-name}
- more: 
  - [https://devcenter.heroku.com/categories/command-line](https://devcenter.heroku.com/categories/command-line)
  - [https://devcenter.heroku.com/articles/heroku-cli](https://devcenter.heroku.com/articles/heroku-cli)


### Setup Test and Development on Localhost
- **Get Credentials from your Twitter-Account:** 
  - [Refer to "Manage your Apps" on Twitter's dev pages](https://dev.twitter.com/apps)
- **Edit your .profile with the Credentials from your Twitter-Account**
  - export DATABASE_URL=jdbc:postgresql://localhost:5432/postgres?user=postgres
  - export TWITTER_CONSUMER_KEY={Credentials from your Twitter-Account}
  - export TWITTER_CONSUMER_SECRET={Credentials from your Twitter-Account}
  - export TWITTER_ACCESS_TOKEN={Credentials from your Twitter-Account}
  - export TWITTER_ACCESS_TOKEN_SECRET={Credentials from your Twitter-Account}
- **Edit app behaviour**
  - export TWITTERWALL_SHOW_USERS_MENU=true
  - export TWITTERWALL_CONTEXT_TEST=true
  - export TWITTER_PAGE_SIZE=500
  - export TWITTER_FETCH_TESTDATA=false
  - export TWITTERWALL_WAIT_FOR_TEST=120000
  - export TWITTERWALL_URL_TEST_DATA_VERBOSE=false
  - export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_TWEETS=true
  - export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS=true
  - export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS_FROM_MENTION=true
  - export TWITTERWALL_SCHEDULER_ALLOW_SEARCH=true
  - export TWITTERWALL_SCHEDULER_HEROKU_DB_LIMIT=false
  - export TWITTERWALL_SCHEDULER_USER_LIST_ALLOW=true
- **Edit Tasks / Use Cases** 
  - export TWITTER_SEARCH_TERM=#hibernate OR #java OR #TYPO3
  - export TWITTERWALL_INFO_WEBPAGE=https://github.com/phasenraum2010/twitterwall2
  - export TWITTERWALL_THEME=typo3
  - export TWITTERWALL_APP_NAME=Twitterwall
  - export TWITTERWALL_INFO_IMPRINT_SCREEN_NAME=port80guru
  - export TWITTERWALL_GOOGLE_ANALYTICS_ID=TWITTERWALL_GOOGLE_ANALYTICS_ID
  - export TWITTERWALL_SCHEDULER_USER_LIST_NAME=test-typo3-hibernate-java
  - export TWITTERWALL_LOGIN_USERNAME=admin
  - export TWITTERWALL_LOGIN_PASSWORD=password
  
- run with: **mvn clean spring-boot:run**

## Contribute


### Fork, patch and contribute code

Feel free to fork Twitterwall [Git repository at GitHub][twitterwall-github] for your own use and
updates.

Contribute your fixes and new features back to the main codebase using
[GitHub pull requests][github-pull-req].

[twitterwall-github]: http://github.com/andriusvelykis/reflow-maven-skin/
[github-pull-req]: http://help.github.com/articles/using-pull-requests

### Issues (bug and feature tracker)

Please report any bugs found, feature requests or other issues on
[Twitterwall GitHub tracker][twitterwall-issues].

When creating a new issue, try following [necolas's guidelines][issue-guidelines].

[twitterwall-issues]: https://github.com/phasenraum2010/twitterwall2/issues
[issue-guidelines]: http://github.com/necolas/issue-guidelines/#readme

### Support

Let me know if you are using Twitterwall. I may get around to creating a showcase page listing user sites.

[Contact me][av-site] if you have questions about Twitterwall, or just like to say something about it.
If you _really really_ like it and want to support the author, I will be glad to
[accept a small donation][donate].

[av-site]: https://twitter.com/ThomasWoehlke
[donate]: https://www.paypal.me/ThomasWoehlke

### Author  

**Thomas Wöhlke**
+ Twitter: [https://twitter.com/ThomasWoehlke](https://twitter.com/ThomasWoehlke)
+ Github: [https://github.com/phasenraum2010](https://github.com/phasenraum2010)
+ Blog: [https://thomas-woehlke.blogspot.de](https://thomas-woehlke.blogspot.de)

### Acknowledgements

+ Twitterwall Frontend is built on [Bootstrap][bootstrap].
+ These include icons from [Glyphicons][glyphicons] and web fonts from [Google][webfonts].
+ JavaScript goodies with [jQuery][jquery].
+ More icons by [fontawesome][fontawesome].
+ Twitterwall Backend is made with [Java][java] and [Spring-Boot][spring-boot].
+ Database [PostgreSQL][postgresql] and jdbc by [PostgreSQL-JDBC][postgresql-jdbc].
+ Running on Cloud-Service [Heroku][heroku] with [Heroku PostegreSQL][postgresql-heroku].

[java]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[fontawesome]: http://fontawesome.io/icons/
[postgresql-jdbc]: https://jdbc.postgresql.org/
[postgresql]: https://www.postgresql.org/
[heroku]: https://heroku.com/
[postgresql-heroku]: https://devcenter.heroku.com/categories/heroku-postgres/
[spring-boot]: https://projects.spring.io/spring-boot/
[bootstrap]: http://getbootstrap.com/
[glyphicons]: http://glyphicons.com/
[webfonts]: http://www.google.com/webfonts/
[jquery]: http://jquery.org

### Copyright and license

&copy; 2017 Thomas Wöhlke

Licensed under the **Apache License, Version 2.0**

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Except where otherwise noted, Documentation of this work is licensed under 
[Creative Commons CC BY-ND 3.0](http://creativecommons.org/licenses/by-nd/3.0/)










