/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.lang;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * {@link ClassPathUtil} {@link Test}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassPathUtilTest
 * @since 1.0.0
 */
public class ClassPathUtilTest extends AbstractTestCase {

    @Test
    public void testGetBootstrapClassPaths() {
        Set<String> bootstrapClassPaths = ClassPathUtil.getBootstrapClassPaths();
        Assert.assertNotNull(bootstrapClassPaths);
        Assert.assertFalse(bootstrapClassPaths.isEmpty());
        echo(bootstrapClassPaths);
    }


    @Test
    public void testGetClassPaths() {
        Set<String> classPaths = ClassPathUtil.getClassPaths();
        Assert.assertNotNull(classPaths);
        Assert.assertFalse(classPaths.isEmpty());
        echo(classPaths);
    }
}
