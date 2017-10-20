/**
 *
 */
package org.confucius.commons.lang;

import junit.framework.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Set;

/**
 * {@link ClassPathUtils} {@link Test}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassPathUtilsTest
 * @since 1.0.0
 */
public class ClassPathUtilsTest extends AbstractTestCase {

    @Test
    public void testGetBootstrapClassPaths() {
        Set<String> bootstrapClassPaths = ClassPathUtils.getBootstrapClassPaths();
        Assert.assertNotNull(bootstrapClassPaths);
        Assert.assertFalse(bootstrapClassPaths.isEmpty());
        echo(bootstrapClassPaths);
    }


    @Test
    public void testGetClassPaths() {
        Set<String> classPaths = ClassPathUtils.getClassPaths();
        Assert.assertNotNull(classPaths);
        Assert.assertFalse(classPaths.isEmpty());
        echo(classPaths);
    }

    @Test
    public void getRuntimeClassLocation() {
        URL location = null;
        location = ClassPathUtils.getRuntimeClassLocation(String.class);
        Assert.assertNotNull(location);
        echo(location);

        location = ClassPathUtils.getRuntimeClassLocation(getClass());
        Assert.assertNotNull(location);
        echo(location);

        //Primitive type
        location = ClassPathUtils.getRuntimeClassLocation(int.class);
        Assert.assertNull(location);

        //Array type
        location = ClassPathUtils.getRuntimeClassLocation(int[].class);
        Assert.assertNull(location);


        Set<String> classNames = ClassUtils.getAllClassNamesInClassPaths();
        for (String className : classNames) {
            if (!ClassLoaderUtils.isLoadedClass(classLoader, className)) {
                location = ClassPathUtils.getRuntimeClassLocation(className);
                Assert.assertNull(location);
            }
        }

    }
}
