set TWITTERWALL_FRONTEND_MAX_RESULTS=200
set TWITTERWALL_SHOW_USERS_MENU=true
set TWITTERWALL_CONTEXT_TEST=true
set TWITTER_PAGE_SIZE=50
set TWITTER_FETCH_TESTDATA=false
set TWITTERWALL_WAIT_FOR_TEST=120000
set TWITTERWALL_URL_TEST_DATA_VERBOSE=false
set TWITTERWALL_SCHEDULER_ALLOW_UPDATE_TWEETS=false
set TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS=false
set TWITTERWALL_SCHEDULER_ALLOW_UPDATE_USERS_FROM_MENTION=false
set TWITTERWALL_SCHEDULER_ALLOW_REMOVE_OLD_DATA_FROM_STORAGE=false
set TWITTERWALL_SCHEDULER_ALLOW_SEARCH=false
set TWITTER_SEARCH_TERM='#hibernate OR #java OR #TYPO3'
set TWITTERWALL_INFO_WEBPAGE=https://github.com/phasenraum2010/twitterwall2
set TWITTERWALL_THEME=typo3
set TWITTERWALL_APP_NAME=Twitterwall
set TWITTERWALL_INFO_IMPRINT_SCREEN_NAME=port80guru
set TWITTERWALL_GOOGLE_ANALYTICS_ID=TWITTERWALL_GOOGLE_ANALYTICS_ID
set TWITTERWALL_SCHEDULER_HEROKU_DB_LIMIT=false
set TWITTERWALL_SCHEDULER_USER_LIST_NAME=test-typo3-hibernate-java
set TWITTERWALL_SCHEDULER_USER_LIST_ALLOW=false
set TWITTERWALL_JPA_HIBERNATE_DDL_AUTO=update
@REM set TWITTERWALL_JPA_HIBERNATE_DDL_AUTO=create-drop
set TWITTERWALL_LOGIN_USERNAME=tw
set TWITTERWALL_LOGIN_PASSWORD=vbfvjdgar64r67tf7a46tf76rgtfgf7d6g

@REM psql -U twitterwall2test < etc/drop-tables.sql

@REM psql -c 'DROP DATABASE kandidatentest;' -U postgres
@REM psql -c 'DROP USER kandidatentest;' -U postgres
@REM psql -c 'DROP ROLE kandidatentest;' -U postgres
@REM psql -c "CREATE USER kandidatentest WITH PASSWORD 'kandidatentestpwd' LOGIN SUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;" -U postgres
@REM psql -c 'GRANT pg_signal_backend, postgres TO kandidatentest WITH ADMIN OPTION;' -U postgres
@REM psql -c "CREATE DATABASE kandidatentest WITH OWNER = kandidatentest TEMPLATE = template1 ENCODING = 'UTF8' LC_COLLATE = 'de_DE.UTF-8' LC_CTYPE = 'de_DE.UTF-8' CONNECTION LIMIT = -1;" -U postgres

psql -c 'select * from version();' -U twitterwall2test
psql -c '\l' -U postgres
psql -c '\dg' -U postgres
psql -c '\dn' -U postgres

mvnw install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
mvnw clean site site:deploy -Ptravis -Dtest=AlphaTopLevelSuiteIT  -B -V

exit 0
