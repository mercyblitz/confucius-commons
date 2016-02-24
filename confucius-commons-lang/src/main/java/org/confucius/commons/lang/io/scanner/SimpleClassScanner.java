/**
 * Confucius commons project
 */
package org.confucius.commons.lang.io.scanner;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.confucius.commons.lang.ClassLoaderUtil;
import org.confucius.commons.lang.ClassUtil;
import org.confucius.commons.lang.filter.FilterUtil;
import org.confucius.commons.lang.filter.PackageNameClassNameFilter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Simple {@link Class} Scanner
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see SimpleClassScanner
 * @since 1.0.0 2016-02-24
 */
public class SimpleClassScanner {

    /**
     * Singleton
     */
    public final static SimpleClassScanner INSTANCE = new SimpleClassScanner();

    protected SimpleClassScanner() {

    }

    /**
     * It's equal to invoke {@link #scan(ClassLoader, String, boolean, boolean)} method with
     * <code>requiredLoad=false</code> and <code>recursive=false</code>
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @param packageName
     *         the name of package
     * @return {@link #scan(ClassLoader, String, boolean, boolean)} method with <code>requiredLoad=false</code>
     * @throws IllegalArgumentException
     *         scanned source is not legal
     * @throws IllegalStateException
     *         scanned source's state is not valid
     */
    public Set<Class<?>> scan(ClassLoader classLoader, String packageName) throws IllegalArgumentException, IllegalStateException {
        return scan(classLoader, packageName, false);
    }

    /**
     * It's equal to invoke {@link #scan(ClassLoader, String, boolean, boolean)} method with
     * <code>requiredLoad=false</code>
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @param packageName
     *         the name of package
     * @param recursive
     *         included sub-package
     * @return {@link #scan(ClassLoader, String, boolean, boolean)} method with <code>requiredLoad=false</code>
     * @throws IllegalArgumentException
     *         scanned source is not legal
     * @throws IllegalStateException
     *         scanned source's state is not valid
     */
    public Set<Class<?>> scan(ClassLoader classLoader, String packageName, boolean recursive) throws IllegalArgumentException, IllegalStateException {
        return scan(classLoader, packageName, recursive, false);
    }


    /**
     * scan {@link Class} set under specified package name or its' sub-packages in {@link ClassLoader}, if
     * <code>requiredLoad</code> indicates <code>true</code> , try to load those classes.
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @param packageName
     *         the name of package
     * @param recursive
     *         included sub-package
     * @param requiredLoad
     *         try to load those classes or not
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    public Set<Class<?>> scan(ClassLoader classLoader, String packageName, final boolean recursive, boolean requiredLoad) throws IllegalArgumentException, IllegalStateException {
        Set<Class<?>> classesSet = Sets.newLinkedHashSet();

        final String packageResourceName = ClassLoaderUtil.ResourceType.PACKAGE.resolve(packageName);

        try {
            Set<String> classNames = Sets.newLinkedHashSet();
            // Find in class loader
            Set<URL> resourceURLs = ClassLoaderUtil.getResources(classLoader, ClassLoaderUtil.ResourceType.PACKAGE, packageName);

            if (resourceURLs.isEmpty()) {
                //Find in class path
                List<String> classNamesInPackage = Lists.newArrayList(ClassUtil.getClassNamesInPackage(packageName));

                if (!classNamesInPackage.isEmpty()) {
                    String classPath = ClassUtil.findClassPath(classNamesInPackage.get(0));
                    URL resourceURL = new File(classPath).toURI().toURL();
                    resourceURLs = Sets.newHashSet(resourceURL);
                }
            }

            for (URL resourceURL : resourceURLs) {
                URL classPathURL = resolveClassPathURL(resourceURL, packageResourceName);
                String classPath = classPathURL.getFile();
                Set<String> classNamesInClassPath = ClassUtil.findClassNamesInClassPath(classPath, true);
                classNames.addAll(filterClassNames(classNamesInClassPath, packageName, recursive));
            }

            for (String className : classNames) {
                Class<?> class_ = requiredLoad ? ClassLoaderUtil.loadClass(classLoader, className) : ClassLoaderUtil.findLoadedClass(classLoader, className);
                if (class_ != null) {
                    classesSet.add(class_);
                }
            }

        } catch (IOException e) {

        }
        return Collections.unmodifiableSet(classesSet);
    }

    private Set<String> filterClassNames(Set<String> classNames, String packageName, boolean recursive) {
        PackageNameClassNameFilter packageNameClassNameFilter = new PackageNameClassNameFilter(packageName, recursive);
        Set<String> filterClassNames = Sets.newLinkedHashSet(FilterUtil.filter(classNames, packageNameClassNameFilter));
        return filterClassNames;
    }


    private URL resolveClassPathURL(URL resourceURL, String packageResourceName) {
        String resource = resourceURL.toExternalForm();
        String classPath = StringUtils.substringBefore(resource, packageResourceName);
        URL classPathURL = null;
        try {
            classPathURL = new URL(classPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return classPathURL;
    }


}
