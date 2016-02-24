/**
 *
 */
package org.confucius.commons.lang;

import com.google.common.collect.Sets;
import junit.framework.Assert;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.confucius.commons.lang.constants.FileSuffixConstants;
import org.junit.Test;

import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
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
    public void testFields() throws Exception {


        List<Field> allFields = FieldUtils.getAllFieldsList(ClassLoader.class);

//        echo(ToStringBuilder.reflectionToString(classLoader,ToStringStyle.MULTI_LINE_STYLE));
        Set<ClassLoader> classLoaders = ClassLoaderUtil.getInheritableClassLoaders(classLoader);
        for (ClassLoader classLoader : classLoaders) {
            echo(String.format("ClassLoader : %s", classLoader));
            for (Field field : allFields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    String message = String.format("Field name : %s , value : %s", field.getName(), ToStringBuilder.reflectionToString(field.get(classLoader), ToStringStyle.NO_CLASS_NAME_STYLE));
                    echo(message);
                }
            }
        }

    }


    @Test
    public void testResolve() {
        String resourceName = "META-INF/abc/def";
        String expectedResourceName = "META-INF/abc/def";
        String resolvedResourceName = ClassLoaderUtil.ResourceType.DEFAULT.resolve(resourceName);
        Assert.assertEquals(expectedResourceName, resolvedResourceName);

        resourceName = "///////META-INF//abc\\/def";
        resolvedResourceName = ClassLoaderUtil.ResourceType.DEFAULT.resolve(resourceName);
        Assert.assertEquals(expectedResourceName, resolvedResourceName);

        resourceName = "java.lang.String.class";

        expectedResourceName = "java/lang/String.class";
        resolvedResourceName = ClassLoaderUtil.ResourceType.CLASS.resolve(resourceName);
        Assert.assertEquals(expectedResourceName, resolvedResourceName);

        resourceName = "java.lang";
        expectedResourceName = "java/lang/";
        resolvedResourceName = ClassLoaderUtil.ResourceType.PACKAGE.resolve(resourceName);
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
        URL resourceURL = ClassLoaderUtil.getResource(classLoader, ClassLoaderUtilTest.class.getName() + FileSuffixConstants.CLASS);
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
        Set<URL> resourceURLs = ClassLoaderUtil.getResources(classLoader, ClassLoaderUtilTest.class.getName() + FileSuffixConstants.CLASS);
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

        type = ClassLoaderUtil.findLoadedClass(classLoader, Double.class.getName());
        Assert.assertEquals(Double.class, type);
    }

    @Test
    public void testIsLoadedClass() {
        Assert.assertTrue(ClassLoaderUtil.isLoadedClass(classLoader, String.class));
        Assert.assertTrue(ClassLoaderUtil.isLoadedClass(classLoader, Double.class));
        Assert.assertTrue(ClassLoaderUtil.isLoadedClass(classLoader, Double.class.getName()));
    }


    @Test
    public void testFindLoadedClassesInClassPath() {
        Double d = null;
        Set<Class<?>> allLoadedClasses = ClassLoaderUtil.findLoadedClassesInClassPath(classLoader);

        Set<Class<?>> classesSet = ClassLoaderUtil.getAllLoadedClasses(classLoader);

        Set<Class<?>> remainingClasses = Sets.newLinkedHashSet(allLoadedClasses);

        remainingClasses.addAll(classesSet);

        Set<Class<?>> sortedClasses = Sets.newTreeSet(new ClassComparator());
        sortedClasses.addAll(remainingClasses);

        echo(sortedClasses);

        int loadedClassesSize = allLoadedClasses.size() + classesSet.size();

        int loadedClassCount = ClassLoaderUtil.getLoadedClassCount();

        echo(loadedClassesSize);
        echo(loadedClassCount);
    }

    @Test
    public void testGetCount() {
        long count = ClassLoaderUtil.getTotalLoadedClassCount();
        Assert.assertTrue(count > 0);

        count = ClassLoaderUtil.getLoadedClassCount();
        Assert.assertTrue(count > 0);

        count = ClassLoaderUtil.getUnloadedClassCount();
        Assert.assertTrue(count > -1);
    }

    @Test
    public void testFindLoadedClassesInClassPaths() {
        Set<Class<?>> allLoadedClasses = ClassLoaderUtil.findLoadedClassesInClassPaths(classLoader, ClassPathUtil.getClassPaths());
        Assert.assertFalse(allLoadedClasses.isEmpty());
    }


    private static class ClassComparator implements Comparator<Class<?>> {

        @Override
        public int compare(Class<?> o1, Class<?> o2) {
            String cn1 = o1.getName();
            String cn2 = o2.getName();
            return cn1.compareTo(cn2);
        }
    }

}
