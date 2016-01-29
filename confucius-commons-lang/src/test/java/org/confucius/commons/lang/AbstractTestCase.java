/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.lang;

import junit.framework.TestCase;

/**
 * Abstract {@link TestCase}
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see AbstractTestCase
 * @since 1.0.0
 */
public class AbstractTestCase extends TestCase {

    public void echo(Object object, Object... others) {
        System.out.println(object);
        for (Object another : others) {
            System.out.println(another);
        }
    }
}
