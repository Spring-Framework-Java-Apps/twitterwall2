package org.woehlke.twitterwall.oodm.entities;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.test.UserServiceTest;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;


/**
 * Created by tw on 22.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=false)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class UserTest {

    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceTest userServiceTest;

    @Autowired
    private TwitterProperties twitterProperties;

    @Ignore
    @Commit
    @Test
    public void fetchTweetsFromTwitterSearchTest() {
        String msg ="getAllDescriptionsTest";
        log.info(msg+"------------------------------------------------");
        //userServiceTest.createTestData();
        Assert.assertTrue(true);
        log.info(msg+"------------------------------------------------");
    }

    @Ignore
    @Commit
    @Test
    public void getAllDescriptionsTest() {
        String msg = "getAllDescriptionsTest";
        log.info(msg+"------------------------------------------------");
        boolean hasNext;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        do {
            Page<String> descriptions = userService.getAllDescriptions(pageRequest);
            hasNext = descriptions.hasNext();
            long totalNumber = descriptions.getTotalElements();
            int number = descriptions.getNumber();
            log.info(msg+"found "+number+" descriptions (total: "+totalNumber+")");
            for(String description:descriptions){
                log.info(msg+"description: "+description);
            }
            pageRequest = pageRequest.next();
        } while (hasNext);
        String message = "userService.findAllDescriptions(); ";
        Assert.assertTrue(message,true);
        log.info(msg+"------------------------------------------------");
    }

    private static String descriptions[] = {
            "Webentwickler @cron_eu, Stuttgart #T3Rookies #TYP",
            "Neos, Flow and TYPO3 development @portachtzig_ Berlin",
            "16.–18. Juni 2017 | DAS TYPO3-Community-Event in Berlin | Bleibt auf dem Laufenden und folgt uns!",
            "Agentur für effiziente Kommunikation und E-Business",
            "Webentwickler",
            "Freelance Frontend developer and TYPO3 integrator. Passionate about punk and ska music. SEGA fanboy.",
            "Webentwickler. Interessiert an Musik, Filmen und Technik",
            "Davitec ist Dienstleister für individuelle Softwareentwicklung und ermöglicht Unternehmen eine erfolgreiche digitale Transformation.",
            "HSV, Musik, TYPO3",
            "Netzwerk von TYPO3-Anwendern in der Ruhrregion und darüber hinaus - monatliche Treffen, gegenseitige Unterstützung und Freude an der Arbeit mit dem CMS TYPO3",
            "#TYPO3 und #Magento Agentur aus Jena • TYPO3 Certified Developer • TYPO3 Silver Member • TYPO3 Specialist • Magento Certified Developer",
            "Age: 43; married; 1 son (Florian) and 1 daughter (Vanessa); Work @Mittwald CM Service",
            "Coding/consulting for @TYPO3, in PHP and Scala, Alumnus of @KITKarlsruhe, Linux user, occasionally doing non-IT stuff.",
            "arndtteunissen ist eine strategische Marken- und Designagentur. Unsere wichtigste Kompetenz besteht in der Entwicklung von Corporate Identity Strategien.",
            "Entwickler @slubdresden. Full-Stack bei @literaturport & @dichterlesen. #AngularJS & #TYPO3. #AvGeek! #hahohe",
            "TYPO3 Developer",
            "TYPO3 Dev, nerds host ;-) and technology addicted from Dresden Germany",
            "yow? (=",
            "Father of two sons, Backend and mobile developer and loving  products...",
            "Webdeveloper bei https://t.co/1KJ6Sdx0jZ #TYPO3 / Youtube: https://t.co/rdYKUVG73s / Videotraining TYPO3 8 LTS: https://t.co/6EBbUNsV75",
            "Beratung | Design | Entwicklung | Redaktion | Schulungen | Betrieb",
            "Mama vom Großen und Kleinen | TYPO3 Active Contributer | Glücklich",
            "Online-Marketing-Berater und Google Partner",
            "Inhaber und Geschäftsführer bei sgalinski Internet Services (Impressum: https://t.co/Hy494B8JlP)",
            "Internet, Intranet, Extranet",
            "TYPO3 Entwickler, Rollenspieler und Mittelalter-Freak",
            "Wer nicht lebt, hat nichts zu befürchten.",
            "TYPO3 Addict, Web-Developer @in2code_de, Münchner, My Blogs: bbq-jungle.de",
            "CEO TYPO3 GmbH",
            "Wir glauben an die Stärke von Bildern. Die Kraft eines Wortes. Und an Fortschritt durch Technologie.",
            "Webdeveloper, UX and UI Expert, TYPO3-Developer",
            "Zu allem 'ne Meinung! Statements & Kommentare. Papa. Info- & Medienjunkie. fotobegeistert & reisefreudig & ein @schnittsteller",
            "Webentwicklung, TYPO3, Online-Kommunikation und was mein Leben sonst noch so hergibt....",
            "Member of TYPO3 Expert Advisory Board, TYPO3 Marketing Team, Magento | web design | content management | secure hosting",
            "#TYPO3 #SCRUM #RE #OKR; Independent Consultant, Trainer, Agile Coach; TYPO3 Expert Advisory Board & Head of TYPO3 Education; https://t.co/E6qwHNXcAh",
    };

}
