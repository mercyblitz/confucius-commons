package org.confucius.commons.performance;

import org.confucius.commons.lang.ClassLoaderUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

/**
 * {@link ClassLoaderUtil} Performance Test
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassLoaderUtilPerformanceTest
 * @since 1.0.0 2016-01-31
 */
@Ignore
public class ClassLoaderUtilPerformanceTest extends AbstractPerformanceTest {


    @Test
    public void testFind() {
        super.execute(new PerformanceAction<Set<Class<?>>>() {
            @Override
            public Set<Class<?>> execute() {
                return ClassLoaderUtil.findLoadedClassesInClassPath(classLoader);
            }
        });

    }
}
