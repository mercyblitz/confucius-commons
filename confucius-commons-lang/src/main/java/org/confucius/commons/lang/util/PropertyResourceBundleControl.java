/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.lang.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link PropertyResourceBundle} {@link ResourceBundle.Control} Implementation which supports encoding {@link
 * Properties} files
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see PropertyResourceBundle
 * @see ResourceBundle.Control
 * @since 1.0.0
 */
public class PropertyResourceBundleControl extends ResourceBundle.Control {

    private static final ConcurrentMap<String, ResourceBundle.Control> encodingControlMap = new ConcurrentHashMap<String, ResourceBundle.Control>();

    static {
        addEncodingControlMap(newControl(SystemUtils.FILE_ENCODING));
        addEncodingControlMap(newControl("UTF-8"));
    }

    private final String encoding;

    protected PropertyResourceBundleControl(final String encoding) {
        this.encoding = encoding;
    }

    protected PropertyResourceBundleControl() {
        this(SystemUtils.FILE_ENCODING);
    }

    private static final void addEncodingControlMap(ResourceBundle.Control control) {
        PropertyResourceBundleControl control_ = (PropertyResourceBundleControl) control;
        encodingControlMap.putIfAbsent(control_.getEncoding(), control_);
    }

    /**
     * Gets an existed instance of {@link PropertyResourceBundleControl}.
     *
     * @param encoding
     * @return an existed instance of {@link PropertyResourceBundleControl}.
     * @version 1.0.0
     * @since 1.0.0
     */
    private static final ResourceBundle.Control getControl(final String encoding) {
        return encodingControlMap.get(encoding);
    }

    /**
     * Creates a new instance of {@link PropertyResourceBundleControl} if absent.
     *
     * @param encoding
     *         Encoding
     * @return Control
     * @throws UnsupportedCharsetException
     *         If <code>encoding</code> is not supported
     * @version 1.0.0
     * @since 1.0.0
     */
    public static final ResourceBundle.Control newControl(final String encoding) throws UnsupportedCharsetException {
        // check encoding
        Charset.forName(encoding);
        final ResourceBundle.Control existedControl = getControl(encoding);
        ResourceBundle.Control control = existedControl;
        if (existedControl == null) {
            control = new PropertyResourceBundleControl(encoding);
            encodingControlMap.put(encoding, control);
        }
        return control;
    }

    public final List<String> getFormats(String baseName) {
        if (baseName == null) {
            throw new NullPointerException();
        }
        return FORMAT_PROPERTIES;
    }


    public ResourceBundle newBundle(String baseName, Locale locale, String format, final ClassLoader classLoader, final boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        String bundleName = super.toBundleName(baseName, locale);
        final String resourceName = super.toResourceName(bundleName, "properties");
        InputStream stream = null;
        Reader reader = null;
        ResourceBundle bundle = null;
        try {
            stream = AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() {
                public InputStream run() throws IOException {
                    InputStream is = null;
                    if (reload) {
                        URL url = classLoader.getResource(resourceName);
                        if (url != null) {
                            URLConnection connection = url.openConnection();
                            if (connection != null) {
                                // Disable caches to get fresh data for
                                // reloading.
                                connection.setUseCaches(false);
                                is = connection.getInputStream();
                            }
                        }
                    } else {
                        is = classLoader.getResourceAsStream(resourceName);
                    }
                    return is;
                }
            });
        } catch (PrivilegedActionException e) {
            throw (IOException) e.getException();
        }

        if (stream != null) {
            try {
                reader = new InputStreamReader(stream, this.getEncoding());
                bundle = new PropertyResourceBundle(reader);
            } finally {
                IOUtils.closeQuietly(stream);
                IOUtils.closeQuietly(reader);
            }
        }

        return bundle;
    }

    /**
     * Sets the encoding of properties file.
     *
     * @return the encoding
     * @version 1.0.0
     * @since 1.0.0
     */
    public String getEncoding() {
        return encoding;
    }
}
