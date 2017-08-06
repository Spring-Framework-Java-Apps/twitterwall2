package org.woehlke.twitterwall.oodm.entities;

import org.junit.Assert;
import org.junit.Test;

public class HashTagTest implements DomainObjectMinimalTest {

    @Test
    @Override
    public void getUniqueIdTest() throws Exception {
        String msg = "getUniqueIdTest: ";

        Task createdBy = null;
        Task updatedBy = null;
        String text = "Java";

        HashTag hashTag = new HashTag(createdBy,updatedBy,text);

        Assert.assertEquals(msg,text,hashTag.getUniqueId());
    }

    @Test
    @Override
    public void isValidTest() throws Exception {
        String msg = "isValidTest: ";

        Task createdBy = null;
        Task updatedBy = null;

        String textValid1 ="Java";

        String textValid2 = "öÖäÄüÜßabvfjbdfghjzAFZFZTFZTFZ055542749_";

        String textNotValid1 = "öÖäÄüÜßabvfjbdfghjzAFZFZTFZTFZ055542749_-";

        String textNotValid2 = "öÖäÄüÜßabvfjbdfghjzAFZFZTF ZTFZ055542749_";

        String textNotValid3 = "öÖäÄüÜßabvfjbdfghjzAFZFZTF%ZTFZ055542749_";

        String textNotValid4 = "öÖäÄüÜßabvfjbdfghjzAFZFZTF@ZTFZ055542749_";

        Assert.assertTrue(msg,HashTag.isValidText(textValid1));
        Assert.assertTrue(msg,HashTag.isValidText(textValid2));

        Assert.assertFalse(msg,HashTag.isValidText(textNotValid1));
        Assert.assertFalse(msg,HashTag.isValidText(textNotValid2));
        Assert.assertFalse(msg,HashTag.isValidText(textNotValid3));
        Assert.assertFalse(msg,HashTag.isValidText(textNotValid4));

        HashTag hashTag = new HashTag(createdBy,updatedBy,textValid1);

        Assert.assertTrue(msg,hashTag.isValid());

        hashTag.setText(textValid2); Assert.assertTrue(msg,hashTag.isValid());

        hashTag.setText(textNotValid1); Assert.assertFalse(msg,hashTag.isValid());
        hashTag.setText(textNotValid2); Assert.assertFalse(msg,hashTag.isValid());
        hashTag.setText(textNotValid3); Assert.assertFalse(msg,hashTag.isValid());
        hashTag.setText(textNotValid4); Assert.assertFalse(msg,hashTag.isValid());
    }
}
