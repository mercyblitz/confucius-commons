package org.confucius.commons.lang;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.lang.reflect.Array;

/**
 * {@link ReflectionUtil} {@link TestCase}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ReflectionUtilTest
 * @since 1.0.0 2016-01-28
 */
public class ReflectionUtilTest extends TestCase {

    public void testAssertArrayIndex() {
        int size = 10;
        Object array = Array.newInstance(int.class, size);

        for (int i = 0; i < size; i++) {
            ReflectionUtil.assertArrayIndex(array, i);
        }

        for (int i = size; i < size * 2; i++) {
            ArrayIndexOutOfBoundsException exception = null;
            try {
                ReflectionUtil.assertArrayIndex(array, i);
            } catch (ArrayIndexOutOfBoundsException e) {
                exception = e;
                System.err.println(e.getMessage());
            }
            Assert.assertNotNull(exception);
        }
    }

    public void testAssertArrayTypeOnException() {
        IllegalArgumentException exception = null;
        try {
            ReflectionUtil.assertArrayType(new Object());
        } catch (IllegalArgumentException e) {
            exception = e;
            System.err.println(e.getMessage());
        }
        Assert.assertNotNull(exception);
    }

    public void testAssertArrayType() {
        testAssertArrayType(long.class);
        testAssertArrayType(int.class);
        testAssertArrayType(short.class);
        testAssertArrayType(byte.class);
        testAssertArrayType(boolean.class);
        testAssertArrayType(double.class);
        testAssertArrayType(float.class);
        testAssertArrayType(char.class);
        testAssertArrayType(String.class);
        testAssertArrayType(Object.class);
    }

    private void testAssertArrayType(Class<?> type) {
        Object array = Array.newInstance(type, 0);
        ReflectionUtil.assertArrayType(array);
    }

    public void testGetCallerClassX() throws Exception {
        Class<?> expectedClass = ReflectionUtilTest.class;

        Class<?> callerClass = ReflectionUtil.getCallerClass();
        Class<?> callerClassInSunJVM = ReflectionUtil.getCallerClassInSunJVM();
        Class<?> callerClassInGeneralJVM = ReflectionUtil.getCallerClassInGeneralJVM();

        Assert.assertEquals(expectedClass, callerClass);
        Assert.assertEquals(callerClassInSunJVM, callerClass);
        Assert.assertEquals(callerClassInGeneralJVM, callerClass);

    }

    public void testGetCallerClassName() {
        String expectedClassName = ReflectionUtilTest.class.getName();
        String callerClassName = ReflectionUtil.getCallerClassName();
        String callerClassNameInSunJVM = ReflectionUtil.getCallerClassNameInSunJVM();
        String callerClassNameInGeneralJVM = ReflectionUtil.getCallerClassNameInGeneralJVM();

        Assert.assertEquals(expectedClassName, callerClassName);
        Assert.assertEquals(callerClassNameInSunJVM, callerClassName);
        Assert.assertEquals(callerClassNameInGeneralJVM, callerClassName);
    }

    public void testGetCallerPackage() {
        Class<?> expectedClass = ReflectionUtilTest.class;
        Package expectedPackage = expectedClass.getPackage();
    }
}
