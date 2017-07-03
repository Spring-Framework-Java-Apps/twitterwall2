package org.woehlke.twitterwall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.exceptions.common.TwitterwallException;
import org.woehlke.twitterwall.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.frontend.model.CountedEntities;
import org.woehlke.twitterwall.scheduled.ScheduledTasksFacade;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tw on 10.06.17.
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.scheduler.allowUpdateTweets}")
    private boolean allowUpdateTweets;

    @Value("${twitterwall.scheduler.allowUpdateUserProfiles}")
    private boolean allowUpdateUserProfiles;

    @Value("${twitterwall.scheduler.allowUpdateUserProfilesFromMention}")
    private boolean allowUpdateUserProfilesFromMention;

    @Value("${twitterwall.scheduler.allowFetchTweetsFromTwitterSearch}")
    private boolean allowFetchTweetsFromTwitterSearch;

    @Value("${twitterwall.scheduler.skipFortesting}")
    private boolean skipFortesting;

    @Value("${twitterwall.scheduler.herokuDbRowsLimit}")
    private boolean herokuDbRowsLimit;

    @Autowired
    public ScheduledTasks(ScheduledTasksFacade scheduledTasksFacade) {
        this.scheduledTasksFacade = scheduledTasksFacade;
    }

    private final ScheduledTasksFacade scheduledTasksFacade;

    private final static long EINE_MINUTE = 60 * 1000;

    private final static long FUENF_MINUTEN = 5 * EINE_MINUTE;

    private final static long EINE_STUNDE = 60 * EINE_MINUTE;

    private final static long ZWOELF_STUNDEN = 12 * EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS = EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER = ZWOELF_STUNDEN;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_TWEETS = ZWOELF_STUNDEN;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER_BY_MENTION = EINE_STUNDE;

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS)
    public void fetchTweetsFromTwitterSearch() {
        if(allowFetchTweetsFromTwitterSearch  && !skipFortesting){
            String msg = "fetch Tweets From TwitterSearch ";
            log.info("START "+msg+": The time is now {}", dateFormat.format(new Date()));
            try {
                CountedEntities countedEntities = this.scheduledTasksFacade.fetchTweetsFromTwitterSearch();
                log.info("DONE "+msg+" (OK)"+": The time is now {}", dateFormat.format(new Date()));
            } catch (TwitterApiException e) {
                log.error(msg + e.getMessage());
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn("NOT DONE "+msg+" (NOK)");
            } catch (TwitterwallException e) {
                log.error(msg + e.getMessage());
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn("NOT DONE "+msg+" (NOK)");
            } catch (RuntimeException e) {
                log.error(msg + e.getMessage());
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn("NOT DONE "+msg+" (NOK)");;
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.error("NOT DONE "+msg+" (NOK)");
            }
        }
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_TWEETS)
    public void updateTweets() {
        if(allowUpdateTweets  && !skipFortesting){
            String msg = "update Tweets ";
            log.info("START "+msg + ": The time is now {}", dateFormat.format(new Date()));
            try {
                CountedEntities countedEntities = this.scheduledTasksFacade.updateTweets();
                log.info("DONE "+msg+" (OK)"+": The time is now {}", dateFormat.format(new Date()));
            } catch (TwitterApiException e) {
                log.error(msg + e.getMessage());
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn("NOT DONE "+msg+" (NOK)");
            } catch (TwitterwallException e) {
                log.error(msg + e.getMessage());
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn("NOT DONE "+msg+" (NOK)");
            } catch (RuntimeException e) {
                log.warn(msg + e.getMessage());
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn("NOT DONE "+msg+" (NOK)");;
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.error("NOT DONE "+msg+" (NOK)");
            }
        }
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER)
    public void updateUserProfiles() {
        if(allowUpdateUserProfiles  && !skipFortesting) {
            String msg = "update User Profiles ";
            log.info("START " + msg + ": The time is now {}", dateFormat.format(new Date()));
            try {
                CountedEntities countedEntities = this.scheduledTasksFacade.updateUserProfiles();
                log.info("DONE " + msg + " (OK)" + ": The time is now {}", dateFormat.format(new Date()));
            } catch (TwitterApiException e) {
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + e.getMessage());
                log.warn(msg + " NOT DONE " + msg + " (NOK)");
            } catch (TwitterwallException e) {
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + e.getMessage());
                log.warn(msg + " NOT DONE " + msg + " (NOK)");
            } catch (RuntimeException e) {
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + e.getMessage());
                log.warn(msg + " NOT DONE " + msg + " (NOK)");
            } catch (Exception e) {
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.error(msg + e.getMessage());
                log.error(msg + " NOT DONE " + msg + " (NOK)");
            }
        }
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER_BY_MENTION)
    public void updateUserProfilesFromMentions(){
        if(allowUpdateUserProfilesFromMention  && !skipFortesting) {
            String msg = "update User Profiles From Mentions";
            log.info("START " + msg + ": The time is now {}", dateFormat.format(new Date()));
            try {
                CountedEntities countedEntities = this.scheduledTasksFacade.updateUserProfilesFromMentions();
                log.info("DONE " + msg + " (OK)" + ": The time is now {}", dateFormat.format(new Date()));
            } catch (TwitterApiException e) {
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + e.getMessage());
                log.warn(msg + " NOT DONE " + msg + " (NOK)");
            } catch (TwitterwallException e) {
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + e.getMessage());
                log.warn(msg + " NOT DONE " + msg + " (NOK)");
            } catch (RuntimeException e) {
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.warn(msg + e.getMessage());
                log.warn(msg + " NOT DONE " + msg + " (NOK)");
            } catch (Exception e) {
                Throwable t = e.getCause();
                while(t != null){
                    log.warn(msg + t.getMessage());
                    t = t.getCause();
                }
                log.error(msg + e.getMessage());
                log.error(msg + " NOT DONE " + msg + " (NOK)");
            }
        }
    }

}
