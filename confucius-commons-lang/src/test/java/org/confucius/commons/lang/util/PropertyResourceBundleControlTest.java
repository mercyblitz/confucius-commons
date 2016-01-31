/**
 *
 */
package org.confucius.commons.lang.util;

import junit.framework.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ResourceBundle;
import java.util.SortedMap;

/**
 * {@link PropertyResourceBundleControl} {@link Test}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see PropertyResourceBundleControlTest
 * @since 1.0.0
 */
public class PropertyResourceBundleControlTest {

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
