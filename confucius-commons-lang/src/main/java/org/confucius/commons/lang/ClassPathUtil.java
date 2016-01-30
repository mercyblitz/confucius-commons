/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.lang;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import javax.annotation.Nonnull;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * {@link ClassPathUtil}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassPathUtil
 * @since 1.0.0
 */
public abstract class ClassPathUtil {

    protected static final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    private static final Set<String> bootstrapClassPaths = initBootstrapClassPaths();

    private static final Set<String> classPaths = initClassPaths();


    private static Set<String> initBootstrapClassPaths() {
        Set<String> bootstrapClassPaths = Collections.emptySet();
        if (runtimeMXBean.isBootClassPathSupported()) {
            bootstrapClassPaths = resolveClassPaths(runtimeMXBean.getBootClassPath());
        }
        return Collections.unmodifiableSet(bootstrapClassPaths);
    }

    private static Set<String> initClassPaths() {
        return resolveClassPaths(runtimeMXBean.getClassPath());
    }

    private static Set<String> resolveClassPaths(String classPath) {
        Set<String> classPaths = Sets.newLinkedHashSet();
        String[] classPathsArray = StringUtils.split(classPath, SystemUtils.PATH_SEPARATOR);
        classPaths.addAll(Arrays.asList(classPathsArray));
        return Collections.unmodifiableSet(classPaths);
    }


    /**
     * Get Bootstrap Class Paths {@link Set}
     *
     * @return If {@link RuntimeMXBean#isBootClassPathSupported()} == <code>false</code>, will return empty set.
     * @version 1.0.0
     * @since 1.0.0
     **/
    @Nonnull
    public static Set<String> getBootstrapClassPaths() {
        return bootstrapClassPaths;
    }

    /**
     * Get {@link #classPaths}
     *
     * @return Class Paths {@link Set}
     * @version 1.0.0
     * @since 1.0.0
     **/
    @Nonnull
    public static Set<String> getClassPaths() {
        return classPaths;
    }
}
