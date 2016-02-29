/**
 *
 */
package org.confucius.commons.web.http.constants;

/**
 * HTTP Header constants
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see HttpHeaderConstants
 * @since 1.0.0 2016-02-29
 */
public interface HttpHeaderConstants {

    /**
     * Connection
     */
    String CONNECTION = "Connection";

    /**
     * Request
     */
    interface Request {

        /**
         * If-None-Match
         */
        String IF_NONE_MATCH = "If-None-Match";

        /**
         * If-Modified-Since
         */
        String IF_MODIFIED_SINCE = "If-Modified-Since";

        /**
         * Referer
         */
        String REFERER="Referer";

    }

    /**
     * Response
     */
    interface Response {

        /**
         * ETag
         */
        String E_TAG = "ETag";


        /**
         * Last-Modified
         */
        String LAST_MODIFIED = "Last-Modified";

        /**
         * Date
         */
        String DATE = "Date";

        /**
         * Expires
         */
        String EXPIRES = "Expires";

        /**
         * Cache-Control
         */
        String CACHE_CONTROL = "Cache-Control";

    }
}
