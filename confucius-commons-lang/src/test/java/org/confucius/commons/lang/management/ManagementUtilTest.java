package org.confucius.commons.lang.management;

import junit.framework.TestCase;
import org.confucius.commons.lang.AbstractTestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link ManagementUtil} {@link TestCase}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ManagementUtil
 * @since 1.0.0 2016-03-23
 */
public class ManagementUtilTest extends AbstractTestCase {

    @Test
    public void testStaticFields() {
        Assert.assertNotNull(ManagementUtil.managementFactoryClass);
        Assert.assertNotNull(ManagementUtil.jvm);
        Assert.assertNotNull(ManagementUtil.getProcessIdMethod);
    }

    @Test
    public void testGetCurrentProcessId() {
        int currentProcessId = ManagementUtil.getCurrentProcessId();
        Assert.assertTrue(currentProcessId > 0);
    }

}
