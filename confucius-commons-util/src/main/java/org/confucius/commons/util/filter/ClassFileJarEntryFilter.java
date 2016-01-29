/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.util.filter;

import java.util.jar.JarEntry;

/**
 * Class File {@link JarEntryFilter}
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
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
        return !jarEntry.isDirectory() && jarEntry.getName().endsWith(".class");
    }
}
