/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.util;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ResourceBundle;
import java.util.SortedMap;

/**
 * {@link PropertyResourceBundleControl} {@link Test}
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see PropertyResourceBundleControlTest
 * @since 1.0.0
 */
public class PropertyResourceBundleControlTest extends TestCase {

    @Test
    public void testNewControl() {
        SortedMap<String, Charset> charsetsSortedMap = Charset.availableCharsets();
        for (String encoding : charsetsSortedMap.keySet()) {
            ResourceBundle.Control control = PropertyResourceBundleControl.newControl(encoding);
            Assert.assertNotNull(control);
        }
    }

    @Test(expected = UnsupportedCharsetException.class)
    public void testNewControlOnException() {
        PropertyResourceBundleControl.newControl("NON-SUPPORTED-ENCODING");
    }
}
