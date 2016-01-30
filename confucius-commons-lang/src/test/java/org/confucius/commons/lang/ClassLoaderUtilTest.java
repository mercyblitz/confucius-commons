/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.lang;

import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * {@link ClassLoaderUtil} {@link Test}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
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

    @Test
    public void testClassLoadingMXBean() {
        ClassLoadingMXBean classLoadingMXBean = ClassLoaderUtil.classLoadingMXBean;
        Assert.assertEquals(classLoadingMXBean.getTotalLoadedClassCount(), ClassLoaderUtil.getTotalLoadedClassCount());
        Assert.assertEquals(classLoadingMXBean.getLoadedClassCount(), ClassLoaderUtil.getLoadedClassCount());
        Assert.assertEquals(classLoadingMXBean.getUnloadedClassCount(), ClassLoaderUtil.getUnloadedClassCount());
        Assert.assertEquals(classLoadingMXBean.isVerbose(), ClassLoaderUtil.isVerbose());

        ClassLoaderUtil.setVerbose(true);
        Assert.assertTrue(ClassLoaderUtil.isVerbose());
    }

    @Test
    public void testGetInheritableClassLoaders() {
        Set<ClassLoader> classLoaders = ClassLoaderUtil.getInheritableClassLoaders(classLoader);
        Assert.assertNotNull(classLoaders);
        Assert.assertTrue(classLoaders.size() > 1);
        echo(classLoaders);
    }

    @Test
    public void testGetLoadedClasses() {
        Set<Class<?>> classesSet = ClassLoaderUtil.getLoadedClasses(classLoader);
        Assert.assertNotNull(classesSet);
        Assert.assertFalse(classesSet.isEmpty());


        classesSet = ClassLoaderUtil.getLoadedClasses(ClassLoader.getSystemClassLoader());
        Assert.assertNotNull(classesSet);
        Assert.assertFalse(classesSet.isEmpty());
        echo(classesSet);
    }

    @Test
    public void testGetAllLoadedClasses() {
        Set<Class<?>> classesSet = ClassLoaderUtil.getAllLoadedClasses(classLoader);
        Assert.assertNotNull(classesSet);
        Assert.assertFalse(classesSet.isEmpty());


        classesSet = ClassLoaderUtil.getAllLoadedClasses(ClassLoader.getSystemClassLoader());
        Assert.assertNotNull(classesSet);
        Assert.assertFalse(classesSet.isEmpty());
        echo(classesSet);
    }

    @Test
    public void testGetAllLoadedClassesMap() {
        Map<ClassLoader, Set<Class<?>>> allLoadedClassesMap = ClassLoaderUtil.getAllLoadedClassesMap(classLoader);
        Assert.assertNotNull(allLoadedClassesMap);
        Assert.assertFalse(allLoadedClassesMap.isEmpty());
    }

    @Test
    public void testFindLoadedClass() {

        Class<?> type = null;
        for (Class<?> class_ : ClassLoaderUtil.getAllLoadedClasses(classLoader)) {
            type = ClassLoaderUtil.findLoadedClass(classLoader, class_.getName());
            Assert.assertEquals(class_, type);
        }

        type = ClassLoaderUtil.findLoadedClass(classLoader, String.class.getName());
        Assert.assertEquals(String.class, type);

        long startTime = System.currentTimeMillis();
        int times = 1000 * 1000;
        for (int i = 0; i < times; i++) {
            type = ClassLoaderUtil.findLoadedClass(classLoader, String.class.getName());
        }
        long costTime = System.currentTimeMillis() - startTime;
        String message = String.format("%s times invocation of ClassLoaderUtil.findLoadedClass takes %s ms", times, costTime);
        echo(message);
        Assert.assertEquals(String.class, type);
    }


    @Test
    public void testGetLoadedBootstrapClasses() {
        Set<Class<?>> loadedBootstrapClasses = ClassLoaderUtil.getLoadedBootstrapClasses();

        Set<Class<?>> classesSet = ClassLoaderUtil.getAllLoadedClasses(classLoader);

        Assert.assertEquals(loadedBootstrapClasses, classesSet);

        int loadedClassesSize = loadedBootstrapClasses.size() + classesSet.size();

        int loadedClassCount = ClassLoaderUtil.getLoadedClassCount();

        echo(loadedClassesSize);
        echo(loadedClassCount);
    }

}
