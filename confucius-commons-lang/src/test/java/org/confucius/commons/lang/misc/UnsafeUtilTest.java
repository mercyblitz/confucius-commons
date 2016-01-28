package org.confucius.commons.lang.misc;

import junit.framework.Assert;
import junit.framework.TestCase;
import sun.misc.Unsafe;

/**
 * {@link UnsafeUtil} {@link TestCase}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see UnsafeUtilTest
 * @since 1.0.0 2016-01-28
 */
public class UnsafeUtilTest extends TestCase {


    private Model model;

    public void setUp() throws Exception {
        model = new Model();
    }

    public void testStaticInit() throws Exception {
        Assert.assertNotNull(UnsafeUtil.unsafe);
        Unsafe unsafe = UnsafeUtil.unsafe;

        Assert.assertEquals(UnsafeUtil.LONG_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(long[].class));
        Assert.assertEquals(UnsafeUtil.INT_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(int[].class));
        Assert.assertEquals(UnsafeUtil.SHORT_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(short[].class));
        Assert.assertEquals(UnsafeUtil.BYTE_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(byte[].class));
        Assert.assertEquals(UnsafeUtil.BOOLEAN_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(boolean[].class));
        Assert.assertEquals(UnsafeUtil.DOUBLE_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(double[].class));
        Assert.assertEquals(UnsafeUtil.FLOAT_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(float[].class));
        Assert.assertEquals(UnsafeUtil.CHAR_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(char[].class));
        Assert.assertEquals(UnsafeUtil.OBJECT_ARRAY_BASE_OFFSET, unsafe.arrayBaseOffset(Object[].class));


        Assert.assertEquals(UnsafeUtil.LONG_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(long[].class));
        Assert.assertEquals(UnsafeUtil.INT_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(int[].class));
        Assert.assertEquals(UnsafeUtil.SHORT_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(short[].class));
        Assert.assertEquals(UnsafeUtil.BYTE_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(byte[].class));
        Assert.assertEquals(UnsafeUtil.BOOLEAN_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(boolean[].class));
        Assert.assertEquals(UnsafeUtil.DOUBLE_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(double[].class));
        Assert.assertEquals(UnsafeUtil.FLOAT_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(float[].class));
        Assert.assertEquals(UnsafeUtil.CHAR_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(char[].class));
        Assert.assertEquals(UnsafeUtil.OBJECT_ARRAY_INDEX_SCALE, unsafe.arrayIndexScale(Object[].class));

    }

    public void testPutLongAndGetLong() throws Exception {
        String fieldName = "longValue";
        long value = Long.MAX_VALUE;
        UnsafeUtil.putLong(model, fieldName, value);
        long returnValue = UnsafeUtil.getLong(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.longValue, returnValue);

        value = Long.MIN_VALUE;
        UnsafeUtil.putLongVolatile(model, fieldName, value);
        returnValue = UnsafeUtil.getLongVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.longValue, returnValue);

        value = Long.MAX_VALUE;
        UnsafeUtil.putOrderedLong(model, fieldName, value);
        returnValue = UnsafeUtil.getLongVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.longValue, returnValue);
    }

    public void testPutIntAndGetInt() throws Exception {
        String fieldName = "intValue";
        int value = 123;
        UnsafeUtil.putInt(model, fieldName, value);
        int returnValue = UnsafeUtil.getInt(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.intValue, returnValue);

        value = Integer.MAX_VALUE;
        UnsafeUtil.putIntVolatile(model, fieldName, value);
        returnValue = UnsafeUtil.getIntVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.intValue, returnValue);

        value = Integer.MIN_VALUE;
        UnsafeUtil.putOrderedInt(model, fieldName, value);
        returnValue = UnsafeUtil.getIntVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.intValue, returnValue);
    }

    public void testPutShortAndGetShort() throws Exception {
        String fieldName = "shortValue";
        short value = Short.MAX_VALUE;
        UnsafeUtil.putShort(model, fieldName, value);
        short returnValue = UnsafeUtil.getShort(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.shortValue, returnValue);

        UnsafeUtil.putShortVolatile(model, fieldName, value);
        returnValue = UnsafeUtil.getShortVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.shortValue, returnValue);
    }

    public void testPutByteAndGetByte() throws Exception {
        String fieldName = "byteValue";
        byte value = Byte.MAX_VALUE;
        UnsafeUtil.putByte(model, fieldName, value);
        byte returnValue = UnsafeUtil.getByte(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.byteValue, returnValue);

        UnsafeUtil.putByteVolatile(model, fieldName, value);
        returnValue = UnsafeUtil.getByteVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.byteValue, returnValue);
    }

    public void testPutBooleanAndGetBoolean() throws Exception {
        String fieldName = "booleanValue";
        boolean value = Boolean.TRUE;
        UnsafeUtil.putBoolean(model, fieldName, value);
        boolean returnValue = UnsafeUtil.getBoolean(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.booleanValue, returnValue);

        UnsafeUtil.putBooleanVolatile(model, fieldName, value);
        returnValue = UnsafeUtil.getBooleanVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.booleanValue, returnValue);
    }

    public void testPutDoubleAndGetDouble() throws Exception {
        String fieldName = "doubleValue";
        double value = Double.MAX_VALUE;
        UnsafeUtil.putDouble(model, fieldName, value);
        double returnValue = UnsafeUtil.getDouble(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.doubleValue, returnValue);

        UnsafeUtil.putDoubleVolatile(model, fieldName, value);
        returnValue = UnsafeUtil.getDoubleVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.doubleValue, returnValue);
    }

    public void testPutFloatAndGetFloat() throws Exception {
        String fieldName = "floatValue";
        float value = Float.MAX_VALUE;
        UnsafeUtil.putFloat(model, fieldName, value);
        float returnValue = UnsafeUtil.getFloat(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.floatValue, returnValue);

        UnsafeUtil.putFloatVolatile(model, fieldName, value);
        returnValue = UnsafeUtil.getFloatVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.floatValue, returnValue);
    }

    public void testPutCharAndGetChar() throws Exception {
        String fieldName = "charValue";
        char value = '@';
        UnsafeUtil.putChar(model, fieldName, value);
        char returnValue = UnsafeUtil.getChar(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.charValue, returnValue);

        UnsafeUtil.putCharVolatile(model, fieldName, value);
        returnValue = UnsafeUtil.getCharVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.charValue, returnValue);

    }

    public void testPutObjectAndGetObject() throws Exception {
        String fieldName = "stringValue";
        Object value = "Test text";
        UnsafeUtil.putObject(model, fieldName, value);
        Object returnValue = UnsafeUtil.getObject(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.stringValue, returnValue);

        value = "AAAAAAAAAAAA";
        UnsafeUtil.putObjectVolatile(model, fieldName, value);
        returnValue = UnsafeUtil.getObjectVolatile(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.stringValue, returnValue);

        value = "BBBBBB";
        UnsafeUtil.putOrderedObject(model, fieldName, value);
        returnValue = UnsafeUtil.getObject(model, fieldName);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.stringValue, returnValue);
    }

    public void testPutLongIntoArrayVolatileAndGetLongFromArrayVolatile() throws Exception {
        String fieldName = "longArrayValue";
        long value = 123;
        int index = 2;
        UnsafeUtil.putLongIntoArrayVolatile(model, fieldName, index, value);
        long returnValue = UnsafeUtil.getLongFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.longArrayValue[index], returnValue);

        value = Integer.MAX_VALUE;
        UnsafeUtil.putOrderedLongIntoArray(model, fieldName, index, value);
        returnValue = UnsafeUtil.getLongFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.longArrayValue[index], returnValue);
    }

    public void testPutIntIntoArrayVolatileAndGetIntFromArrayVolatile() throws Exception {
        String fieldName = "intArrayValue";
        int value = 123;
        int index = 1;
        UnsafeUtil.putIntIntoArrayVolatile(model, fieldName, index, value);
        int returnValue = UnsafeUtil.getIntFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.intArrayValue[index], returnValue);

        value = Integer.MAX_VALUE;
        UnsafeUtil.putOrderedIntIntoArray(model, fieldName, index, value);
        returnValue = UnsafeUtil.getIntFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.intArrayValue[index], returnValue);
    }

    public void testPutShortIntoArrayVolatileAndGetShortFromArrayVolatile() throws Exception {
        String fieldName = "shortArrayValue";
        short value = 123;
        int index = 5;
        UnsafeUtil.putShortIntoArrayVolatile(model, fieldName, index, value);
        short returnValue = UnsafeUtil.getShortFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.shortArrayValue[index], returnValue);
    }

    public void testPutByteIntoArrayVolatileAndGetByteFromArrayVolatile() throws Exception {
        String fieldName = "byteArrayValue";
        byte value = 123;
        int index = 5;
        UnsafeUtil.putByteIntoArrayVolatile(model, fieldName, index, value);
        byte returnValue = UnsafeUtil.getByteFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.byteArrayValue[index], returnValue);
    }

    public void testPutBooleanIntoArrayVolatileAndGetBooleanFromArrayVolatile() throws Exception {
        String fieldName = "booleanArrayValue";
        boolean value = Boolean.TRUE;
        int index = 3;
        UnsafeUtil.putBooleanIntoArrayVolatile(model, fieldName, index, value);
        boolean returnValue = UnsafeUtil.getBooleanFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.booleanArrayValue[index], returnValue);
    }

    public void testPutDoubleIntoArrayVolatileAndGetDoubleFromArrayVolatile() throws Exception {
        String fieldName = "doubleArrayValue";
        double value = Double.MAX_VALUE;
        int index = 8;
        UnsafeUtil.putDoubleIntoArrayVolatile(model, fieldName, index, value);
        double returnValue = UnsafeUtil.getDoubleFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.doubleArrayValue[index], returnValue);
    }

    public void testPutFloatIntoArrayVolatileAndGetFloatFromArrayVolatile() throws Exception {
        String fieldName = "floatArrayValue";
        float value = Float.MAX_VALUE;
        int index = 7;
        UnsafeUtil.putFloatIntoArrayVolatile(model, fieldName, index, value);
        float returnValue = UnsafeUtil.getFloatFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.floatArrayValue[index], returnValue);
    }

    public void testPutCharIntoArrayVolatileAndGetCharFromArrayVolatile() throws Exception {
        String fieldName = "charArrayValue";
        char value = '@';
        int index = 9;
        UnsafeUtil.putCharIntoArrayVolatile(model, fieldName, index, value);
        char returnValue = UnsafeUtil.getCharFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.charArrayValue[index], returnValue);
    }

    public void testPutObjectIntoArrayVolatileAndGetObjectFromArrayVolatile() throws Exception {
        String fieldName = "objectArrayValue";
        Object value = "Test";
        int index = 5;
        UnsafeUtil.putObjectIntoArrayVolatile(model, fieldName, index, value);
        Object returnValue = UnsafeUtil.getObjectFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.objectArrayValue[index], returnValue);

        UnsafeUtil.putOrderedObjectIntoArray(model, fieldName, index, value);
        returnValue = UnsafeUtil.getObjectFromArrayVolatile(model, fieldName, index);
        Assert.assertEquals(returnValue, value);
        Assert.assertEquals(model.objectArrayValue[index], returnValue);

    }

    /**
     * 测试当存储更高范围类型的值，然后获取
     */
    public void tesPutOnInvalidTypeValue() throws Exception {
        String fieldName = "intValue";
        int value = Integer.MAX_VALUE;
        int index = 1;
        IllegalArgumentException exception;

        exception = null;
        Assert.assertNull(exception);
        try {
            UnsafeUtil.putLong(model, fieldName, value);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);

        exception = null;
        Assert.assertNull(exception);
        try {
            UnsafeUtil.putShort(model, fieldName, (short) 1);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);

        exception = null;
        Assert.assertNull(exception);
        try {
            UnsafeUtil.putChar(model, fieldName, (char) 1);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);


    }


    public void testOnObjectIsNull() throws Exception {
        String fieldName = "aaaa";
        Object value = "value";

        NullPointerException exception = null;
        try {
            UnsafeUtil.putObject(null, fieldName, value);
        } catch (NullPointerException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    public void testOnIllegalArgumentException() throws Exception {
        String fieldName = "aaaa";
        Object value = "value";

        NullPointerException exception = null;
        try {
            UnsafeUtil.putObject(model, fieldName, value);
        } catch (NullPointerException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    private static class Model {
        private long longValue;
        private int intValue;
        private short shortValue;
        private byte byteValue;
        private boolean booleanValue;
        private float floatValue;
        private double doubleValue;
        private char charValue;
        private String stringValue;
        private long[] longArrayValue = new long[10];
        private int[] intArrayValue = new int[10];
        private short[] shortArrayValue = new short[10];
        private byte[] byteArrayValue = new byte[10];
        private boolean[] booleanArrayValue = new boolean[10];
        private double[] doubleArrayValue = new double[10];
        private float[] floatArrayValue = new float[10];
        private char[] charArrayValue = new char[10];
        private Object[] objectArrayValue = new Object[10];
    }

}
