/**
 *
 */
package org.confucius.commons.lang.io.scanner;

import junit.framework.Assert;
import org.confucius.commons.lang.AbstractTestCase;
import org.confucius.commons.lang.ClassLoaderUtils;
import org.confucius.commons.lang.filter.JarEntryFilter;
import org.confucius.commons.lang.util.jar.JarUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * {@link SimpleJarEntryScanner} {@link Test}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see SimpleJarEntryScannerTest
 * @since 1.0.0
 */
public class SimpleJarEntryScannerTest extends AbstractTestCase {

    private SimpleJarEntryScanner simpleJarEntryScanner = SimpleJarEntryScanner.INSTANCE;

    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    @Test
    public void testScan() throws IOException {
        URL resourceURL = ClassLoaderUtils.getClassResource(classLoader, String.class);
        Set<JarEntry> jarEntrySet = simpleJarEntryScanner.scan(resourceURL, true);
        Assert.assertEquals(1, jarEntrySet.size());

        JarFile jarFile = JarUtils.toJarFile(resourceURL);
        jarEntrySet = simpleJarEntryScanner.scan(jarFile, true);
        Assert.assertTrue(jarEntrySet.size() > 1000);


        jarEntrySet = simpleJarEntryScanner.scan(jarFile, true, new JarEntryFilter() {
            @Override
            public boolean accept(JarEntry jarEntry) {
                return jarEntry.getName().equals("java/lang/String.class");
            }
        });

        Assert.assertEquals(1, jarEntrySet.size());

    }
}
