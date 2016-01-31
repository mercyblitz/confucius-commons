/**
 *
 */
package org.confucius.commons.lang;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.EnumerationUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.confucius.commons.lang.constants.Constants;
import org.confucius.commons.lang.constants.FileSuffixConstants;
import org.confucius.commons.lang.constants.PathConstants;
import org.confucius.commons.lang.io.FileUtil;
import org.confucius.commons.lang.io.scanner.SimpleFileScanner;
import org.confucius.commons.lang.io.scanner.SimpleJarEntryScanner;
import org.confucius.commons.lang.net.URLUtil;
import org.confucius.commons.lang.util.jar.JarUtil;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * {@link ClassLoader} Utility
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassLoader
 * @since 1.0.0
 */
public abstract class ClassLoaderUtil {

    protected static final ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();

    private static final Method findLoadedClassMethod = initFindLoadedClassMethod();

    private static final Map<String, Set<String>> allClassNamesMapInClassPath = initClassNamesMapInClassPath();

    private static Map<String, Set<String>> initClassNamesMapInClassPath() {
        Map<String, Set<String>> classNamesMap = Maps.newLinkedHashMap();
        Set<String> classPaths = ClassPathUtil.getClassPaths();
        for (String classPath : classPaths) {
            Set<String> classNames = findClassNames(classPath);
            classNamesMap.put(classPath, classNames);
        }
        return Collections.unmodifiableMap(classNamesMap);
    }

    /**
     * Initializes {@link Method} for {@link ClassLoader#findLoadedClass(String)}
     *
     * @return {@link Method} for {@link ClassLoader#findLoadedClass(String)}
     */
    private static Method initFindLoadedClassMethod() {
        final Method findLoadedClassMethod;
        try {
            findLoadedClassMethod = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
            findLoadedClassMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw jvmUnsupportedOperationException(e);
        }
        return findLoadedClassMethod;
    }

    private static UnsupportedOperationException jvmUnsupportedOperationException(Throwable throwable) {
        String stackTrace = ExceptionUtils.getStackTrace(throwable);
        String message = String.format("Current JVM[ Implementation : %s , Version : %s ] does not supported ! " +
                "Stack Trace : %s", SystemUtils.JAVA_VENDOR, SystemUtils.JAVA_VERSION, stackTrace);
        throw new UnsupportedOperationException(message);
    }

    /**
     * The map of all class names in {@link ClassPathUtil#getClassPaths() class path} , the class path for one {@link
     * JarFile} or classes directory as key , the class names set as value
     *
     * @return Read-only
     */
    @Nonnull
    public static Map<String, Set<String>> getAllClassNamesMapInClassPath() {
        return allClassNamesMapInClassPath;
    }

    /**
     * The set of all class names in {@link ClassPathUtil#getClassPaths() class path}
     *
     * @return Read-only
     */
    @Nonnull
    public static Set<String> getAllClassNamesInClassPath() {
        Set<String> allClassNames = Sets.newLinkedHashSet();
        for (Set<String> classNames : allClassNamesMapInClassPath.values()) {
            allClassNames.addAll(classNames);
        }
        return Collections.unmodifiableSet(allClassNames);
    }


    /**
     * Returns the number of classes that are currently loaded in the Java virtual machine.
     *
     * @return the number of currently loaded classes.
     */
    public static int getLoadedClassCount() {
        return classLoadingMXBean.getLoadedClassCount();
    }

    /**
     * Returns the total number of classes unloaded since the Java virtual machine has started execution.
     *
     * @return the total number of unloaded classes.
     */
    public static long getUnloadedClassCount() {
        return classLoadingMXBean.getUnloadedClassCount();
    }

    /**
     * Tests if the verbose output for the class loading system is enabled.
     *
     * @return <tt>true</tt> if the verbose output for the class loading system is enabled; <tt>false</tt> otherwise.
     */
    public static boolean isVerbose() {
        return classLoadingMXBean.isVerbose();
    }

    /**
     * Enables or disables the verbose output for the class loading system.  The verbose output information and the
     * output stream to which the verbose information is emitted are implementation dependent.  Typically, a Java
     * virtual machine implementation prints a message each time a class file is loaded.
     * <p/>
     * <p>This method can be called by multiple threads concurrently. Each invocation of this method enables or disables
     * the verbose output globally.
     *
     * @param value
     *         <tt>true</tt> to enable the verbose output; <tt>false</tt> to disable.
     * @throws java.lang.SecurityException
     *         if a security manager exists and the caller does not have ManagementPermission("control").
     */
    public static void setVerbose(boolean value) {
        classLoadingMXBean.setVerbose(value);
    }

    /**
     * Returns the total number of classes that have been loaded since the Java virtual machine has started execution.
     *
     * @return the total number of classes loaded.
     */
    public static long getTotalLoadedClassCount() {
        return classLoadingMXBean.getTotalLoadedClassCount();
    }

    /**
     * Find Loaded {@link Class} under specified inheritable {@link ClassLoader} and class names
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @param classNames
     *         class names set
     * @return {@link Class} if loaded , or <code>null</code>
     */
    public static Set<Class<?>> findLoadedClasses(ClassLoader classLoader, Set<String> classNames) {
        Set<Class<?>> loadedClasses = Sets.newLinkedHashSet();
        for (String className : classNames) {
            Class<?> class_ = findLoadedClass(classLoader, className);
            if (class_ != null) {
                loadedClasses.add(class_);
            }
        }
        return Collections.unmodifiableSet(loadedClasses);
    }

    /**
     * Check specified {@link Class} is loaded on specified inheritable {@link ClassLoader}
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @param type
     *         {@link Class}
     * @return If Loaded , return <code>true</code> , or <code>false</code>
     */
    public static boolean isLoadedClass(ClassLoader classLoader, Class<?> type) {
        return isLoadedClass(classLoader, type.getName());
    }

    /**
     * Check specified {@link Class#getName() class name}  is loaded on specified inheritable {@link ClassLoader}
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @param className
     *         {@link Class#getName() class name}
     * @return If Loaded , return <code>true</code> , or <code>false</code>
     */
    public static boolean isLoadedClass(ClassLoader classLoader, String className) {
        return findLoadedClass(classLoader, className) != null;
    }

    /**
     * Find Loaded {@link Class} under specified inheritable {@link ClassLoader}
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @param className
     *         class name
     * @return {@link Class} if loaded , or <code>null</code>
     */
    public static Class<?> findLoadedClass(ClassLoader classLoader, String className) {
        Class<?> loadedClass = null;
        Set<ClassLoader> classLoaders = getInheritableClassLoaders(classLoader);
        try {
            for (ClassLoader loader : classLoaders) {
                loadedClass = (Class<?>) findLoadedClassMethod.invoke(loader, className);
                if (loadedClass != null) {
                    break;
                }
            }
        } catch (Exception ignored) {
        }
        return loadedClass;
    }

    /**
     * Get the resource URLs Set under specified resource name and type
     *
     * @param classLoader
     *         ClassLoader
     * @param resourceType
     *         {@link ResourceType} Enum
     * @param resourceName
     *         resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *         <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @throws IOException
     * @version 1.0.0
     * @since 1.0.0
     */
    public static Set<URL> getResources(ClassLoader classLoader, ResourceType resourceType, String resourceName) throws NullPointerException, IOException {
        String normalizedResourceName = resourceType.resolve(resourceName);
        return Sets.newLinkedHashSet(EnumerationUtils.toList(classLoader.getResources(normalizedResourceName)));
    }

    /**
     * Get the resource URLs list under specified resource name
     *
     * @param classLoader
     *         ClassLoader
     * @param resourceName
     *         resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *         <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @throws IOException
     * @version 1.0.0
     * @since 1.0.0
     */
    public static Set<URL> getResources(ClassLoader classLoader, String resourceName) throws NullPointerException, IOException {
        Set<URL> resourceURLs = Collections.emptySet();
        for (ResourceType resourceType : ResourceType.values()) {
            resourceURLs = getResources(classLoader, resourceType, resourceName);
            if (CollectionUtils.isNotEmpty(resourceURLs)) {
                break;
            }
        }
        return resourceURLs;
    }

    /**
     * Get the resource URL under specified resource name
     *
     * @param classLoader
     *         ClassLoader
     * @param resourceName
     *         resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *         <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @version 1.0.0
     * @since 1.0.0
     */
    public static URL getResource(ClassLoader classLoader, String resourceName) throws NullPointerException {
        URL resourceURL = null;
        for (ResourceType resourceType : ResourceType.values()) {
            resourceURL = getResource(classLoader, resourceType, resourceName);
            if (resourceURL != null) {
                break;
            }
        }
        return resourceURL;
    }

    /**
     * Get the resource URL under specified resource name and type
     *
     * @param classLoader
     *         ClassLoader
     * @param resourceType
     *         {@link ResourceType} Enum
     * @param resourceName
     *         resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *         <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @version 1.0.0
     * @since 1.0.0
     */
    public static URL getResource(ClassLoader classLoader, ResourceType resourceType, String resourceName) throws NullPointerException {
        String normalizedResourceName = resourceType.resolve(resourceName);
        return classLoader.getResource(normalizedResourceName);
    }


    /**
     * Get the {@link Class} resource URL under specified {@link Class#getName() Class name}
     *
     * @param classLoader
     *         ClassLoader
     * @param className
     *         class name
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @version 1.0.0
     * @since 1.0.0
     */
    public static URL getClassResource(ClassLoader classLoader, String className) {
        return getResource(classLoader, ResourceType.CLASS, className);
    }

    /**
     * Get the {@link Class} resource URL under specified {@link Class}
     *
     * @param classLoader
     *         ClassLoader
     * @param type
     *         {@link Class type}
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @version 1.0.0
     * @since 1.0.0
     */
    public static URL getClassResource(ClassLoader classLoader, Class<?> type) {
        String resourceName = type.getName();
        return getClassResource(classLoader, resourceName);
    }

    /**
     * Get all Inheritable {@link ClassLoader ClassLoaders} {@link Set} (including {@link ClassLoader} argument)
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @return Read-only {@link Set}
     * @throws NullPointerException
     *         If <code>classLoader</code> argument is <code>null</code>
     */
    @Nonnull
    public static Set<ClassLoader> getInheritableClassLoaders(ClassLoader classLoader) throws NullPointerException {
        Set<ClassLoader> classLoadersSet = Sets.newLinkedHashSet();
        classLoadersSet.add(classLoader);
        ClassLoader parentClassLoader = classLoader.getParent();
        while (parentClassLoader != null) {
            classLoadersSet.add(parentClassLoader);
            parentClassLoader = parentClassLoader.getParent();
        }
        return Collections.unmodifiableSet(classLoadersSet);
    }

    /**
     * Get all loaded classes {@link Map} under specified inheritable {@link ClassLoader} , {@link ClassLoader} as key ,
     * its loaded classes {@link Set} as value.
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @return Read-only Map
     * @throws UnsupportedOperationException
     * @throws NullPointerException
     *         If <code>classLoader</code> argument is <code>null</code>
     */
    @Nonnull
    public static Map<ClassLoader, Set<Class<?>>> getAllLoadedClassesMap(ClassLoader classLoader) throws UnsupportedOperationException {
        Map<ClassLoader, Set<Class<?>>> allLoadedClassesMap = Maps.newLinkedHashMap();
        Set<ClassLoader> classLoadersSet = getInheritableClassLoaders(classLoader);
        for (ClassLoader loader : classLoadersSet) {
            allLoadedClassesMap.put(loader, getLoadedClasses(loader));
        }
        return Collections.unmodifiableMap(allLoadedClassesMap);
    }

    /**
     * Get all loaded classes {@link Set} under specified inheritable {@link ClassLoader}
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @return Read-only {@link Set}
     * @throws UnsupportedOperationException
     *         If JVM does not support
     * @throws NullPointerException
     *         If <code>classLoader</code> argument is <code>null</code>
     */
    @Nonnull
    public static Set<Class<?>> getAllLoadedClasses(ClassLoader classLoader) throws UnsupportedOperationException {
        Set<Class<?>> allLoadedClassesSet = Sets.newLinkedHashSet();
        Map<ClassLoader, Set<Class<?>>> allLoadedClassesMap = getAllLoadedClassesMap(classLoader);
        for (Set<Class<?>> loadedClassesSet : allLoadedClassesMap.values()) {
            allLoadedClassesSet.addAll(loadedClassesSet);
        }
        return Collections.unmodifiableSet(allLoadedClassesSet);
    }

    /**
     * Get loaded classes {@link Set} under specified {@link ClassLoader}( not all inheritable {@link ClassLoader
     * ClassLoaders})
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @return Read-only {@link Set}
     * @throws UnsupportedOperationException
     *         If JVM does not support
     * @throws NullPointerException
     *         If <code>classLoader</code> argument is <code>null</code>
     * @see #getAllLoadedClasses(ClassLoader)
     */
    @Nonnull
    public static Set<Class<?>> getLoadedClasses(ClassLoader classLoader) throws UnsupportedOperationException {
        final Set<Class<?>> classesSet;
        try {
            List<Class<?>> classes = (List<Class<?>>) FieldUtils.readField(classLoader, "classes", true);
            classesSet = Sets.newLinkedHashSet(classes);
        } catch (IllegalAccessException e) {
            throw jvmUnsupportedOperationException(e);
        }
        return Collections.unmodifiableSet(Sets.newLinkedHashSet(classesSet));
    }

    /**
     * Find loaded classes {@link Set} in class path
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @return Read-only {@link Set}
     * @throws UnsupportedOperationException
     *         If JVM does not support
     */
    public static Set<Class<?>> findLoadedClassesInClassPath(ClassLoader classLoader) throws UnsupportedOperationException {
        Set<String> classNames = getAllClassNamesInClassPath();
        return findLoadedClasses(classLoader, classNames);
    }

    /**
     * Find loaded classes {@link Set} in class paths {@link Set}
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @param classPaths
     *         the class paths for the {@link Set} of {@link JarFile} or classes directory
     * @return Read-only {@link Set}
     * @throws UnsupportedOperationException
     *         If JVM does not support
     * @see #findLoadedClass(ClassLoader, String)
     */
    public static Set<Class<?>> findLoadedClassesInClassPaths(ClassLoader classLoader, Set<String> classPaths) throws UnsupportedOperationException {
        Set<Class<?>> loadedClasses = Sets.newLinkedHashSet();
        for (String classPath : classPaths) {
            loadedClasses.addAll(findLoadedClassesInClassPath(classLoader, classPath));
        }
        return loadedClasses;
    }

    /**
     * Find loaded classes {@link Set} in class path
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @param classPath
     *         the class path for one {@link JarFile} or classes directory
     * @return Read-only {@link Set}
     * @throws UnsupportedOperationException
     *         If JVM does not support
     * @see #findLoadedClass(ClassLoader, String)
     */
    public static Set<Class<?>> findLoadedClassesInClassPath(ClassLoader classLoader, String classPath) throws UnsupportedOperationException {
        Set<String> classNames = allClassNamesMapInClassPath.get(classPath);
        if (classNames == null) {
            classNames = findClassNames(classPath);
        }
        return findLoadedClasses(classLoader, classNames);
    }

    protected static Set<String> findClassNames(String classPath) {
        File classesFileHolder = new File(classPath); // JarFile or Directory
        if (classesFileHolder.isDirectory()) { //Directory
            return findClassNamesFromDirectory(classesFileHolder);
        } else if (classesFileHolder.isFile() && classPath.endsWith(FileSuffixConstants.JAR)) { //JarFile
            return findClassNamesFromJarFile(classesFileHolder);
        }
        return Collections.emptySet();
    }


    protected static Set<String> findClassNamesFromDirectory(File classesDirectory) {
        Set<String> classNames = Sets.newLinkedHashSet();
        SimpleFileScanner simpleFileScanner = SimpleFileScanner.INSTANCE;
        Set<File> classFiles = simpleFileScanner.scan(classesDirectory, true, new SuffixFileFilter(FileSuffixConstants.CLASS));
        for (File classFile : classFiles) {
            String className = resolveClassName(classesDirectory, classFile);
            classNames.add(className);
        }
        return classNames;
    }

    protected static Set<String> findClassNamesFromJarFile(File jarFile) {
        if (!jarFile.exists()) {
            return Collections.emptySet();
        }

        Set<String> classNames = Sets.newLinkedHashSet();

        SimpleJarEntryScanner simpleJarEntryScanner = SimpleJarEntryScanner.INSTANCE;
        try {
            JarFile jarFile_ = new JarFile(jarFile);
            Set<JarEntry> jarEntries = simpleJarEntryScanner.scan(jarFile_, true, new JarUtil.JarEntryFilter() {
                @Override
                public boolean accept(JarEntry jarEntry) {
                    return jarEntry.getName().endsWith(FileSuffixConstants.CLASS);
                }
            });

            for (JarEntry jarEntry : jarEntries) {
                String jarEntryName = jarEntry.getName();
                String className = resolveClassName(jarEntryName);
                if (StringUtils.isNotBlank(className)) {
                    classNames.add(className);
                }
            }

        } catch (Exception e) {

        }

        return classNames;
    }


    protected static String resolveClassName(File classesDirectory, File classFile) {
        String classFileRelativePath = FileUtil.resolveRelativePath(classesDirectory, classFile);
        return resolveClassName(classFileRelativePath);
    }

    protected static String resolveClassName(String resourceName) {
        String className = StringUtils.replace(resourceName, PathConstants.SLASH, Constants.DOT);
        className = StringUtils.substringBefore(className, FileSuffixConstants.CLASS);
        return className;
    }

    /**
     * Resource Type
     */
    public enum ResourceType {

        DEFAULT {
            @Override
            public String normalize(String name) {
                return name;
            }
        },
        CLASS {
            @Override
            public String normalize(String name) {
                return StringUtils.replace(name, Constants.DOT, PathConstants.SLASH) + FileSuffixConstants.CLASS;
            }
        };

        /**
         * resolves resource name
         *
         * @param name
         *         resource name
         * @return a newly resolved resource name
         */
        public String resolve(String name) {
            String normalizedName = normalize(name);
            if (normalizedName == null)
                return normalizedName;

            normalizedName = URLUtil.resolvePath(normalizedName);

            // 除去开头的"/"
            while (normalizedName.startsWith("/")) {
                normalizedName = normalizedName.substring(1);
            }

            return normalizedName;
        }

        abstract String normalize(String name);
    }

    /**
     * Class File {@link JarUtil.JarEntryFilter}
     */
    private static class ClassFileJarEntryFilter implements JarUtil.JarEntryFilter {

        @Override
        public boolean accept(JarEntry jarEntry) {
            String jarEntryName = jarEntry.getName();
            return StringUtils.endsWith(jarEntryName, FileSuffixConstants.CLASS);
        }
    }
}
