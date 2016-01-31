package org.confucius.commons.performance;

/**
 * {@link PerformanceAction}
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see PerformanceAction
 * @since 1.0.0 2016-01-31
 */
public interface PerformanceAction<T> {

    T execute();

}
