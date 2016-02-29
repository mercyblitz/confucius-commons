/**
 * Project   : designrule File      : XmlRootElementAnnotatedClassFilter.java Date      : 2012-1-5 Time      :
 * ÏÂÎç05:23:16 Copyright : taobao.com Ltd.
 */
package org.confucius.commons.xml.jaxb.util;


import org.apache.commons.lang3.ArrayUtils;
import org.confucius.commons.lang.filter.ClassFilter;

import javax.xml.bind.annotation.XmlRootElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

/**
 * {@link XmlRootElement} {@link ClassFilter}
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see XmlRootElement
 * @since 1.0.0
 */
class JAXBElementClassFilter implements ClassFilter {

    public boolean accept(Class<?> type) {
        boolean accepted = false;
        if (!Modifier.isAbstract(type.getModifiers())) {
            Package jaxbPackage = XmlRootElement.class.getPackage();
            Annotation[] annotations = type.getAnnotations();
            if (ArrayUtils.isNotEmpty(annotations)) {
                for (Annotation annotation : annotations) {
                    if (jaxbPackage.equals(annotation.annotationType().getPackage())) {
                        accepted = true;
                        break;
                    }
                }
            }
        }
        return accepted;
    }

}
