package org.confucius.commons.lang;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.List;
import java.util.Set;

/**
 * {@link ServiceLoaderUtilTest}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ServiceLoaderUtilTest
 * @since 1.0.0 2016-01-28
 */
public class ServiceLoaderUtilTest extends TestCase {

    public void testLoadServicesList() throws Exception {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<CharSequence> charSequenceList = ServiceLoaderUtil.loadServicesList(classLoader, CharSequence.class);
        Assert.assertEquals(1, charSequenceList.size());

        CharSequence charSequence = charSequenceList.get(0);
        CharSequence firstService = ServiceLoaderUtil.loadFirstService(classLoader, CharSequence.class);
        CharSequence lastService = ServiceLoaderUtil.loadLastService(classLoader, CharSequence.class);

        Assert.assertNotNull(charSequence);
        Assert.assertEquals(charSequence, firstService);
        Assert.assertEquals(charSequence, lastService);
        Assert.assertEquals(firstService, lastService);

        String string = charSequence.toString();
        assertTrue(string.isEmpty());

        IllegalArgumentException e = null;

        try {
            ServiceLoaderUtil.loadServicesList(classLoader, Set.class);
        } catch (IllegalArgumentException e_) {
            e = e_;
            e.printStackTrace();
        }

        assertNotNull(e);

    }
}
