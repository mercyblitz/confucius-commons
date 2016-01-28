package org.confucius.commons.lang.misc;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.confucius.commons.lang.ReflectionUtil;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.ConcurrentMap;

import static org.confucius.commons.lang.ReflectionUtil.assertArrayIndex;
import static org.confucius.commons.lang.ReflectionUtil.assertFieldMatchType;

/**
 * {@link sun.misc.Unsafe} Utility class <p/> <b> Take case to  use those utility methods in order to the stability fo
 * JVM </b>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see UnsafeUtil
 * @since 1.0.0 2016-01-28
 */
public abstract class UnsafeUtil {

    final static Unsafe unsafe;
    /**
     * <code>long</code>数组base index
     */
    static final int LONG_ARRAY_BASE_OFFSET;
    /**
     * <code>int</code>数组base index
     */
    static final int INT_ARRAY_BASE_OFFSET;
    /**
     * <code>short</code>数组base index
     */
    static final int SHORT_ARRAY_BASE_OFFSET;
    /**
     * <code>byte</code>数组base index
     */
    static final int BYTE_ARRAY_BASE_OFFSET;
    /**
     * <code>boolean</code>数组base index
     */
    static final int BOOLEAN_ARRAY_BASE_OFFSET;
    /**
     * <code>double</code>数组base index
     */
    static final int DOUBLE_ARRAY_BASE_OFFSET;
    /**
     * <code>float</code> 数组base index
     */
    static final int FLOAT_ARRAY_BASE_OFFSET;
    /**
     * <code>char</code> 数组base index
     */
    static final int CHAR_ARRAY_BASE_OFFSET;
    /**
     * <code>java.lang.Object</code> 数组base index
     */
    static final int OBJECT_ARRAY_BASE_OFFSET;
    /**
     * <code>long</code>数组索引scale
     */
    static final int LONG_ARRAY_INDEX_SCALE;
    /**
     * <code>int</code>数组索引scale
     */
    static final int INT_ARRAY_INDEX_SCALE;
    /**
     * <code>short</code>数组索引scale
     */
    static final int SHORT_ARRAY_INDEX_SCALE;
    /**
     * <code>byte</code>数组索引scale
     */
    static final int BYTE_ARRAY_INDEX_SCALE;
    /**
     * <code>boolean</code>数组索引scale
     */
    static final int BOOLEAN_ARRAY_INDEX_SCALE;
    /**
     * <code>double</code>数组索引scale
     */
    static final int DOUBLE_ARRAY_INDEX_SCALE;
    /**
     * <code>float</code>数组索引scale
     */
    static final int FLOAT_ARRAY_INDEX_SCALE;
    /**
     * <code>char</code>数组索引scale
     */
    static final int CHAR_ARRAY_INDEX_SCALE;
    /**
     * <code>java.lang.Object</code>数组索引scale
     */
    static final int OBJECT_ARRAY_INDEX_SCALE;
    /**
     * Offset Cache, 其Key为
     */
    private final static ConcurrentMap<String, Long> offsetCache = Maps.newConcurrentMap();

    static {
        try {
            final PrivilegedExceptionAction<Unsafe> action = new PrivilegedExceptionAction<Unsafe>() {
                public Unsafe run() throws Exception {
                    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafe.setAccessible(true);
                    return (Unsafe) theUnsafe.get(null);
                }
            };

            unsafe = AccessController.doPrivileged(action);

            if (unsafe == null) {
                throw new NullPointerException();
            }

            LONG_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(long[].class);
            INT_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(int[].class);
            SHORT_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(short[].class);
            BYTE_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(byte[].class);
            BOOLEAN_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(boolean[].class);
            DOUBLE_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(double[].class);
            FLOAT_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(float[].class);
            CHAR_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(char[].class);
            OBJECT_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(Object[].class);

            LONG_ARRAY_INDEX_SCALE = unsafe.arrayIndexScale(long[].class);
            INT_ARRAY_INDEX_SCALE = unsafe.arrayIndexScale(int[].class);
            SHORT_ARRAY_INDEX_SCALE = unsafe.arrayIndexScale(short[].class);
            BYTE_ARRAY_INDEX_SCALE = unsafe.arrayIndexScale(byte[].class);
            BOOLEAN_ARRAY_INDEX_SCALE = unsafe.arrayIndexScale(boolean[].class);
            DOUBLE_ARRAY_INDEX_SCALE = unsafe.arrayIndexScale(double[].class);
            FLOAT_ARRAY_INDEX_SCALE = unsafe.arrayIndexScale(float[].class);
            CHAR_ARRAY_INDEX_SCALE = unsafe.arrayIndexScale(char[].class);
            OBJECT_ARRAY_INDEX_SCALE = unsafe.arrayIndexScale(Object[].class);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Current JVM does not support  sun.misc.Unsafe");
        }
    }

    private UnsafeUtil() {

    }

    /**
     * 计算数组Index Offset
     *
     * @param index
     *         索引值
     * @param baseOffset
     *         {@link Unsafe#arrayBaseOffset(Class)}
     * @param indexScale
     *         {@link Unsafe#arrayIndexScale(Class)}
     * @return
     * @see java.util.concurrent.atomic.AtomicIntegerArray
     */
    protected static long arrayIndexOffset(int index, long baseOffset, long indexScale) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index " + index);
        return baseOffset + (long) index * indexScale;
    }


    /**
     * 计算<code>long</code>类型数组索引相对offset
     *
     * @param index
     *         数组索引
     * @return 相对offset
     */
    protected static long longArrayIndexOffset(int index) {
        return arrayIndexOffset(index, LONG_ARRAY_BASE_OFFSET, LONG_ARRAY_INDEX_SCALE);
    }

    /**
     * 计算<code>int</code>类型数组索引相对offset
     *
     * @param index
     *         数组索引
     * @return 相对offset
     */
    protected static long intArrayIndexOffset(int index) {
        return arrayIndexOffset(index, INT_ARRAY_BASE_OFFSET, INT_ARRAY_INDEX_SCALE);
    }

    /**
     * 计算<code>short</code>类型数组索引相对offset
     *
     * @param index
     *         数组索引
     * @return 相对offset
     */
    protected static long shortArrayIndexOffset(int index) {
        return arrayIndexOffset(index, SHORT_ARRAY_BASE_OFFSET, SHORT_ARRAY_INDEX_SCALE);
    }

    /**
     * 计算<code>byte</code>类型数组索引相对offset
     *
     * @param index
     *         数组索引
     * @return 相对offset
     */
    protected static long byteArrayIndexOffset(int index) {
        return arrayIndexOffset(index, BYTE_ARRAY_BASE_OFFSET, BYTE_ARRAY_INDEX_SCALE);
    }

    /**
     * 计算<code>boolean</code>类型数组索引相对offset
     *
     * @param index
     *         数组索引
     * @return 相对offset
     */
    protected static long booleanArrayIndexOffset(int index) {
        return arrayIndexOffset(index, BOOLEAN_ARRAY_BASE_OFFSET, BOOLEAN_ARRAY_INDEX_SCALE);
    }

    /**
     * 计算<code>double</code>类型数组索引相对offset
     *
     * @param index
     *         数组索引
     * @return 相对offset
     */
    protected static long doubleArrayIndexOffset(int index) {
        return arrayIndexOffset(index, DOUBLE_ARRAY_BASE_OFFSET, DOUBLE_ARRAY_INDEX_SCALE);
    }

    /**
     * 计算<code>float</code>类型数组索引相对offset
     *
     * @param index
     *         数组索引
     * @return 相对offset
     */
    protected static long floatArrayIndexOffset(int index) {
        return arrayIndexOffset(index, FLOAT_ARRAY_BASE_OFFSET, FLOAT_ARRAY_INDEX_SCALE);
    }

    /**
     * 计算<code>char</code>类型数组索引相对offset
     *
     * @param index
     *         数组索引
     * @return 相对offset
     */
    protected static long charArrayIndexOffset(int index) {
        return arrayIndexOffset(index, CHAR_ARRAY_BASE_OFFSET, CHAR_ARRAY_INDEX_SCALE);
    }

    /**
     * 计算<code>java.lang.Object</code>类型数组索引相对offset
     *
     * @param index
     *         数组索引
     * @return 相对offset
     */
    protected static long objectArrayIndexOffset(int index) {
        return arrayIndexOffset(index, OBJECT_ARRAY_BASE_OFFSET, OBJECT_ARRAY_INDEX_SCALE);
    }


    /**
     * 创建Offset缓存Key
     *
     * @param type
     *         类型
     * @param fieldName
     *         字段名称
     * @return Offset缓存Key
     */
    protected static String createOffsetCacheKey(Class<?> type, String fieldName) {
        StringBuilder keyBuilder = new StringBuilder(type.getName()).append("#").append(fieldName);
        return keyBuilder.toString();
    }

    /**
     * 在缓存获取Offset
     *
     * @param type
     *         类型
     * @param fieldName
     *         字段名称
     * @return Offset
     */
    protected static Long getOffsetFromCache(Class<?> type, String fieldName) {
        String key = createOffsetCacheKey(type, fieldName);
        return offsetCache.get(key);
    }

    /**
     * 将offset保存到缓存
     *
     * @param type
     *         类型
     * @param fieldName
     *         字段名称
     * @param offset
     *         offset
     */
    protected static void putOffsetFromCache(Class<?> type, String fieldName, long offset) {
        String key = createOffsetCacheKey(type, fieldName);
        offsetCache.putIfAbsent(key, offset);
    }


    /**
     * 获取对象数组字段中目标索引的<code>long</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @return 目标索引的值
     */
    public static long getLongFromArrayVolatile(Object object, String fieldName, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        long offset = longArrayIndexOffset(index);
        return unsafe.getLongVolatile(array, offset);
    }

    /**
     * 获取对象数组字段中目标索引的值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @return 目标索引的值
     */
    public static int getIntFromArrayVolatile(Object object, String fieldName, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = intArrayIndexOffset(index);
        return unsafe.getIntVolatile(array, offset);
    }

    /**
     * 获取对象数组字段中目标索引的<code>short</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @return 目标索引的值
     */
    public static short getShortFromArrayVolatile(Object object, String fieldName, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = shortArrayIndexOffset(index);
        return unsafe.getShortVolatile(array, offset);
    }

    /**
     * 获取对象数组字段中目标索引的<code>byte</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @return 目标索引的值
     */
    public static byte getByteFromArrayVolatile(Object object, String fieldName, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = byteArrayIndexOffset(index);
        return unsafe.getByteVolatile(array, offset);
    }

    /**
     * 获取对象数组字段中目标索引的<code>boolean</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @return 目标索引的值
     */
    public static boolean getBooleanFromArrayVolatile(Object object, String fieldName, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = booleanArrayIndexOffset(index);
        return unsafe.getBooleanVolatile(array, offset);
    }

    /**
     * 获取对象数组字段中目标索引的<code>double</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @return 目标索引的值
     */
    public static double getDoubleFromArrayVolatile(Object object, String fieldName, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = doubleArrayIndexOffset(index);
        return unsafe.getDoubleVolatile(array, offset);
    }

    /**
     * 获取对象数组字段中目标索引的<code>float</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @return 目标索引的值
     */
    public static float getFloatFromArrayVolatile(Object object, String fieldName, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = floatArrayIndexOffset(index);
        return unsafe.getFloatVolatile(array, offset);
    }

    /**
     * 获取对象数组字段中目标索引的<code>char</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @return 目标索引的值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static char getCharFromArrayVolatile(Object object, String fieldName, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = charArrayIndexOffset(index);
        return unsafe.getCharVolatile(array, offset);
    }

    /**
     * 获取对象数组字段中目标索引的<code>java.lang.Object</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @return 目标索引的值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static Object getObjectFromArrayVolatile(Object object, String fieldName, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = objectArrayIndexOffset(index);
        return unsafe.getObjectVolatile(array, offset);
    }


    /**
     * 给指定对象的字段设置给定的double值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         double值
     */
    public static void putDouble(Object object, String fieldName, double value) {
        assertFieldMatchType(object, fieldName, double.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putDouble(object, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的float值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         float值
     */
    public static void putFloat(Object object, String fieldName, float value) {
        assertFieldMatchType(object, fieldName, float.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putFloat(object, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的short值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         short值
     */
    public static void putShort(Object object, String fieldName, short value) {
        assertFieldMatchType(object, fieldName, short.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putShort(object, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的byte值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         byte值
     */
    public static void putByte(Object object, String fieldName, byte value) {
        assertFieldMatchType(object, fieldName, byte.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putByte(object, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的boolean值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         boolean值
     */
    public static void putBoolean(Object object, String fieldName, boolean value) {
        assertFieldMatchType(object, fieldName, boolean.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putBoolean(object, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的char值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         char值
     */
    public static void putChar(Object object, String fieldName, char value) {
        assertFieldMatchType(object, fieldName, char.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putChar(object, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的Object值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         Object值
     */
    public static void putObject(Object object, String fieldName, Object value) {
        assertFieldMatchType(object, fieldName, Object.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putObject(object, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的long值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         long值
     */
    public static void putLong(Object object, String fieldName, long value) {
        assertFieldMatchType(object, fieldName, long.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putLong(object, offset, value);
    }


    /**
     * 给指定对象的字段设置给定的int值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         int值
     */
    public static void putInt(Object object, String fieldName, int value) {
        assertFieldMatchType(object, fieldName, int.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putInt(object, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的int值(确保写入顺序）
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         int值
     */
    public static void putOrderedInt(Object object, String fieldName, int value) {
        assertFieldMatchType(object, fieldName, int.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putOrderedInt(object, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的long值(确保写入顺序）
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         long值
     */
    public static void putOrderedLong(Object object, String fieldName, long value) {
        assertFieldMatchType(object, fieldName, long.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putOrderedLong(object, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的Object值(确保写入顺序）
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         Object值
     */
    public static void putOrderedObject(Object object, String fieldName, Object value) {
        assertFieldMatchType(object, fieldName, Object.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putOrderedObject(object, offset, value);
    }

    /**
     * 给指定对象的<code>volatile</code>字段设置给定的double值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         double值
     */
    public static void putDoubleVolatile(Object object, String fieldName, double value) {
        assertFieldMatchType(object, fieldName, double.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putDoubleVolatile(object, offset, value);
    }

    /**
     * 给指定对象的<code>volatile</code>字段设置给定的float值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         float值
     */
    public static void putFloatVolatile(Object object, String fieldName, float value) {
        assertFieldMatchType(object, fieldName, float.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putFloatVolatile(object, offset, value);
    }

    /**
     * 给指定对象的<code>volatile</code>字段设置给定的short值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         short值
     */
    public static void putShortVolatile(Object object, String fieldName, short value) {
        assertFieldMatchType(object, fieldName, short.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putShortVolatile(object, offset, value);
    }

    /**
     * 给指定对象的<code>volatile</code>字段设置给定的byte值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         byte值
     */
    public static void putByteVolatile(Object object, String fieldName, byte value) {
        assertFieldMatchType(object, fieldName, byte.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putByteVolatile(object, offset, value);
    }

    /**
     * 给指定对象的<code>volatile</code>字段设置给定的boolean值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         boolean值
     */
    public static void putBooleanVolatile(Object object, String fieldName, boolean value) {
        assertFieldMatchType(object, fieldName, boolean.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putBooleanVolatile(object, offset, value);
    }

    /**
     * 给指定对象的<code>volatile</code>字段设置给定的char值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         char值
     */
    public static void putCharVolatile(Object object, String fieldName, char value) {
        assertFieldMatchType(object, fieldName, char.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putCharVolatile(object, offset, value);
    }

    /**
     * 给指定对象的<code>volatile</code>字段设置给定的Object值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         Object值
     */
    public static void putObjectVolatile(Object object, String fieldName, Object value) {
        assertFieldMatchType(object, fieldName, Object.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putObjectVolatile(object, offset, value);
    }

    /**
     * 给指定对象的<code>volatile</code>字段设置给定的long值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         long值
     */
    public static void putLongVolatile(Object object, String fieldName, long value) {
        assertFieldMatchType(object, fieldName, long.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putLongVolatile(object, offset, value);
    }

    /**
     * 给指定对象的<code>volatile</code>字段设置给定的int值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param value
     *         int值
     */
    public static void putIntVolatile(Object object, String fieldName, int value) {
        assertFieldMatchType(object, fieldName, int.class);
        long offset = getObjectFieldOffset(object, fieldName);
        unsafe.putIntVolatile(object, offset, value);
    }


    /**
     * 给指定对象的字段设置给定的<code>long</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>long</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putLongIntoArrayVolatile(Object object, String fieldName, int index, long value) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        ReflectionUtil.assertArrayIndex(array, index);
        long offset = longArrayIndexOffset(index);
        unsafe.putLongVolatile(array, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的<code>long</code>值（顺序写入）
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>long</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putOrderedLongIntoArray(Object object, String fieldName, int index, long value) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = longArrayIndexOffset(index);
        unsafe.putOrderedLong(array, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的<code>int</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>int</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putIntIntoArrayVolatile(Object object, String fieldName, int index, int value) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = intArrayIndexOffset(index);
        unsafe.putIntVolatile(array, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的<code>int</code>值（顺序写入）
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>int</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putOrderedIntIntoArray(Object object, String fieldName, int index, int value) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = intArrayIndexOffset(index);
        unsafe.putOrderedInt(array, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的<code>short</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>short</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putShortIntoArrayVolatile(Object object, String fieldName, int index, short value) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = shortArrayIndexOffset(index);
        unsafe.putShortVolatile(array, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的<code>byte</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>byte</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putByteIntoArrayVolatile(Object object, String fieldName, int index, byte value) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = byteArrayIndexOffset(index);
        unsafe.putByteVolatile(array, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的<code>boolean</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>boolean</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putBooleanIntoArrayVolatile(Object object, String fieldName, int index, boolean value) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = booleanArrayIndexOffset(index);
        unsafe.putBooleanVolatile(array, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的<code>double</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>double</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putDoubleIntoArrayVolatile(Object object, String fieldName, int index, double value) throws IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = doubleArrayIndexOffset(index);
        unsafe.putDoubleVolatile(array, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的<code>float</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>float</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putFloatIntoArrayVolatile(Object object, String fieldName, int index, float value) throws IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = floatArrayIndexOffset(index);
        unsafe.putFloatVolatile(array, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的<code>char</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>char</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putCharIntoArrayVolatile(Object object, String fieldName, int index, char value) throws IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = charArrayIndexOffset(index);
        unsafe.putCharVolatile(array, offset, value);
    }

    /**
     * 给指定对象的字段设置给定的<code>java.lang.Object</code>值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>java.lang.Object</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putObjectIntoArrayVolatile(Object object, String fieldName, int index, Object value) throws IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = objectArrayIndexOffset(index);
        unsafe.putObjectVolatile(array, offset, value);
    }


    /**
     * 给指定对象的字段设置给定的<code>java.lang.Object</code>值（顺序写入）
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @param index
     *         元素索引
     * @param value
     *         <code>java.lang.Object</code>值
     * @throws IllegalArgumentException
     *         参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException
     *         当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void putOrderedObjectIntoArray(Object object, String fieldName, int index, Object value) throws IllegalAccessException {
        Object array = FieldUtils.readDeclaredField(object, fieldName, true);
        assertArrayIndex(array, index);
        long offset = objectArrayIndexOffset(index);
        unsafe.putOrderedObject(array, offset, value);
    }

    /**
     * 获取指定对象字段的Object值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return Object值
     */
    public static Object getObject(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getObject(object, offset);
    }

    /**
     * 获取指定对象字段的long值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return long值
     */
    public static long getLong(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getLong(object, offset);
    }

    /**
     * 获取指定对象字段的double值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return double值
     */
    public static double getDouble(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getDouble(object, offset);
    }

    /**
     * 获取指定对象字段的float值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return float值
     */
    public static float getFloat(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getFloat(object, offset);
    }

    /**
     * 获取指定对象字段的short值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return short值
     */
    public static short getShort(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getShort(object, offset);
    }

    /**
     * 获取指定对象字段的byte值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return byte值
     */
    public static byte getByte(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getByte(object, offset);
    }

    /**
     * 获取指定对象字段的boolean值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return boolean值
     */
    public static boolean getBoolean(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getBoolean(object, offset);
    }

    /**
     * 获取指定对象字段的char值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return char值
     */
    public static char getChar(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getChar(object, offset);
    }

    /**
     * 获取指定对象字段的int 值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return int值
     */
    public static int getInt(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getInt(object, offset);
    }

    /**
     * 获取指定对象<code>volatile</code>字段的Object值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return Object值
     */
    public static Object getObjectVolatile(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getObjectVolatile(object, offset);
    }

    /**
     * 获取指定对象<code>volatile</code>字段的long值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return long值
     */
    public static long getLongVolatile(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getLongVolatile(object, offset);
    }

    /**
     * 获取指定对象<code>volatile</code>字段的double值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return double值
     */
    public static double getDoubleVolatile(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getDoubleVolatile(object, offset);
    }

    /**
     * 获取指定对象<code>volatile</code>字段的float值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return float值
     */
    public static float getFloatVolatile(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getFloatVolatile(object, offset);
    }

    /**
     * 获取指定对象<code>volatile</code>字段的short值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return short值
     */
    public static short getShortVolatile(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getShortVolatile(object, offset);
    }

    /**
     * 获取指定对象<code>volatile</code>字段的byte值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return byte值
     */
    public static byte getByteVolatile(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getByteVolatile(object, offset);
    }

    /**
     * 获取指定对象字段的boolean值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return boolean值
     */
    public static boolean getBooleanVolatile(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getBooleanVolatile(object, offset);
    }

    /**
     * 获取指定对象<code>volatile</code>字段的char值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return char值
     */
    public static char getCharVolatile(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getCharVolatile(object, offset);
    }

    /**
     * 获取指定对象<code>volatile</code>字段的int 值
     *
     * @param object
     *         对象
     * @param fieldName
     *         字段名称
     * @return int值
     */
    public static int getIntVolatile(Object object, String fieldName) {
        long offset = getObjectFieldOffset(object, fieldName);
        return unsafe.getIntVolatile(object, offset);
    }

    /**
     * 获取对象字段的偏移量
     *
     * @param object
     *         对象实例
     * @param fieldName
     *         字段名称
     * @return 偏移量
     * @throws IllegalArgumentException
     *         在制定类型中无法通过字段名称获取时
     * @throws NullPointerException
     *         当参数为<code>null</code>时
     */
    protected static long getObjectFieldOffset(Object object, String fieldName) throws IllegalArgumentException, NullPointerException {
        Class<?> type = object.getClass();
        Long offsetFromCache = getOffsetFromCache(type, fieldName);
        if (offsetFromCache != null) {
            return offsetFromCache;
        }
        Field field = FieldUtils.getField(type, fieldName, true);
        long offset = unsafe.objectFieldOffset(field);
        putOffsetFromCache(type, fieldName, offset);
        return offset;
    }

    /**
     * 获取类静态字段的偏移量
     *
     * @param type
     *         类型
     * @param fieldName
     *         字段名称
     * @return 偏移量
     */
    public static long getStaticFieldOffset(Class<?> type, String fieldName) {
        Field field = FieldUtils.getField(type, fieldName, true);
        return unsafe.staticFieldOffset(field);
    }
}
