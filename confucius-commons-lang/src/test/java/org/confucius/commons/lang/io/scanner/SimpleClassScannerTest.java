/**
 * Confucius commons project
 */
package org.confucius.commons.lang.io.scanner;

import junit.framework.Assert;
import org.confucius.commons.lang.AbstractTestCase;
import org.junit.Test;

import java.util.Set;

/**
 * {@link SimpleClassScannerTest}
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see SimpleClassScannerTest
 * @since 1.0.0 2016-02-24
 */
public class SimpleClassScannerTest extends AbstractTestCase {

    private SimpleClassScanner simpleClassScanner = SimpleClassScanner.INSTANCE;

    @Test
    public void testScan() {
        Set<Class<?>> classesSet = simpleClassScanner.scan(classLoader, "java.lang");
        Assert.assertFalse(classesSet.isEmpty());
        echo(classesSet);

        classesSet = simpleClassScanner.scan(classLoader, "org.confucius.commons.lang.io.scanner");
        Assert.assertFalse(classesSet.isEmpty());
        echo(classesSet);
    }
}
