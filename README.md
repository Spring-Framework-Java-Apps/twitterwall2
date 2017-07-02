# [Twitterwall2]( https://github.com/phasenraum2010/twitterwall2 )

Twitterwall with [spring:boot]( https://projects.spring.io/spring-boot/ ) for [heroku]( https://heroku.com )

## Development

### Repository:
- Get Source Code: git clone https://github.com/phasenraum2010/twitterwall2.git
- Project: https://github.com/phasenraum2010/twitterwall2/projects/1
- Issues: https://github.com/phasenraum2010/twitterwall2/issues

### heroku CLI
- heroku login
- heroku pg:psql --app {app-name}
- heroku logs -t --app {app-name}

### Setup Testing on Localhost
- Get Credentials from your Twitter-Account: [Refer to "Manage your Apps" on Twitter's dev pages](https://dev.twitter.com/apps)
- Edit your .profile with the Credentials from your Twitter-Account
  - export JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/postgres?user=postgres
  - export TWITTER_CONSUMER_KEY={Credentials from your Twitter-Account}
  - export TWITTER_CONSUMER_SECRET={Credentials from your Twitter-Account}
  - export TWITTER_ACCESS_TOKEN={Credentials from your Twitter-Account}
  - export TWITTER_ACCESS_TOKEN_SECRET={Credentials from your Twitter-Account}
- Edit app behaviour
  - export TWITTERWALL_FRONTEND_MAX_RESULTS=60
  - export TWITTERWALL_SHOW_USERS_MENU=true
  - export TWITTER_PAGE_SIZE=500
  - export TWITTER_FETCH_TESTDATA=true
  - export TWITTERWALL_WAIT_FOR_TEST=20000
  - export TWITTERWALL_URL_TEST_DATA_VERBOSE=true
  - export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_TWEETS=false
  - export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS=false
  - export TWITTERWALL_SCHEDULER_ALLOW_SEARCH=true
- Edit Use Case  
  - export TWITTER_SEARCH_TERM='#hibernate OR #java OR #TYPO3'
  - export TWITTERWALL_INFO_WEBPAGE=https://github.com/phasenraum2010/twitterwall2
  - export TWITTERWALL_THEME=typo3
  - export TWITTERWALL_APP_NAME='Twitterwall'
  - export TWITTERWALL_INFO_IMPRINT_SCREEN_NAME=port80guru
- run with: mvn clean spring-boot:run


## Issue Tracker

- Have a bug or a feature request? 
- Please create an issue here on GitHub that conforms with [necolas's guidelines](http://github.com/necolas/issue-guidelines).
- Issues: https://github.com/phasenraum2010/twitterwall2/issues


## Contributing

Fork the repository and submit pull requests.
+ https://github.com/phasenraum2010/twitterwall2


## Author

**Thomas Wöhlke**

+ Twitter: https://twitter.com/ThomasWoehlke
+ Github: https://github.com/phasenraum2010
+ Blog: https://thomas-woehlke.blogspot.de



## Copyright and license

&copy; 2017 Thomas Wöhlke

Licensed under the GNU General Public License Version 2.0 (the "License");
you may not use this work except in compliance with the License.
You may obtain a copy of the License in the LICENSE file, or at:

   http://www.gnu.org/licenses/old-licenses/gpl-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.





