/**
 *
 */
package org.confucius.commons.lang.util.jar;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.confucius.commons.lang.constants.FileSuffixConstants;
import org.confucius.commons.lang.constants.PathConstants;
import org.confucius.commons.lang.constants.ProtocolConstants;
import org.confucius.commons.lang.filter.JarEntryFilter;
import org.confucius.commons.lang.net.URLUtil;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Jar Utility class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see JarEntry
 * @see JarFile
 * @since 1.0.0
 */
public class JarUtil {


    /**
     * Create a {@link JarFile} from specified {@link URL} of {@link JarFile}
     *
     * @param jarURL
     *         {@link URL} of {@link JarFile} or {@link JarEntry}
     * @return JarFile
     * @throws IOException
     *         If {@link JarFile jar file} is invalid, see {@link JarFile#JarFile(String)}
     * @version 1.0.0
     * @since 1.0.0
     */
    public static JarFile toJarFile(URL jarURL) throws IOException {
        JarFile jarFile = null;
        final String jarAbsolutePath = resolveJarAbsolutePath(jarURL);
        if (jarAbsolutePath == null)
            return null;
        jarFile = new JarFile(jarAbsolutePath);
        return jarFile;
    }

    /**
     * Assert <code>jarURL</code> argument is valid , only supported protocols : {@link ProtocolConstants#JAR jar} and
     * {@link ProtocolConstants#FILE file}
     *
     * @param jarURL
     *         {@link URL} of {@link JarFile} or {@link JarEntry}
     * @throws NullPointerException
     *         If <code>jarURL</code> is <code>null</code>
     * @throws IllegalArgumentException
     *         If {@link URL#getProtocol()} is not {@link ProtocolConstants#JAR jar} or {@link ProtocolConstants#FILE
     *         file}
     */
    protected static void assertJarURLProtocol(URL jarURL) throws NullPointerException, IllegalArgumentException {
        final String protocol = jarURL.getProtocol(); //NPE check
        if (!ProtocolConstants.JAR.equals(protocol) && !ProtocolConstants.FILE.equals(protocol)) {
            String message = String.format("jarURL Protocol[%s] is unsupported ,except %s and %s ", protocol, ProtocolConstants.JAR, ProtocolConstants.FILE);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Resolve Relative path from Jar URL
     *
     * @param jarURL
     *         {@link URL} of {@link JarFile} or {@link JarEntry}
     * @return Non-null
     * @throws NullPointerException
     *         see {@link #assertJarURLProtocol(URL)}
     * @throws IllegalArgumentException
     *         see {@link #assertJarURLProtocol(URL)}
     * @version 1.0.0
     * @since 1.0.0 2012-3-20 下午02:37:25
     */
    @Nonnull
    public static String resolveRelativePath(URL jarURL) throws NullPointerException, IllegalArgumentException {
        assertJarURLProtocol(jarURL);
        String form = jarURL.toExternalForm();
        String relativePath = StringUtils.substringAfter(form, "!/");
        return URLUtil.decode(relativePath);
    }

    /**
     * Resolve absolute path from the {@link URL} of {@link JarEntry}
     *
     * @param jarURL
     *         {@link URL} of {@link JarFile} or {@link JarEntry}
     * @return If {@link URL#getProtocol()} equals <code>jar</code> or <code>file</code> , resolves absolute path, or
     * return <code>null</code>
     * @throws NullPointerException
     *         see {@link #assertJarURLProtocol(URL)}
     * @throws IllegalArgumentException
     *         see {@link #assertJarURLProtocol(URL)}
     * @version 1.0.0
     * @since 1.0.0
     */
    @Nonnull
    public static String resolveJarAbsolutePath(URL jarURL) throws NullPointerException, IllegalArgumentException {
        assertJarURLProtocol(jarURL);
        String jarURLPath = jarURL.toExternalForm();
        String jarPath = PathConstants.SLASH + StringUtils.substringBetween(jarURLPath, ":/", FileSuffixConstants.JAR) + FileSuffixConstants.JAR;
        jarPath = URLUtil.decode(jarPath);
        File jarFile = new File(jarPath);
        return jarFile.exists() ? jarFile.getAbsolutePath() : null;
    }

    /**
     * Filter {@link JarEntry} list from {@link JarFile}
     *
     * @param jarFile
     *         {@link JarFile}
     * @param jarEntryFilter
     *         {@link JarEntryFilter}
     * @return Read-only List
     */
    @Nonnull
    public static List<JarEntry> filter(JarFile jarFile, JarEntryFilter jarEntryFilter) {
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        List<JarEntry> jarEntriesList = Lists.newLinkedList();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if (jarEntryFilter == null || jarEntryFilter.accept(jarEntry)) {
                jarEntriesList.add(jarEntry);
            }
        }
        return Collections.unmodifiableList(jarEntriesList);
    }


}
