/**
 * Confucius commons project
 */
package org.confucius.commons.lang;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * {@link ClassUtils} {@link TestCase}
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassUtilsTest
 * @since 1.0.0
 */
public class ClassUtilsTest extends AbstractTestCase {

    @Test
    public void testGetClassNamesInClassPath() {
        Set<String> classPaths = ClassPathUtils.getClassPaths();
        for (String classPath : classPaths) {
            Set<String> classNames = ClassUtils.getClassNamesInClassPath(classPath, true);
            Assert.assertNotNull(classNames);
        }
    }

    @Test
    public void testGetClassNamesInPackage() {
        Set<String> packageNames = ClassUtils.getAllPackageNamesInClassPaths();
        for (String packageName : packageNames) {
            Set<String> classNames = ClassUtils.getClassNamesInPackage(packageName);
            Assert.assertFalse(classNames.isEmpty());
            Assert.assertNotNull(classNames);
            echo(packageName);
            echo("\t" + classNames);
        }
    }


    @Test
    public void testGetAllPackageNamesInClassPaths() {
        Set<String> packageNames = ClassUtils.getAllPackageNamesInClassPaths();
        Assert.assertNotNull(packageNames);
        echo(packageNames);
    }

    @Test
    public void testFindClassPath() {
        String classPath = ClassUtils.findClassPath(MapUtils.class);
        Assert.assertNotNull(classPath);

        classPath = ClassUtils.findClassPath(String.class);
        Assert.assertNotNull(classPath);
    }

    @Test
    public void testGetAllClassNamesMapInClassPath() {
        Map<String, Set<String>> allClassNamesMapInClassPath = ClassUtils.getClassPathToClassNamesMap();
        Assert.assertFalse(allClassNamesMapInClassPath.isEmpty());
    }

    @Test
    public void testGetAllClassNamesInClassPath() {
        Set<String> allClassNames = ClassUtils.getAllClassNamesInClassPaths();
        Assert.assertFalse(allClassNames.isEmpty());
    }

    @Test
    public void testGetCodeSourceLocation() throws IOException {
        URL codeSourceLocation = null;
        Assert.assertNull(codeSourceLocation);

        codeSourceLocation = ClassUtils.getCodeSourceLocation(ClassUtilsTest.class);
        echo("codeSourceLocation : " + codeSourceLocation);
        Assert.assertNotNull(codeSourceLocation);

        codeSourceLocation = ClassUtils.getCodeSourceLocation(String.class);
        echo("codeSourceLocation : " + codeSourceLocation);
        Assert.assertNotNull(codeSourceLocation);


    }

}
