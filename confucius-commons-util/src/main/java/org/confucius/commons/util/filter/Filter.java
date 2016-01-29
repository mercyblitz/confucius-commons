/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.util.filter;

/**
 * {@link Filter} interface
 *
 * @param <T>
 *         the type of Filtered object
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see Filter
 * @since 1.0.0
 */
public interface Filter<T> {

    /**
     * Does accept filtered object?
     *
     * @param filteredObject
     *         filtered object
     * @return
     */
    boolean accept(T filteredObject);
}
