/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.lang;

import junit.framework.TestCase;

import java.util.Iterator;

/**
 * Abstract {@link TestCase}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see AbstractTestCase
 * @since 1.0.0
 */
public abstract class AbstractTestCase {

    public void echo(Object object, Object... others) {
        echo(object);
        echo(others);
    }

    public void echo(Object object) {
        System.out.println(object);
    }

    public void echo(Iterable<?> iterable) {
        Iterator<?> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            echo(iterator.next());
        }
    }
}
