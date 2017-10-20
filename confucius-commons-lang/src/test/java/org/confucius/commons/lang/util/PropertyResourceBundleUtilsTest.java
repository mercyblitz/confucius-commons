/**
 *
 */
package org.confucius.commons.lang.util;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ResourceBundle;

/**
 * {@link PropertyResourceBundleUtils} {@link Test}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see PropertyResourceBundleUtilsTest
 * @since 1.0.0
 */
public class PropertyResourceBundleUtilsTest {

    @Test
    public void testGetBundle() {
        ResourceBundle resourceBundle = PropertyResourceBundleUtils.getBundle("META-INF.test", "UTF-8");
        String expected = "测试名称";
        String value = resourceBundle.getString("name");
        Assert.assertEquals(expected, value);
    }

}
