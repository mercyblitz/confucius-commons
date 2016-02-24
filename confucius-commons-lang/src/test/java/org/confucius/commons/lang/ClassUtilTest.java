/**
 * Confucius commons project
 */
package org.confucius.commons.lang;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 * {@link ClassUtil} {@link TestCase}
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassUtilTest
 * @since 1.0.0 2016-02-23
 */
public class ClassUtilTest extends AbstractTestCase {

    @Test
    public void testGetClassNamesInClassPath() {
        Set<String> classPaths = ClassPathUtil.getClassPaths();
        for (String classPath : classPaths) {
            Set<String> classNames = ClassUtil.getClassNamesInClassPath(classPath, true);
            Assert.assertNotNull(classNames);
        }
    }

    @Test
    public void testGetClassNamesInPackage() {
        Set<String> packageNames = ClassUtil.getAllPackageNamesInClassPaths();
        for (String packageName : packageNames) {
            Set<String> classNames = ClassUtil.getClassNamesInPackage(packageName);
            Assert.assertFalse(classNames.isEmpty());
            Assert.assertNotNull(classNames);
            echo(packageName);
            echo("\t" + classNames);
        }
    }


    @Test
    public void testGetAllPackageNamesInClassPaths() {
        Set<String> packageNames = ClassUtil.getAllPackageNamesInClassPaths();
        Assert.assertNotNull(packageNames);
        echo(packageNames);
    }

    @Test
    public void testFindClassPath() {
        String classPath = ClassUtil.findClassPath(Map.Entry.class);
        Assert.assertNotNull(classPath);
    }

    @Test
    public void testGetAllClassNamesMapInClassPath() {
        Map<String, Set<String>> allClassNamesMapInClassPath = ClassUtil.getClassPathToClassNamesMap();
        Assert.assertFalse(allClassNamesMapInClassPath.isEmpty());
    }

    @Test
    public void testGetAllClassNamesInClassPath() {
        Set<String> allClassNames = ClassUtil.getAllClassNamesInClassPaths();
        Assert.assertFalse(allClassNames.isEmpty());
    }
}
