/**
 *
 */
package org.confucius.commons.web.servlet.constants;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * {@link ServletRequest} attribute Name constants
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ServletRequest#getAttribute(String)
 * @since 1.0.0 2016-02-29
 */
public interface ServletRequestAttributeNameConstants {

    /**
     * 9.3.1 Included Request Parameters The following request attributes must be set: these attributes must not be set.
     * javax.servlet.include.request_uri javax.servlet.include.context_path javax.servlet.include.servlet_path
     * javax.servlet.include.path_info javax.servlet.include.query_string
     */
    interface Include {

        /**
         * <code>"javax.servlet.include.request_uri"</code> is used to {@link javax.servlet.RequestDispatcher#include(ServletRequest,
         * ServletResponse) included}
         */
        String REQUEST_URI = "javax.servlet.include.request_uri";


        /**
         * <code>"javax.servlet.include.context_path"</code> is used to {@link javax.servlet.RequestDispatcher#include(ServletRequest,
         * ServletResponse) included}
         */
        String CONTEXT_PATH = "javax.servlet.include.context_path";


        /**
         * <code>"javax.servlet.include.servlet_path"</code> is used to {@link javax.servlet.RequestDispatcher#include(ServletRequest,
         * ServletResponse) included}
         */
        String SERVLET_PATH = "javax.servlet.include.servlet_path";

        /**
         * <code>"javax.servlet.include.path_info"</code> is used to {@link javax.servlet.RequestDispatcher#include(ServletRequest,
         * ServletResponse) included}
         */
        String PATH_INFO = "javax.servlet.include.path_info";


        /**
         * <code>"javax.servlet.include.query_string"</code> is used to {@link javax.servlet.RequestDispatcher#include(ServletRequest,
         * ServletResponse) included}
         */
        String QUERY_STRING = "javax.servlet.include.query_string";

    }

    /**
     * 9.4.2 Forwarded Request Parameters The following request attributes must be set: these attributes must not be
     * set. javax.servlet.forward.request_uri javax.servlet.forward.context_path javax.servlet.forward.servlet_path
     * javax.servlet.forward.path_info javax.servlet.forward.query_string
     */
    interface Forward {

        /**
         * <code>"javax.servlet.forward.request_uri"</code> is used to {@link javax.servlet.RequestDispatcher#forward(ServletRequest,
         * ServletResponse) forwarded}
         */
        String REQUEST_URI = "javax.servlet.forward.request_uri";


        /**
         * <code>"javax.servlet.forward.context_path"</code> is used to {@link javax.servlet.RequestDispatcher#forward(ServletRequest,
         * ServletResponse) forwarded}
         */
        String CONTEXT_PATH = "javax.servlet.forward.context_path";


        /**
         * <code>"javax.servlet.forward.servlet_path"</code> is used to {@link javax.servlet.RequestDispatcher#forward(ServletRequest,
         * ServletResponse) forward}
         */
        String SERVLET_PATH = "javax.servlet.forward.servlet_path";

        /**
         * <code>"javax.servlet.forward.path_info"</code> is used to {@link javax.servlet.RequestDispatcher#forward(ServletRequest,
         * ServletResponse) forwarded}
         */
        String PATH_INFO = "javax.servlet.forward.path_info";


        /**
         * <code>"javax.servlet.forward.query_string"</code> is used to {@link javax.servlet.RequestDispatcher#forward(ServletRequest,
         * ServletResponse) forwarded}
         */
        String QUERY_STRING = "javax.servlet.forward.query_string";

    }


    /**
     * 10.9 Error Handling
     * <p/>
     * TABLE 10-1  Request Attributes and their types Request Attributes  Type javax.servlet.error.status_code
     * java.lang.Integer javax.servlet.error.exception_type java.lang.Class javax.servlet.error.message java.lang.String
     * javax.servlet.error.exception java.lang.Throwable javax.servlet.error.request_uri java.lang.String
     * javax.servlet.error.servlet_name java.lang.String
     */
    interface Error {

        /**
         * <code>"javax.servlet.error.status_code"</code>
         */
        String STATUS_CODE = "javax.servlet.error.status_code";

        /**
         * <code>"javax.servlet.error.exception_type"</code>
         */
        String EXCEPTION_TYPE = "javax.servlet.error.exception_type";

        /**
         * <code>"javax.servlet.error.message"</code>
         */
        String MESSAGE = "javax.servlet.error.message";

        /**
         * <code>"javax.servlet.error.exception"</code>
         */
        String EXCEPTION = "javax.servlet.error.exception";

        /**
         * <code>"javax.servlet.error.request_uri"</code>
         */
        String REQUEST_URI = "javax.servlet.error.request_uri";

        /**
         * <code>"javax.servlet.error.servlet_name"</code>
         */
        String SERVLET_NAME = "javax.servlet.error.servlet_name";

    }
}
