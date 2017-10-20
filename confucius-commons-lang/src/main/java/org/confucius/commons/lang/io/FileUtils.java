/**
 *
 */
package org.confucius.commons.lang.io;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.confucius.commons.lang.net.URLUtils;

import java.io.File;

/**
 * {@link File} Utility
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see FileUtils
 * @since 1.0.0
 */
public abstract class FileUtils {

    /**
     * Resolve Relative Path
     *
     * @param parentDirectory
     *         Parent Directory
     * @param targetFile
     *         Target File
     * @return If <code>targetFile</code> is a sub-file of <code>parentDirectory</code> , resolve relative path, or
     * <code>null</code>
     * @since 1.0.0
     */
    public static String resolveRelativePath(File parentDirectory, File targetFile) {
        String parentDirectoryPath = parentDirectory.getAbsolutePath();
        String targetFilePath = targetFile.getAbsolutePath();
        if (!targetFilePath.contains(parentDirectoryPath)) {
            return null;
        }
        return URLUtils.resolvePath(StringUtils.replace(targetFilePath, parentDirectoryPath, SystemUtils.FILE_SEPARATOR));
    }
}
