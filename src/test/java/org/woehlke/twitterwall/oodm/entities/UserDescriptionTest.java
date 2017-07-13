package org.woehlke.twitterwall.oodm.entities;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.entities.Url;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 22.06.17.
 */
public class UserDescriptionTest {

    private static final Logger log = LoggerFactory.getLogger(UserDescriptionTest.class);

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

    @Test
    public void printDescriptionsTest(){
        int lfdNr = 0;
        log.info("printDescriptionsTest");
        log.info("++++++++++++++++++++");
        log.info("found "+descriptions.length+" descriptions");
        for(String description:descriptions){
            log.info("--------------------");
            lfdNr++;
            log.info("description "+lfdNr+": "+description);
            for(HashTag hashTag:this.getHashTags(description)){
                log.info("found hashTag: "+hashTag.toString());
            }
            for(Url url:this.getUrls(description)){
                log.info("found url: "+ url.toString());
            }
            for(Mention mention:this.getMentions(description)){
                log.info("found mention: "+mention.toString());
            }
        }
        log.info("++++++++++++++++++++");
    }

    private List<HashTag> getHashTags(String description){
        List<HashTag> hashTags = new ArrayList<>();
        Pattern hashTagPattern = Pattern.compile("#(\\w*)("+stopChar+")");
        Matcher m3 = hashTagPattern.matcher(description);
        while (m3.find()) {
            hashTags.add(new HashTag(m3.group(1),indices));
        }
        Pattern hashTagPattern2 = Pattern.compile("#(\\w*)$");
        Matcher m4 = hashTagPattern2.matcher(description);
        while (m4.find()) {
            hashTags.add(new HashTag(m4.group(1),indices));
        }
        return hashTags;
    }

    private List<Url> getUrls(String description){
        List<Url> urls = new ArrayList<>();
        Pattern hashTagPattern = Pattern.compile("(https://t\\.co/\\w*)("+stopChar+")");
        Matcher m3 = hashTagPattern.matcher(description);
        while (m3.find()) {
            urls.add(getUrl(m3.group(1)));
        }
        Pattern hashTagPattern2 = Pattern.compile("(https://t\\.co/\\w*)$");
        Matcher m4 = hashTagPattern2.matcher(description);
        while (m4.find()) {
            urls.add(getUrl(m4.group(1)));
        }
        return urls;
    }

    private Url getUrl(String urlString){
        String display="";
        String expanded="";
        Url newUrl = new Url(display,expanded,urlString,indices);
        return newUrl;
    }

    private List<Mention> getMentions(String description){
        List<Mention> mentions = new ArrayList<>();
        Pattern mentionPattern1 = Pattern.compile("@(\\w*)("+stopChar+")");
        Matcher m3 = mentionPattern1.matcher(description);
        while (m3.find()) {
            mentions.add(getMention(m3.group(1)));
        }
        Pattern mentionPattern2 = Pattern.compile("@(\\w*)$");
        Matcher m4 = mentionPattern2.matcher(description);
        while (m4.find()) {
            mentions.add(getMention(m4.group(1)));
        }
        return mentions;
    }

    private Mention getMention(String mentionString) {
        long idTwitter = 10000000L;
        String screenName=mentionString;
        String name=mentionString;
        int[] myindices = indices;
        return new Mention(idTwitter,screenName,name,myindices);
    }

    static private int[] indices = {};

    static private String stopChar = Entities.stopChar;

}
