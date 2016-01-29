/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.util;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ResourceBundle;

/**
 * {@link PropertyResourceBundleUtil} {@link Test}
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see PropertyResourceBundleUtilTest
 * @since 1.0.0
 */
public class PropertyResourceBundleUtilTest extends TestCase {

    @Test
    public void testGetBundle() {
        ResourceBundle resourceBundle = PropertyResourceBundleUtil.getBundle("META-INF.test");
        String expected = "测试名称";
        String value = resourceBundle.getString("name");
        Assert.assertEquals(expected, value);
    }
}
