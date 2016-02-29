/**
 *
 */
package org.confucius.commons.web.servlet.util;

import org.confucius.commons.lang.ClassLoaderUtil;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * {@link ServletContext} utility class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ServletContext
 * @since 1.0.0 2016-02-29
 */
public class ServletContextUtil {

    /**
     * Load class under specified {@link ServletContext}
     *
     * @param servletContext
     *         {@link ServletContext}
     * @param className
     *         class name
     * @param <T>
     *         the loaded type
     * @return the loaded type
     */
    @Nonnull
    public static <T> Class<T> loadClass(ServletContext servletContext, String className) {
        ClassLoader classLoader = servletContext.getClass().getClassLoader();
        return (Class<T>) ClassLoaderUtil.loadClass(classLoader, className);
    }

    /**
     * Load instance under specified {@link ServletContext}
     *
     * @param servletContext
     *         {@link ServletContext}
     * @param className
     *         class name
     * @param <T>
     *         the loaded type
     * @return instance
     * @throws ServletException
     *         If can't initialize instance for specified class name
     */
    @Nonnull
    public static <T> T loadInstance(ServletContext servletContext, String className) throws ServletException {
        Class<T> type = loadClass(servletContext, className);
        T instance = null;
        try {
            instance = type.newInstance();
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return instance;
    }
}
