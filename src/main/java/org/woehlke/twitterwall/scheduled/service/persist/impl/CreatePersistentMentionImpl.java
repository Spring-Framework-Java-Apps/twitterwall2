package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.application.TaskService;
import org.woehlke.twitterwall.oodm.service.entities.MentionService;
import org.woehlke.twitterwall.scheduled.service.facade.StoreTwitterProfileForProxyMentionForUser;
import org.woehlke.twitterwall.scheduled.service.persist.CreatePersistentMention;

/**
 * Created by tw on 14.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class CreatePersistentMentionImpl implements CreatePersistentMention {

    private static final Logger log = LoggerFactory.getLogger(CreatePersistentMentionImpl.class);

    private final MentionService mentionService;

    private final StoreTwitterProfileForProxyMentionForUser storeTwitterProfileForProxyMentionForUser;

    private final TaskService taskService;

    @Autowired
    public CreatePersistentMentionImpl(MentionService mentionService, StoreTwitterProfileForProxyMentionForUser storeTwitterProfileForProxyMentionForUser, TaskService taskService) {
       this.mentionService = mentionService;
        this.storeTwitterProfileForProxyMentionForUser = storeTwitterProfileForProxyMentionForUser;
        this.taskService = taskService;
    }

    /**
     *   Creates a Mention and a User, or fetch an axisting one from the Database.
     *
     *    <pre>
     *    Algorithmus: User anlegen
     *    1. gibt es einen User mit diesem ScreenName?
     *    2. Falls Nein? -&gt; hole TwitterProfile für ScreenName über die Twitter-API
     *       2.1 Gibt es von der Twitter-API ein TwitterProfile?
     *       2.2 Falls ja: 1. Persistierungs-Prozess dafür durchführen;
     *                     2. die idTwitter des Users im Mention-Objekt speichern.
     *                     3. geschickt speichern, eigene unique-idTwitter erstellen.
     *                     4. das persistierte Mention Objekt zurück geben
     *       2.3 Falls nein: entsprechenden Wert zurück geben (null?)
     *     3. Mention anlegen: geschickt speichern, eigene unique-idTwitter erstellen:
     *     3.1 gibt es eine Mention mit diesem ScreenName?
     *     3.2 Falls Nein? -&gt; hole die höchste verwendete idTwitter und speichere neues Mention Objekt.
     *     </pre>
     *
     * @param mention Mention
     * @param task Task
     * @return mention - persistet and with a User, if there is one at Twitter
     */
    @Override
    public Mention getPersistentMentionAndUserFor(Mention mention, Task task) {
        String msg = "getPersistentMentionAndUserFor:";
        String screenName = mention.getScreenName();
        User foundUser = storeTwitterProfileForProxyMentionForUser.storeTwitterProfileForProxyMentionForUser(mention,task);
        if(foundUser.getScreenName().compareTo(mention.getScreenName())!=0){
            String eventMsg = msg + "KNOWN_BUG - ScreenName user: "+foundUser.getScreenName()+" mention: "+mention.getScreenName();
            log.warn(eventMsg);
            taskService.warn(task,eventMsg);
            mention.setScreenName(foundUser.getScreenName());
            screenName = foundUser.getScreenName();
        }
        if(foundUser != null){
            Mention persMention = null;
            try {
                Mention myFoundMention = mentionService.findByScreenName(screenName);
                myFoundMention.setUser(foundUser);
                myFoundMention.setIdTwitterOfUser(foundUser.getIdTwitter());
                persMention = mentionService.update(myFoundMention,task);
                log.debug(msg+" updated: "+persMention.toString());
            } catch (EmptyResultDataAccessException e){
                mention.setUser(foundUser);
                mention.setIdTwitterOfUser(foundUser.getIdTwitter());
                persMention = mentionService.createProxyMention(mention,task);
                log.debug(msg+" persisted: "+persMention.toString());
            }
            return persMention;
        } else {
            String eventMsg = msg+"ERROR: useful Persistent Mention expectet, but there is none!";
            taskService.error(task,eventMsg);
            log.error(eventMsg);
            return null;
        }
    }
}