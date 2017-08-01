package org.woehlke.twitterwall.test;

import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Url;

import java.util.List;

/**
 * Created by tw on 01.07.17.
 */
public interface UrlServiceTestHelper {

    List<Url> getTestData(Task task);
}
