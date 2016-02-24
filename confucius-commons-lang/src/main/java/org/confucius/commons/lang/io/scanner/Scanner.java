/**
 *
 */
package org.confucius.commons.lang.io.scanner;


import org.confucius.commons.lang.filter.Filter;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * {@link Scanner}
 *
 * @param <S>
 *         the type of scanned source
 * @param <R>
 *         the type of scan result
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see Scanner
 * @since 1.0.0
 */
public interface Scanner<S, R> {

    /**
     * Scan source to calculate result set
     *
     * @param source
     *         scanned source
     * @return result set , non-null
     * @throws IllegalArgumentException
     *         scanned source is not legal
     * @throws IllegalStateException
     *         scanned source's state is not valid
     */
    @Nonnull
    Set<R> scan(S source) throws IllegalArgumentException, IllegalStateException;

    /**
     * Scan source to calculate result set with {@link Filter}
     *
     * @param source
     *         scanned source
     * @param filter
     *         {@link Filter<R> filter} to accept result
     * @return result set , non-null
     * @throws IllegalArgumentException
     *         scanned source is not legal
     * @throws IllegalStateException
     *         scanned source's state is not valid
     */
    @Nonnull
    Set<R> scan(S source, Filter<R> filter) throws IllegalArgumentException, IllegalStateException;

}
