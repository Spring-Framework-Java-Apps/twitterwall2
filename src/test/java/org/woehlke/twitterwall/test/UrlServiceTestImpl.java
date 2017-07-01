package org.woehlke.twitterwall.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.PersistDataFromTwitter;

import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by tw on 01.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UrlServiceTestImpl implements UrlServiceTest {

    @Value("${twitterwall.batch.url.fetchTestDataVerbose}")
    private boolean fetchTestDataVerbose;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Url> getTestData() {
        Map<String, String> hostsTest = new HashMap<>();
        hostsTest.put("https://t.co/lQlse7u93G", "port80guru.tumblr.com");
        Map<String, String> urlsTest = new HashMap<>();
        urlsTest.put("https://t.co/lQlse7u93G", "https://port80guru.tumblr.com/");

        if (fetchTestDataVerbose) {
            hostsTest = hosts;
            urlsTest = urls;
        }

        String urlSrc;
        String display;
        String expanded;
        int[] indices = {};
        List<Url> testData = new ArrayList<>();
        for (Map.Entry<String, String> url : urlsTest.entrySet()) {
            urlSrc = url.getKey();
            expanded = url.getValue();
            display = hostsTest.get(urlSrc);
            Url myUrl = new Url(display, expanded, urlSrc, indices);
            testData.add(myUrl);
        }
        return testData;
    }

    private static Map<String, String> hosts = new HashMap<>();

    static {
        hosts.put("https://t.co/4tDiIJ74eR", "www.ecentral.de");
        hosts.put("https://t.co/n9LlZXFjTc", "www.davitec.de");
        hosts.put("http://t.co/RzkVNLQLx6", "www.medien-kultur-it.net");
        hosts.put("https://t.co/N7Rq7beH8n", "www.quizpalme.de");
        hosts.put("https://t.co/Fb3UEeVWWk", "www.xing.com");
        hosts.put("https://t.co/Ycl7ZCbRsy", "www.thomaskieslich.de");
        hosts.put("https://t.co/feV9khihK3", "querbeet.docma.de");
        hosts.put("https://t.co/vFHaMjhQxK", "www.mittwald.de");
        hosts.put("https://t.co/4HBxydWdIE", "episch.org");
        hosts.put("https://t.co/uzzouytYxh", "www.oliver-thiele.de");
        hosts.put("http://t.co/7ccMD0G00p", "magic.hat-of-typo3.de");
        hosts.put("http://t.co/gnmbZ3qaY4", "www.pega-sus.de");
        hosts.put("https://t.co/Lp00eOlYYK", "www.chriwo.de");
        hosts.put("http://t.co/ZK8jQ2KEJD", "www.renekreupl.de");
        hosts.put("https://t.co/kM9PIn3BgS", "usetypo3.com");
        hosts.put("https://t.co/46Dz7qCB8L", "www.portachtzig.com");
        hosts.put("https://t.co/xnOcVzUTkm", "typo3worx.eu");
        hosts.put("https://t.co/hMMJBGFFKR", "www.davitec.de");
        hosts.put("http://t.co/imtLrCUAko", "christian-hellmuth.de");
        hosts.put("https://t.co/RvKQvMMpzF", "wwagner.net");
        hosts.put("https://t.co/cbsEBiSW2E", "www.web-vision.de");
        hosts.put("http://t.co/FGRp2dcyII", "www.marit.ag");
        hosts.put("https://t.co/Yfcyk3aqh1", "www.fanor.de");
        hosts.put("http://t.co/mNdNHD0dLO", "www.interactive-tools.de");
        hosts.put("http://t.co/a7oWdQhTin", "undkonsorten.com");
        hosts.put("https://t.co/Lq9HDdbM1S", "www.wapplersystems.de");
        hosts.put("https://t.co/7haKo3blhj", "www.tritum.de");
        hosts.put("https://t.co/qIDUMVN3WY", "gosna.de");
        hosts.put("https://t.co/t72OfGzzfd", "github.com");
        hosts.put("http://t.co/TC1JsRY0sY", "www.typo3-ruhr.org");
        hosts.put("http://t.co/hIxL9WeoP1", "www.schnittsteller.de");
        hosts.put("http://t.co/yspEKRBcSw", "randomprojects.de");
        hosts.put("https://t.co/lQlse7u93G", "port80guru.tumblr.com");
        hosts.put("http://t.co/75Hf3JV6JU", "www.typo3camp-berlin.de");
        hosts.put("https://t.co/gnmbZ3qaY4", "www.pega-sus.de");
        hosts.put("https://t.co/4g5pdzrWkM", "alinbu.net");
        hosts.put("http://t.co/nWxOAD6WNC", "www.agentur-brandung.de");
        hosts.put("http://t.co/5uLiz5mReg", "www.foodfindr.de");
        hosts.put("http://t.co/sgSySSyjol", "t3easy.de");
        hosts.put("http://t.co/N0wDJifTl8", "blog.sammyb.de");
        hosts.put("https://t.co/MuVYy9ahOi", "www.gkm.me");
        hosts.put("https://t.co/DWliJ3cEUT", "www.frauliessmann.de");
        hosts.put("https://t.co/OP7oBmBA3T", "spooner-web.de");
        hosts.put("https://t.co/z0lOKQCs0y", "typo3.com");
        hosts.put("https://t.co/okIuRGVRi1", "www.kay-strobach.de");
        hosts.put("https://t.co/f2vtNkiwTJ", "netz-basis.com");
        hosts.put("https://t.co/NWnpbiSrFg", "flyingcircus.io");
        hosts.put("https://t.co/HQpP0PXtCY", "www.rodejohann.de");
        hosts.put("http://t.co/tjFrOTqr1l", "www.cps-it.de");
        hosts.put("http://t.co/9Ct4HFvTSM", "www.plusb.de");
        hosts.put("http://t.co/8EuxwYs0va", "www.ideenwerft.com");
        hosts.put("http://t.co/MALJQIyYrt", "www.arndtteunissen.de");
        hosts.put("http://t.co/9YEDGUjbqK", "www.in2code.de");
        hosts.put("http://t.co/9P2qeHaGH7", "a-w.io");
        hosts.put("https://t.co/5s5ggTRpIX", "www.sgalinski.de");
        hosts.put("http://t.co/gnmbZ3HLPC", "www.pega-sus.de");
        hosts.put("http://t.co/CudjEZGLVT", "sebastian.kreideweiss.info");
    }

    private static Map<String, String> urls = new HashMap<>();

    static {
        urls.put("https://t.co/4tDiIJ74eR", "http://www.ecentral.de/");
        urls.put("https://t.co/n9LlZXFjTc", "https://www.davitec.de/");
        urls.put("http://t.co/RzkVNLQLx6", "http://www.medien-kultur-it.net/");
        urls.put("https://t.co/N7Rq7beH8n", "http://www.quizpalme.de/");
        urls.put("https://t.co/Fb3UEeVWWk", "https://www.xing.com/profile/Nicole_Cordes2");
        urls.put("https://t.co/Ycl7ZCbRsy", "https://www.thomaskieslich.de/");
        urls.put("https://t.co/feV9khihK3", "http://querbeet.docma.de/");
        urls.put("https://t.co/vFHaMjhQxK", "https://www.mittwald.de/");
        urls.put("https://t.co/4HBxydWdIE", "https://episch.org/");
        urls.put("https://t.co/uzzouytYxh", "https://www.oliver-thiele.de/");
        urls.put("http://t.co/7ccMD0G00p", "http://magic.hat-of-typo3.de/");
        urls.put("http://t.co/gnmbZ3qaY4", "https://www.pega-sus.de/home.html");
        urls.put("https://t.co/Lp00eOlYYK", "http://www.chriwo.de/");
        urls.put("http://t.co/ZK8jQ2KEJD", "http://www.renekreupl.de/");
        urls.put("https://t.co/kM9PIn3BgS", "https://usetypo3.com/");
        urls.put("https://t.co/46Dz7qCB8L", "https://www.portachtzig.com/");
        urls.put("https://t.co/xnOcVzUTkm", "http://typo3worx.eu/");
        urls.put("https://t.co/hMMJBGFFKR", "https://www.davitec.de/");
        urls.put("http://t.co/imtLrCUAko", "https://christian-hellmuth.de/");
        urls.put("https://t.co/RvKQvMMpzF", "https://wwagner.net/");
        urls.put("https://t.co/cbsEBiSW2E", "https://www.web-vision.de/");
        urls.put("http://t.co/FGRp2dcyII", "http://www.marit.ag/");
        urls.put("https://t.co/Yfcyk3aqh1", "http://www.fanor.de/");
        urls.put("http://t.co/mNdNHD0dLO", "https://www.interactive-tools.de/");
        urls.put("http://t.co/a7oWdQhTin", "http://undkonsorten.com/");
        urls.put("https://t.co/Lq9HDdbM1S", "https://www.wapplersystems.de/profil-sven-wappler/");
        urls.put("https://t.co/7haKo3blhj", "https://www.tritum.de/");
        urls.put("https://t.co/qIDUMVN3WY", "http://gosna.de/");
        urls.put("https://t.co/t72OfGzzfd", "https://github.com/markusguenther");
        urls.put("http://t.co/TC1JsRY0sY", "http://www.typo3-ruhr.org/");
        urls.put("http://t.co/hIxL9WeoP1", "http://www.schnittsteller.de/");
        urls.put("http://t.co/yspEKRBcSw", "http://randomprojects.de/ClubMate_RT/");
        urls.put("https://t.co/lQlse7u93G", "https://port80guru.tumblr.com/");
        urls.put("http://t.co/75Hf3JV6JU", "http://www.typo3camp-berlin.de/");
        urls.put("https://t.co/gnmbZ3qaY4", "https://www.pega-sus.de/home.html");
        urls.put("https://t.co/4g5pdzrWkM", "https://alinbu.net/");
        urls.put("http://t.co/nWxOAD6WNC", "https://www.agentur-brandung.de/");
        urls.put("http://t.co/5uLiz5mReg", "http://www.foodfindr.de/");
        urls.put("http://t.co/sgSySSyjol", "https://t3easy.de/index.php?id=2");
        urls.put("http://t.co/N0wDJifTl8", "https://blog.sammyb.de/");
        urls.put("https://t.co/MuVYy9ahOi", "https://www.gkm.me/");
        urls.put("https://t.co/DWliJ3cEUT", "http://www.frauliessmann.de/");
        urls.put("https://t.co/OP7oBmBA3T", "https://spooner-web.de/");
        urls.put("https://t.co/z0lOKQCs0y", "https://typo3.com/");
        urls.put("https://t.co/okIuRGVRi1", "http://www.kay-strobach.de/willkommen/");
        urls.put("https://t.co/f2vtNkiwTJ", "https://netz-basis.com/blog");
        urls.put("https://t.co/NWnpbiSrFg", "https://flyingcircus.io/");
        urls.put("https://t.co/HQpP0PXtCY", "http://www.rodejohann.de/");
        urls.put("http://t.co/tjFrOTqr1l", "http://www.cps-it.de/");
        urls.put("http://t.co/9Ct4HFvTSM", "http://www.plusb.de/");
        urls.put("http://t.co/8EuxwYs0va", "https://www.ideenwerft.com/");
        urls.put("http://t.co/MALJQIyYrt", "https://www.arndtteunissen.de/");
        urls.put("http://t.co/9YEDGUjbqK", "https://www.in2code.de/");
        urls.put("http://t.co/9P2qeHaGH7", "http://a-w.io/");
        urls.put("https://t.co/5s5ggTRpIX", "https://www.sgalinski.de/");
        urls.put("http://t.co/gnmbZ3HLPC", "https://www.pega-sus.de/home.html");
        urls.put("http://t.co/CudjEZGLVT", "http://sebastian.kreideweiss.info/");
    }
}
