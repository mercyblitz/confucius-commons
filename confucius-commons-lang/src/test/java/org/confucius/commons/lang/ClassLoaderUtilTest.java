/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.lang;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * {@link ClassLoaderUtil} {@link TestCase}
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassLoaderUtil
 * @since 1.0.0
 */
public class ClassLoaderUtilTest extends AbstractTestCase {

    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    @Test
    public void testResolve() {
        String resourceName = "META-INF/abc/def";
        String expectedResourceName = "META-INF/abc/def";
        String resolvedResourceName = ClassLoaderUtil.ResourceType.DEFAULT.resolve(resourceName);
        Assert.assertEquals(expectedResourceName, resolvedResourceName);

        resourceName = "///////META-INF//abc\\/def";
        resolvedResourceName = ClassLoaderUtil.ResourceType.DEFAULT.resolve(resourceName);
        Assert.assertEquals(expectedResourceName, resolvedResourceName);

        resourceName = "java.lang.String";

        expectedResourceName = "java/lang/String.class";
        resolvedResourceName = ClassLoaderUtil.ResourceType.CLASS.resolve(resourceName);
        Assert.assertEquals(expectedResourceName, resolvedResourceName);

    }

    @Test
    public void testGetClassResource() {
        URL classResourceURL = ClassLoaderUtil.getClassResource(classLoader, ClassLoaderUtilTest.class);
        Assert.assertNotNull(classResourceURL);
        echo(classResourceURL);

        classResourceURL = ClassLoaderUtil.getClassResource(classLoader, String.class.getName());
        Assert.assertNotNull(classResourceURL);
        echo(classResourceURL);
    }

    @Test
    public void testGetResource() {
        URL resourceURL = ClassLoaderUtil.getResource(classLoader, ClassLoaderUtilTest.class.getName());
        Assert.assertNotNull(resourceURL);
        echo(resourceURL);

        resourceURL = ClassLoaderUtil.getResource(classLoader, "///java/lang/CharSequence.class");
        Assert.assertNotNull(resourceURL);
        echo(resourceURL);

        resourceURL = ClassLoaderUtil.getResource(classLoader, "//META-INF/services/java.lang.CharSequence");
        Assert.assertNotNull(resourceURL);
        echo(resourceURL);
    }

    @Test
    public void testGetResources() throws IOException {
        Set<URL> resourceURLs = ClassLoaderUtil.getResources(classLoader, ClassLoaderUtilTest.class.getName());
        Assert.assertNotNull(resourceURLs);
        Assert.assertEquals(1, resourceURLs.size());
        echo(resourceURLs);

        resourceURLs = ClassLoaderUtil.getResources(classLoader, "///java/lang/CharSequence.class");
        Assert.assertNotNull(resourceURLs);
        Assert.assertEquals(1, resourceURLs.size());
        echo(resourceURLs);

        resourceURLs = ClassLoaderUtil.getResources(classLoader, "//META-INF/services/java.lang.CharSequence");
        Assert.assertNotNull(resourceURLs);
        Assert.assertEquals(1, resourceURLs.size());
        echo(resourceURLs);
    }

}
