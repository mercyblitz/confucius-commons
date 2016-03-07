/**
 * Confucius commmons project
 */
package org.confucius.commons.web.servlet.util;

import org.apache.commons.lang3.StringUtils;
import org.confucius.commons.lang.constants.PathConstants;
import org.confucius.commons.lang.net.URLUtil;
import org.confucius.commons.web.http.constants.HttpHeaderConstants;
import org.confucius.commons.web.servlet.constants.ServletRequestAttributeNameConstants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * {@link ServletRequest} Utility class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ServletRequest
 * @since 1.0.0
 */
public class ServletRequestUtil {

    @Nullable
    public static Integer getErrorStatusCode(ServletRequest request) {
        return (Integer) request.getAttribute(ServletRequestAttributeNameConstants.Error.STATUS_CODE);
    }

    public static Class<? extends Throwable> getExceptionType(ServletRequest request) {
        return (Class<? extends Throwable>) request.getAttribute(ServletRequestAttributeNameConstants.Error.EXCEPTION_TYPE);
    }

    public static <T extends Throwable> T getException(ServletRequest request) {
        return (T) request.getAttribute(ServletRequestAttributeNameConstants.Error.EXCEPTION);
    }

    public static String getExceptionMessage(ServletRequest request) {
        return (String) request.getAttribute(ServletRequestAttributeNameConstants.Error.MESSAGE);
    }

    public static String getExceptionRequestURI(ServletRequest request) {
        return (String) request.getAttribute(ServletRequestAttributeNameConstants.Error.REQUEST_URI);
    }

    public static String getExceptionServletName(ServletRequest request) {
        return (String) request.getAttribute(ServletRequestAttributeNameConstants.Error.SERVLET_NAME);
    }

    public static boolean isIncludedRequest(ServletRequest request) {
        return request.getAttribute(ServletRequestAttributeNameConstants.Include.REQUEST_URI) != null;
    }

    public static boolean isForwardedRequest(ServletRequest request) {
        return request.getAttribute(ServletRequestAttributeNameConstants.Forward.REQUEST_URI) != null;
    }

    public static boolean isErrorRequest(ServletRequest request) {
        return request.getAttribute(ServletRequestAttributeNameConstants.Error.REQUEST_URI) != null;
    }

    /**
     * Get query string from {@link HttpServletRequest request}
     *
     * @param request
     *         HttpServletRequest
     * @return query string if present
     */
    public static String getRequestQueryString(HttpServletRequest request) {

        String queryString = (String) request.getAttribute(ServletRequestAttributeNameConstants.Include.QUERY_STRING);

        if (queryString == null) {
            queryString = (String) request.getAttribute(ServletRequestAttributeNameConstants.Forward.QUERY_STRING);
        }

        if (queryString == null) {
            queryString = request.getQueryString();
        }

        return queryString;
    }

    /**
     * Get the whole request URL
     *
     * @param request
     *         HttpServletRequest
     * @return the whole request URL without duplicated slash
     */
    @Nonnull
    public static String getRequestURL(HttpServletRequest request) {
        return getRootURL(request) + getRequestURI(request);
    }

    /**
     * Gets the string representation of URI of HTTP request.
     * <p/>
     * The URI will remove duplicated slash <code>PathConstants.SLASH</code>. For example, The origin request URI is
     * <code>"//mytemplate//////index.html"</code>, the result of
     * <p/>
     * invocation current method will be <code>"/mytemplate/index.html"</code>.
     *
     * @param request
     * @return the URL of HTTP request.
     * @since 1.0.0
     */
    public static String getRequestURI(HttpServletRequest request) {

        if (request == null)
            throw new NullPointerException("request argument must not be null!");

        String requestURI = (String) request.getAttribute(ServletRequestAttributeNameConstants.Include.REQUEST_URI);

        if (requestURI == null) {
            requestURI = (String) request.getAttribute(ServletRequestAttributeNameConstants.Forward.REQUEST_URI);
        }

        if (requestURI == null) {
            requestURI = (String) request.getAttribute(ServletRequestAttributeNameConstants.Error.REQUEST_URI);
        }

        if (requestURI == null) {
            requestURI = request.getRequestURI();
        }

        requestURI = URLUtil.resolvePath(requestURI);

        return requestURI;
    }

    /**
     * Get Root URL
     *
     * @param request
     *         HttpServletRequest
     * @return root url without duplicated slash
     */
    @Nonnull
    public static String getRootURL(HttpServletRequest request) {
        String host = request.getServerName();
        int port = request.getLocalPort();
        String scheme = request.getScheme();
        String contextPath = request.getContextPath();

        StringBuilder urlBuilder = new StringBuilder(scheme).append("://").append(host);

        if ("http".equals(scheme)) {
            if (port != 80) {
                urlBuilder.append(":").append(port);
            }
        } else if ("https".equals(scheme)) {
            if (port != 443) {
                urlBuilder.append(":").append(port);
            }
        }

        if (!PathConstants.SLASH.equals(contextPath)) {
            urlBuilder.append(contextPath);
        }

        return urlBuilder.toString();
    }

    /**
     * Get context request URL
     *
     * @param request
     * @return context request URL without duplicated slash
     */
    @Nonnull
    public static String getContextRequestURL(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String rootURL = getRootURL(request);
        //如果上下文路径不等于PathConstants.SLASH
        if (PathConstants.SLASH.equals(contextPath)) {
            return rootURL;
        } else {
            return rootURL + contextPath;
        }
    }

    /**
     * Get Referer URI
     *
     * @param request
     *         HttpServletRequest
     * @return Get Referer URI
     * @version 1.0.0
     * @since 1.0.0 2011-11-8
     */
    public static String getRefererURI(HttpServletRequest request) {
        String referer = getReferer(request);
        if (referer == null) {
            return null;
        }
        String contextURL = getContextRequestURL(request);
        String uri = StringUtils.substringAfter(referer, contextURL);
        if (!uri.startsWith(PathConstants.SLASH)) {
            uri = PathConstants.SLASH + uri;
        }
        return uri;
    }

    /**
     * Get Referer URL
     *
     * @param request
     *         HttpServletRequest
     * @return Get Referer URL
     * @version 1.0.0
     * @since 1.0.0 2011-11-7
     */
    public static String getReferer(HttpServletRequest request) {
        String referer = request.getHeader(HttpHeaderConstants.Request.REFERER);
        return referer;
    }

    /**
     * Get cookie from {@link HttpServletRequest Http reuqest}
     *
     * @param request
     *         HttpServletRequest
     * @param cookieName
     *         the name of {@link Cookie}
     * @return
     * @version 1.0.0
     * @see
     * @since 1.0.0
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length < 1)
            return null;

        for (Cookie cookie : cookies) {
            String cookieName_ = cookie.getName();
            if (cookieName_.equals(cookieName)) {
                return cookie;
            }
        }

        return null;
    }

    /**
     * Get context name from {@link HttpServletRequest request}
     *
     * @param <T>
     * @param request
     *         HttpServletRequest
     * @param attributeName
     *         attribute name
     * @return context name from {@link HttpServletRequest request}
     * @version 1.0.0
     * @see HttpServletRequest
     * @since 1.0.0 2011-10-21
     */
    public static <T> T getAttribute(ServletRequest request, String attributeName) {
        T value = (T) request.getAttribute(attributeName);
        return value;
    }

    /**
     * Get ServletContext
     *
     * @param request
     *         Get ServletContext
     * @return Get ServletContext
     * @version 1.0.0
     * @see
     * @since 1.0.0 2011-8-25
     */
    public static ServletContext getServletContext(HttpServletRequest request) {
        return request.getSession().getServletContext();
    }

    /**
     * Resolve request URI
     *
     * @param request
     *         HttpServletRequest
     * @return Resolved request URI
     * @version 1.0.0
     * @see HttpServletRequest#getRequestURI()
     * @since 1.0.0 2011-10-28
     */
    public static String resolveRequestURI(HttpServletRequest request) {
        return URLUtil.resolvePath(request.getRequestURI());
    }
}
