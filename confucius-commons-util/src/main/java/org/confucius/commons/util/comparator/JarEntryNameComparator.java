/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.util.comparator;

import java.util.Comparator;
import java.util.jar.JarEntry;

/**
 * {@link JarEntry#getName()}  JarEntry Name} {@link Comparator}
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see JarEntryNameComparator
 * @since 1.0.0
 */
public class JarEntryNameComparator implements Comparator<JarEntry> {

    @Override
    public int compare(JarEntry one, JarEntry another) {
        String oneName = one.getName();
        String anotherName = another.getName();
        return oneName.compareTo(anotherName);
    }
}
