/**
 *
 */
package org.confucius.commons.lang;

import junit.framework.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Set;

/**
 * {@link ClassPathUtil} {@link Test}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassPathUtilTest
 * @since 1.0.0
 */
public class ClassPathUtilTest extends AbstractTestCase {

    @Test
    public void testGetBootstrapClassPaths() {
        Set<String> bootstrapClassPaths = ClassPathUtil.getBootstrapClassPaths();
        Assert.assertNotNull(bootstrapClassPaths);
        Assert.assertFalse(bootstrapClassPaths.isEmpty());
        echo(bootstrapClassPaths);
    }


    @Test
    public void testGetClassPaths() {
        Set<String> classPaths = ClassPathUtil.getClassPaths();
        Assert.assertNotNull(classPaths);
        Assert.assertFalse(classPaths.isEmpty());
        echo(classPaths);
    }

    @Test
    public void getRuntimeClassLocation() {
        URL location = null;
        location = ClassPathUtil.getRuntimeClassLocation(String.class);
        Assert.assertNotNull(location);
        echo(location);

        location = ClassPathUtil.getRuntimeClassLocation(getClass());
        Assert.assertNotNull(location);
        echo(location);

        //Primitive type
        location = ClassPathUtil.getRuntimeClassLocation(int.class);
        Assert.assertNull(location);

        //Array type
        location = ClassPathUtil.getRuntimeClassLocation(int[].class);
        Assert.assertNull(location);


        Set<String> classNames = ClassUtil.getAllClassNamesInClassPaths();
        for (String className : classNames) {
            if (!ClassLoaderUtil.isLoadedClass(classLoader, className)) {
                location = ClassPathUtil.getRuntimeClassLocation(className);
                Assert.assertNull(location);
            }
        }

    }
}
