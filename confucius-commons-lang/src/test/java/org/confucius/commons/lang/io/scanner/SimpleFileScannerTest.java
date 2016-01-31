/**
 *
 */
package org.confucius.commons.lang.io.scanner;

import junit.framework.Assert;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.lang3.SystemUtils;
import org.confucius.commons.lang.AbstractTestCase;
import org.junit.Test;

import java.io.File;
import java.util.Set;

/**
 * {@link SimpleFileScanner} {@link Test}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see SimpleFileScanner
 * @since 1.0.0
 */
public class SimpleFileScannerTest extends AbstractTestCase {

    private SimpleFileScanner simpleFileScanner = SimpleFileScanner.INSTANCE;

    @Test
    public void testScan() {
        File jarHome = new File(SystemUtils.JAVA_HOME);
        Set<File> directories = simpleFileScanner.scan(jarHome, true, DirectoryFileFilter.INSTANCE);
        Assert.assertFalse(directories.isEmpty());

        directories = simpleFileScanner.scan(jarHome, false, new NameFileFilter("bin"));
        Assert.assertEquals(1, directories.size());
    }
}
