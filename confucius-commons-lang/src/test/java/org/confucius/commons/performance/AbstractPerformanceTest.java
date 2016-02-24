package org.confucius.commons.performance;

import org.confucius.commons.lang.AbstractTestCase;
import org.junit.Ignore;

/**
 * {@link AbstractPerformanceTest}
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see AbstractPerformanceTest
 * @since 1.0.0 2016-01-31
 */
@Ignore
public abstract class AbstractPerformanceTest extends AbstractTestCase {


    protected <T> void execute(PerformanceAction<T> action) {
        long startTime = System.currentTimeMillis();
        T returnValue = action.execute();
        long costTime = System.currentTimeMillis() - startTime;
        String message = String.format("execution %s return %s costs %s ms", action, returnValue, costTime);
        echo(message);
    }

}
