package org.confucius.commons.lang.management;

import junit.framework.TestCase;
import org.confucius.commons.lang.AbstractTestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link ManagementUtils} {@link TestCase}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ManagementUtils
 * @since 1.0.0
 */
public class ManagementUtilsTest extends AbstractTestCase {

    @Test
    public void testStaticFields() {
        Assert.assertNotNull(ManagementUtils.jvm);
        Assert.assertNotNull(ManagementUtils.getProcessIdMethod);
    }

    @Test
    public void testGetCurrentProcessId() {
        int currentProcessId = ManagementUtils.getCurrentProcessId();
        Assert.assertTrue(currentProcessId > 0);
    }

}
