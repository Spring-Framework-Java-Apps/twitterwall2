package org.woehlke.twitterwall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.scheduled.service.facade.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final ConfigTwitterwall configTwitterwall;

    private void logEnv(String msg){
        log.info("====================================================================");
        log.info(msg);
        log.info("====================================================================");
        log.info("twitterwall.scheduler.allowUpdateTweets = "+configTwitterwall.getScheduler().getAllowUpdateTweets());
        log.info("twitterwall.scheduler.allowUpdateUserProfiles = "+configTwitterwall.getScheduler().getAllowUpdateUserProfiles());
        log.info("twitterwall.scheduler.allowUpdateUserProfilesFromMention = "+configTwitterwall.getScheduler().getAllowUpdateUserProfilesFromMention());
        log.info("twitterwall.scheduler.allowFetchTweetsFromTwitterSearch = "+configTwitterwall.getScheduler().getAllowFetchTweetsFromTwitterSearch());
        log.info("twitterwall.scheduler.skipFortesting = "+configTwitterwall.getScheduler().getSkipFortesting());
        log.info("twitterwall.scheduler.herokuDbRowsLimit = "+configTwitterwall.getScheduler().getHerokuDbRowsLimit());
        log.info("twitterwall.scheduler.fetchUserList.name = "+configTwitterwall.getScheduler().getFetchUserList().getName());
        log.info("twitterwall.scheduler.fetchUserList.allow = "+configTwitterwall.getScheduler().getFetchUserList().getAllow());
        log.info("====================================================================");
    }

    private final FetchTweetsFromTwitterSearch fetchTweetsFromTwitterSearch;

    private final FetchUsersFromDefinedUserList fetchUsersFromDefinedUserList;

    private final UpdateTweets updateTweets;

    private final UpdateUserProfiles updateUserProfiles;

    private final UpdateUserProfilesFromMentions updateUserProfilesFromMentions;

    @Autowired
    public ScheduledTasks(ConfigTwitterwall configTwitterwall, FetchTweetsFromTwitterSearch fetchTweetsFromTwitterSearch, FetchUsersFromDefinedUserList fetchUsersFromDefinedUserList, UpdateTweets updateTweets, UpdateUserProfiles updateUserProfiles, UpdateUserProfilesFromMentions updateUserProfilesFromMentions) {
        this.configTwitterwall = configTwitterwall;
        this.fetchTweetsFromTwitterSearch = fetchTweetsFromTwitterSearch;
        this.fetchUsersFromDefinedUserList = fetchUsersFromDefinedUserList;
        this.updateTweets = updateTweets;
        this.updateUserProfiles = updateUserProfiles;
        this.updateUserProfilesFromMentions = updateUserProfilesFromMentions;
    }

    private final static long EINE_MINUTE = 60 * 1000;

    private final static long FUENF_MINUTEN = 5 * EINE_MINUTE;

    private final static long EINE_STUNDE = 60 * EINE_MINUTE;

    private final static long ZWOELF_STUNDEN = 12 * EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS = EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER = ZWOELF_STUNDEN;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_TWEETS = ZWOELF_STUNDEN;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER_BY_MENTION = EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_USER_LIST = ZWOELF_STUNDEN;

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS)
    public void fetchTweetsFromTwitterSearch() {
        String msg = "fetch Tweets From TwitterSearch ";
        logEnv(msg);
        if(configTwitterwall.getScheduler().getAllowUpdateTweets()  && !configTwitterwall.getScheduler().getSkipFortesting()){
            log.info("START "+msg+": The time is now {}", dateFormat.format(new Date()));
            try {
                this.fetchTweetsFromTwitterSearch.fetchTweetsFromTwitterSearch();
                log.info("DONE "+msg+" (OK)"+": The time is now {}", dateFormat.format(new Date()));
            } catch (RuntimeException e) {
                msg += " (RuntimeException) ";
                String eMesg = e.getMessage();
                e.printStackTrace();
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.error(msg +" : " + eMesg);
                log.error("NOT DONE "+msg+" (NOK)");;
            } catch (Exception e) {
                msg += " (Exception) ";
                String eMesg = e.getMessage();
                e.printStackTrace();
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.error(msg +" : " + eMesg);
                log.error("NOT DONE "+msg+" (NOK)");;
            }
        }
        logEnv(msg);
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_TWEETS)
    public void updateTweets() {
        String msg = "update Tweets ";
        logEnv(msg);
        if(configTwitterwall.getScheduler().getAllowUpdateTweets() && !configTwitterwall.getScheduler().getSkipFortesting()){
            log.info("START "+msg + ": The time is now {}", dateFormat.format(new Date()));
            try {
                this.updateTweets.updateTweets();
                log.info("DONE "+msg+" (OK)"+": The time is now {}", dateFormat.format(new Date()));
            } catch (RuntimeException e) {
                msg += " (RuntimeException) ";
                String eMsg = e.getMessage();
                log.warn(msg + e.getMessage());
                Throwable t = e.getCause();
                e.printStackTrace();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + eMsg);
                log.warn("NOT DONE "+msg+" (NOK) {}", dateFormat.format(new Date()));;
            } catch (Exception e) {
                msg += " (Exception) ";
                String eMsg = e.getMessage();
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + eMsg);
                log.warn("NOT DONE "+msg+" (NOK) {}", dateFormat.format(new Date()));
            }
        }
        logEnv(msg);
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER)
    public void updateUserProfiles() {
        String msg = "update User Profiles ";
        logEnv(msg);
        if(configTwitterwall.getScheduler().getAllowUpdateUserProfiles()  && !configTwitterwall.getScheduler().getSkipFortesting()) {
            log.info("START " + msg + ": The time is now {}", dateFormat.format(new Date()));
            try {
                //this.updateUserProfiles.updateUserProfiles();
                log.info("DONE " + msg + " (OK)" + ": The time is now {}", dateFormat.format(new Date()));
            } catch (RuntimeException e) {
                msg += " (RuntimeException) ";
                String eMsg = e.getMessage();
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + eMsg);
                log.warn(msg + " NOT DONE " + msg + " (NOK)  {}", dateFormat.format(new Date()));
            } catch (Exception e) {
                msg += " (Exception) ";
                String eMsg = e.getMessage();
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + eMsg);
                log.error(msg + " NOT DONE " + msg + " (NOK)  {}", dateFormat.format(new Date()));
            }
        }
        logEnv(msg);
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER_BY_MENTION)
    public void updateUserProfilesFromMentions(){
        String msg = "update User Profiles From Mentions";
        logEnv(msg);
        if(configTwitterwall.getScheduler().getAllowUpdateUserProfilesFromMention() && !configTwitterwall.getScheduler().getSkipFortesting()) {
            log.info("START " + msg + ": The time is now {}", dateFormat.format(new Date()));
            try {
                //this.updateUserProfilesFromMentions.updateUserProfilesFromMentions();
                log.info("DONE " + msg + " (OK)" + ": The time is now {}", dateFormat.format(new Date()));
            } catch (RuntimeException e) {
                msg += " (RuntimeException) ";
                String eMsg = e.getMessage();
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + eMsg);
                log.warn(msg + " NOT DONE " + msg + " (NOK) {}", dateFormat.format(new Date()));
            } catch (Exception e) {
                msg += " (Exception) ";
                String eMsg = e.getMessage();
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + eMsg);
                log.error(msg + " NOT DONE " + msg + " (NOK) {}", dateFormat.format(new Date()));
            }
        }
        logEnv(msg);
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_USER_LIST)
    public void fetchUsersFromDefinedUserList(){
        String msg = "fetch Users from Defined User List ";
        logEnv(msg);
        if(configTwitterwall.getScheduler().getFetchUserList().getAllow()  && !configTwitterwall.getScheduler().getSkipFortesting()) {
            log.info("START " + msg + ": The time is now {}", dateFormat.format(new Date()));
            try {
                this.fetchUsersFromDefinedUserList.fetchUsersFromDefinedUserList();
                log.info("DONE " + msg + " (OK)" + ": The time is now {}", dateFormat.format(new Date()));
            } catch (RuntimeException e) {
                msg += " (RuntimeException) ";
                String eMsg = e.getMessage();
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + eMsg);
                log.warn(msg + " NOT DONE " + msg + " (NOK) {}", dateFormat.format(new Date()));
            } catch (Exception e) {
                msg += " (Exception) ";
                String eMsg = e.getMessage();
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.error(msg + eMsg);
                log.error(msg + " NOT DONE " + msg + " (NOK) {}", dateFormat.format(new Date()));
            }
        }
        logEnv(msg);
    }
}
