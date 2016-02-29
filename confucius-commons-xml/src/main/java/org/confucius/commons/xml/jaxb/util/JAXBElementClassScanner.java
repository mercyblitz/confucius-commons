/**
 * Project   : commons-xml File      : XmlRootElementAnnotatedClassScanner.java Date      : 2012-1-9 Time      :
 * ����11:32:43 Copyright : taobao.com Ltd.
 */
package org.confucius.commons.xml.jaxb.util;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.confucius.commons.lang.filter.FilterUtil;
import org.confucius.commons.lang.io.scanner.SimpleClassScanner;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;

/**
 * JAXB Class Scanner
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @since 1.0.0
 */
class JAXBElementClassScanner {

    final private SimpleClassScanner classScanner = SimpleClassScanner.INSTANCE;

    final private JAXBElementClassFilter classFilter = new JAXBElementClassFilter();

    /**
     * @param classLoader
     * @param packageName
     * @return
     * @throws NullPointerException
     */
    @Nonnull
    public Set<Class<?>> scan(@Nonnull ClassLoader classLoader, @Nonnull String packageName) throws NullPointerException {
        if (classLoader == null || StringUtils.isBlank(packageName)) {
            throw new NullPointerException();
        }
        Set<Class<?>> classes = Sets.newLinkedHashSet(FilterUtil.filter(classScanner.scan(classLoader, packageName, false, true), classFilter));

        return classes;
    }
}
