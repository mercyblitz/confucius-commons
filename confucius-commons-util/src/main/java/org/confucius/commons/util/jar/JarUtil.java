/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.util.jar;

import org.apache.commons.lang3.StringUtils;
import org.confucius.commons.lang.constants.FileSuffixConstants;
import org.confucius.commons.lang.constants.PathConstants;
import org.confucius.commons.lang.constants.ProtocolConstants;
import org.confucius.commons.lang.net.URLUtil;

import java.io.File;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Jar Utility class
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see JarEntry
 * @see JarFile
 * @since 1.0.0
 */
public class JarUtil {

    /**
     * 通过 jar包中实体URL，如果jarEntryURL的协议为jar或file，返回Jar包的证实物理位置。否则返回 <code>null</code>。
     *
     * @param jarEntryURL
     *         The URL of {@link JarEntry}
     * @return 如果jarEntryURL的协议为jar，返回Jar包的证实物理位置。否则返回<code>null</code>。
     * @version 1.0.0
     * @since 1.0.0
     */
    public static String resolveJarAbsolutePath(URL jarEntryURL) {
        String protocol = jarEntryURL.getProtocol();
        if (ProtocolConstants.JAR.equals(protocol) || ProtocolConstants.FILE.equals(protocol)) {
            String jarEntryURLPath = jarEntryURL.toExternalForm();
            String jarPath = PathConstants.SLASH + StringUtils.substringBetween(jarEntryURLPath, ":/", FileSuffixConstants.JAR) + FileSuffixConstants.JAR;
            jarPath = URLUtil.decode(jarPath);
            File jarFile = new File(jarPath);
            return jarFile.exists() ? jarFile.getAbsolutePath() : null;
        }
        return null;
    }
}
