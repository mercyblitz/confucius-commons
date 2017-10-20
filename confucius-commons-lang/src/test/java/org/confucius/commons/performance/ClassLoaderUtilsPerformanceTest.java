package org.confucius.commons.performance;

import org.confucius.commons.lang.ClassLoaderUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

/**
 * {@link ClassLoaderUtils} Performance Test
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassLoaderUtilsPerformanceTest
 * @since 1.0.0
 */
@Ignore
public class ClassLoaderUtilsPerformanceTest extends AbstractPerformanceTest {


    @Test
    public void testFind() {
        super.execute(new PerformanceAction<Set<Class<?>>>() {
            @Override
            public Set<Class<?>> execute() {
                return ClassLoaderUtils.findLoadedClassesInClassPath(classLoader);
            }
        });

    }
}
