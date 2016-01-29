/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.util;

import org.apache.commons.lang3.SystemUtils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * {@link PropertyResourceBundle} Utility class
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see PropertyResourceBundle
 * @since 1.0.0
 */
public abstract class PropertyResourceBundleUtil {

    private PropertyResourceBundleUtil() {
    }

    /**
     * {@link ResourceBundle#getBundle(String, Locale)} with {@link SystemUtils#FILE_ENCODING default file encoding}
     * encoding and {@link Locale#getDefault() default Locale} under {@link Thread#getContextClassLoader() Thread
     * context ClassLoader}
     *
     * @param baseName
     *         the base name of the resource bundle, a fully qualified class name
     * @return
     * @throws NullPointerException
     *         if <code>baseName</code>, <code>locale</code>, or <code>loader</code> is <code>null</code>
     * @throws MissingResourceException
     *         if no resource bundle for the specified base name can be found
     * @throws IllegalArgumentException
     *         if the given <code>control</code> doesn't perform properly (e.g., <code>control.getCandidateLocales</code>
     *         returns null.) Note that validation of <code>control</code> is performed as needed.
     */
    public static ResourceBundle getBundle(String baseName) {
        return getBundle(baseName, SystemUtils.FILE_ENCODING);
    }

    /**
     * {@link ResourceBundle#getBundle(String, Locale)} with specified encoding and {@link Locale#getDefault() default
     * Locale} under {@link Thread#getContextClassLoader() Thread context ClassLoader}
     *
     * @param baseName
     *         the base name of the resource bundle, a fully qualified class name
     * @param encoding
     *         the control which gives information for the resource bundle loading process
     * @return
     * @throws NullPointerException
     *         if <code>baseName</code> is <code>null</code>
     * @throws MissingResourceException
     *         if no resource bundle for the specified base name can be found
     * @throws IllegalArgumentException
     *         if the given <code>control</code> doesn't perform properly (e.g., <code>control.getCandidateLocales</code>
     *         returns null.) Note that validation of <code>control</code> is performed as needed.
     */
    public static ResourceBundle getBundle(String baseName, String encoding) {
        return getBundle(baseName, Locale.getDefault(), encoding);
    }

    /**
     * {@link ResourceBundle#getBundle(String, Locale)} with specified encoding under {@link
     * Thread#getContextClassLoader() Thread context ClassLoader}
     *
     * @param baseName
     *         the base name of the resource bundle, a fully qualified class name
     * @param locale
     *         the locale for which a resource bundle is desired
     * @param encoding
     *         the control which gives information for the resource bundle loading process
     * @return
     * @throws NullPointerException
     *         if <code>baseName</code>, <code>locale</code> is <code>null</code>
     * @throws MissingResourceException
     *         if no resource bundle for the specified base name can be found
     * @throws IllegalArgumentException
     *         if the given <code>control</code> doesn't perform properly (e.g., <code>control.getCandidateLocales</code>
     *         returns null.) Note that validation of <code>control</code> is performed as needed.
     */
    public static ResourceBundle getBundle(String baseName, Locale locale, String encoding) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return getBundle(baseName, locale, classLoader, encoding);
    }

    /**
     * {@link ResourceBundle#getBundle(String, Locale, ClassLoader)} with specified encoding
     *
     * @param baseName
     *         the base name of the resource bundle, a fully qualified class name
     * @param locale
     *         the locale for which a resource bundle is desired
     * @param classLoader
     *         the class loader from which to load the resource bundle
     * @param encoding
     *         the control which gives information for the resource bundle loading process
     * @return
     * @throws NullPointerException
     *         if <code>baseName</code>, <code>locale</code>, or <code>loader</code> is <code>null</code>
     * @throws MissingResourceException
     *         if no resource bundle for the specified base name can be found
     * @throws IllegalArgumentException
     *         if the given <code>control</code> doesn't perform properly (e.g., <code>control.getCandidateLocales</code>
     *         returns null.) Note that validation of <code>control</code> is performed as needed.
     */
    public static ResourceBundle getBundle(String baseName, Locale locale, ClassLoader classLoader, String encoding) {
        ResourceBundle.Control control = PropertyResourceBundleControl.newControl(encoding);
        return ResourceBundle.getBundle(baseName, locale, classLoader, control);
    }


}
