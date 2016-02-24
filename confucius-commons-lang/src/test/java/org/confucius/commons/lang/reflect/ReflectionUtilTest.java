package org.confucius.commons.lang.reflect;

import com.google.common.collect.Maps;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.confucius.commons.lang.AbstractTestCase;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * {@link ReflectionUtil} {@link TestCase}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ReflectionUtilTest
 * @since 1.0.0 2016-01-28
 */
public class ReflectionUtilTest extends AbstractTestCase {

    @Test
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

    @Test
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

    @Test
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

    @Test
    public void testGetCallerClassX() throws Exception {
        Class<?> expectedClass = ReflectionUtilTest.class;

        Class<?> callerClass = ReflectionUtil.getCallerClass();
        Class<?> callerClassInSunJVM = ReflectionUtil.getCallerClassInSunJVM();
        Class<?> callerClassInGeneralJVM = ReflectionUtil.getCallerClassInGeneralJVM();

        Assert.assertEquals(expectedClass, callerClass);
        Assert.assertEquals(callerClassInSunJVM, callerClass);
        Assert.assertEquals(callerClassInGeneralJVM, callerClass);

    }

    @Test
    public void testGetCallerClassName() {
        String expectedClassName = ReflectionUtilTest.class.getName();
        String callerClassName = ReflectionUtil.getCallerClassName();
        String callerClassNameInSunJVM = ReflectionUtil.getCallerClassNameInSunJVM();
        String callerClassNameInGeneralJVM = ReflectionUtil.getCallerClassNameInGeneralJVM();

        Assert.assertEquals(expectedClassName, callerClassName);
        Assert.assertEquals(callerClassNameInSunJVM, callerClassName);
        Assert.assertEquals(callerClassNameInGeneralJVM, callerClassName);
    }

    @Test
    public void testGetCallerPackage() {
        Class<?> expectedClass = ReflectionUtilTest.class;
        Package expectedPackage = expectedClass.getPackage();

        Assert.assertEquals(expectedPackage, ReflectionUtil.getCallerPackage());
    }

    @Test
    public void testGetCallerClassNamePerformance() {

        for (int i = 0; i < 6; i++) {
            int times = (int) Math.pow(10 + .0, i + .0);
            testGetCallerClassNameInSunJVMPerformance(times);
            testGetCallerClassNameInGeneralJVMPerformance(times);
            System.out.println();
        }
    }

    private void testGetCallerClassNameInSunJVMPerformance(int times) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            ReflectionUtil.getCallerClassNameInSunJVM();
        }
        long costTime = System.currentTimeMillis() - startTime;
        System.out.printf("It's cost to execute ReflectionUtil.getCallerClassNameInSunJVM() %d times : %d ms！\n", times, costTime);
    }

    private void testGetCallerClassNameInGeneralJVMPerformance(int times) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            ReflectionUtil.getCallerClassNameInGeneralJVM();
        }
        long costTime = System.currentTimeMillis() - startTime;
        System.out.printf("It's cost to execute ReflectionUtil.getCallerClassNameInGeneralJVM() %d times : %d ms！\n", times, costTime);
    }

    @Test
    public void testToList() {
        int[] intArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> list = ReflectionUtil.toList(intArray);
        Object expectedList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Assert.assertEquals(expectedList, list);


        int[][] intIntArray = new int[][]{{1, 2, 3}, {4, 5, 6,}, {7, 8, 9}};
        list = ReflectionUtil.toList(intIntArray);
        expectedList = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9));
        Assert.assertEquals(expectedList, list);
    }

    @Test
    public void testReadFieldsAsMap() {
        Map<String, Object> map = ReflectionUtil.readFieldsAsMap(new String("abc"));
        echo(map);

        map = ReflectionUtil.readFieldsAsMap(Arrays.asList(1, 2, 3, 4));
        echo(map);

        Map<String, String> value = Maps.newHashMapWithExpectedSize(3);
        value.put("a", "a");
        value.put("b", "b");
        value.put("c", "c");
        map = ReflectionUtil.readFieldsAsMap(value);
        echo(map);

        map = ReflectionUtil.readFieldsAsMap(String.class);
        echo(map);

        map = ReflectionUtil.readFieldsAsMap(Class.class);
        echo(map);
    }
}
