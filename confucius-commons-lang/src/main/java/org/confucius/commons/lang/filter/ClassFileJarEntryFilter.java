/**
 *
 */
package org.confucius.commons.lang.filter;

import org.confucius.commons.lang.constants.FileSuffixConstants;

import java.util.jar.JarEntry;

/**
 * Class File {@link JarEntryFilter}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassFileJarEntryFilter
 * @since 1.0.0
 */
public class ClassFileJarEntryFilter implements JarEntryFilter {

    /**
     * {@link ClassFileJarEntryFilter} Singleton instance
     */
    public static final ClassFileJarEntryFilter INSTANCE = new ClassFileJarEntryFilter();

    protected ClassFileJarEntryFilter() {

    }

    @Override
    public boolean accept(JarEntry jarEntry) {
        return !jarEntry.isDirectory() && jarEntry.getName().endsWith(FileSuffixConstants.CLASS);
    }
}
