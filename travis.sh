#!/opt/local/bin/bash

export TWITTERWALL_FRONTEND_MAX_RESULTS=200
export TWITTERWALL_SHOW_USERS_MENU=true
export TWITTERWALL_CONTEXT_TEST=true
export TWITTER_PAGE_SIZE=50
export TWITTER_FETCH_TESTDATA=false
export TWITTERWALL_WAIT_FOR_TEST=120000
export TWITTERWALL_URL_TEST_DATA_VERBOSE=false
export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_TWEETS=false
export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS=false
export TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS_FROM_MENTION=false
export TWITTERWALL_SCHEDULER_ALLOW_REMOVE_OLD_DATA_FROM_STORAGE=false
export TWITTERWALL_SCHEDULER_ALLOW_SEARCH=false
export TWITTER_SEARCH_TERM='#hibernate OR #java OR #TYPO3'
export TWITTERWALL_INFO_WEBPAGE=https://github.com/phasenraum2010/twitterwall2
export TWITTERWALL_THEME=typo3
export TWITTERWALL_APP_NAME=Twitterwall
export TWITTERWALL_INFO_IMPRINT_SCREEN_NAME=port80guru
export TWITTERWALL_GOOGLE_ANALYTICS_ID=TWITTERWALL_GOOGLE_ANALYTICS_ID
export TWITTERWALL_SCHEDULER_HEROKU_DB_LIMIT=false
export TWITTERWALL_SCHEDULER_USER_LIST_NAME=test-typo3-hibernate-java
export TWITTERWALL_SCHEDULER_USER_LIST_ALLOW=false
export TWITTERWALL_JPA_HIBERNATE_DDL_AUTO=update
#export TWITTERWALL_JPA_HIBERNATE_DDL_AUTO=create-drop
export TWITTERWALL_LOGIN_USERNAME=tw
export TWITTERWALL_LOGIN_PASSWORD=vbfvjdgar64r67tf7a46tf76rgtfgf7d6g

#psql -U twitterwall2test < etc/drop-tables.sql

#psql -c 'DROP DATABASE kandidatentest;' -U postgres
#psql -c 'DROP USER kandidatentest;' -U postgres
#psql -c 'DROP ROLE kandidatentest;' -U postgres
#psql -c "CREATE USER kandidatentest WITH PASSWORD 'kandidatentestpwd' LOGIN SUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;" -U postgres
#psql -c 'GRANT pg_signal_backend, postgres TO kandidatentest WITH ADMIN OPTION;' -U postgres
#psql -c "CREATE DATABASE kandidatentest WITH OWNER = kandidatentest TEMPLATE = template1 ENCODING = 'UTF8' LC_COLLATE = 'de_DE.UTF-8' LC_CTYPE = 'de_DE.UTF-8' CONNECTION LIMIT = -1;" -U postgres

psql -c 'select * from version();' -U twitterwall2test
psql -c '\l' -U postgres
psql -c '\dg' -U postgres
psql -c '\dn' -U postgres

./mvnw install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
./mvnw clean site site:deploy -Ptravis -Dtest=AlphaTopLevelSuiteIT  -B -V

exit 0