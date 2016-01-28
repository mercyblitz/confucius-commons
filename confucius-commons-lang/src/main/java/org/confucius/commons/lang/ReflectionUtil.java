/**
 * Project   : commons-lang
 * File      : ReflectionUtil.java
 * Date      : 2012-1-5
 * Time      : 下午01:29:58
 * Copyright : taobao.com Ltd.
 */
package org.confucius.commons.lang;

import com.google.common.collect.Lists;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 反射工具类
 *
 * @author <a href="mailto:taogu.mxx@taobao.com">Mercy</a>
 * @version 1.0.0
 * @see Method
 * @see Field
 * @see Constructor
 * @see Array
 * @since 1.0.0 2012-1-5 下午01:29:58
 */
@SuppressWarnings("unchecked")
public class ReflectionUtil extends BaseUtil {

    /**
     * 断言是否为合法数组索引
     *
     * @param array 数组对象
     * @param index 索引
     * @throws IllegalArgumentException       参考{@link ReflectionUtil#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException 当<code>index</code>小于0，或者大于或等于数组长度
     */
    public static void assertArrayIndex(Object array, int index) throws IllegalArgumentException {
        if (index < 0) {
            String message = String.format("The index argument must be positive , actual is %s", index);
            throw new ArrayIndexOutOfBoundsException(message);
        }
        ReflectionUtil.assertArrayType(array);
        int length = Array.getLength(array);
        if (index > length - 1) {
            String message = String.format("The index must be less than %s , actual is %s", length, index);
            throw new ArrayIndexOutOfBoundsException(message);
        }
    }

    /**
     * 断言参数是否数组
     *
     * @param array 数组对象
     * @throws IllegalArgumentException 当<code>array</code>不是数组类型时
     */
    public static void assertArrayType(Object array) throws IllegalArgumentException {
        Class<?> type = array.getClass();
        if (!type.isArray()) {
            String message = String.format("The argument is not an array object, its type is %s", type.getName());
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言是否字段是否匹配期待的类型
     *
     * @param object       对象
     * @param fieldName    字段名称
     * @param expectedType 期待的类型
     * @throws IllegalArgumentException 当类型服务匹配时
     */
    public static void assertFieldMatchType(Object object, String fieldName, Class<?> expectedType) throws IllegalArgumentException {
        Class<?> type = object.getClass();
        Field field = getField(type, fieldName);
        Class<?> fieldType = field.getType();
        if (!expectedType.isAssignableFrom(fieldType)) {
            String message = String.format("The type[%s] of field[%s] in Class[%s] can't match expected type[%s]", fieldType.getName(), fieldName, type.getName(), expectedType.getName());
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 获取对象中的字段值
     *
     * @param object    对象
     * @param fieldName 字段名称
     * @return 如果找不到的话，返回<code>null</code>
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getField(object.getClass(), fieldName);
        Object value = null;
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            value = field.get(object);
        } catch (Exception ignored) {
        } finally {
            field.setAccessible(accessible);
        }
        return value;
    }

    /**
     * 获取类中的字段对象
     *
     * @param typeWithField 带字段的类对象
     * @param fieldName     字段名称
     * @return 如果找不到的话，返回<code>null</code>
     * @throws IllegalArgumentException 在制定类型中无法通过字段名称获取时
     * @throws NullPointerException     当参数为<code>null</code>时
     */
    public static Field getField(Class<?> typeWithField, String fieldName) throws IllegalArgumentException, NullPointerException {
        Field field = null;
        try {
            field = typeWithField.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ignored) {
            String message = String.format("Field[%s] can not be found in type[%s]", fieldName, typeWithField.getName());
            throw new IllegalArgumentException(message);
        }
        return field;
    }

    /**
     * 设置指定对象字段的值
     *
     * @param object     目标对象
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     */
    public static void setFiled(Object object, String fieldName, Object fieldValue) {
        Field field = getField(object.getClass(), fieldName);
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            field.set(object, fieldValue);
        } catch (IllegalAccessException e) {
            String message = String.format("Field[%s] can not be set with value[%s]", fieldName, fieldValue);
            throw new IllegalArgumentException(message, e);
        } finally {
            if (field != null) {
                field.setAccessible(accessible);
            }
        }
    }

    // /**
    // * 方法签名
    // */
    // private static class MethodSignature {
    // final private Class<?> targetClass;
    // final private String methodName;
    // final private Class<?>[] argClasses;
    //
    // final private int hashCode;
    //
    // public MethodSignature(Class<?> targetClass, String methodName,
    // Class<?>... argClasses) {
    // this.targetClass = targetClass;
    // this.methodName = methodName;
    // this.argClasses = argClasses;
    // this.hashCode = createHashCode();
    // }
    //
    // private int createHashCode() {
    // int result = 17;
    // result = 37 * result + targetClass.hashCode();
    // result = 37 * result + methodName.hashCode();
    // if (argClasses != null) {
    // for (int i = 0; i < argClasses.length; i++) {
    // result = 37 * result + ((argClasses[i] == null) ? 0 :
    // argClasses[i].hashCode());
    // }
    // }
    // return result;
    // }
    //
    // public boolean equals(Object o2) {
    // if (this == o2) {
    // return true;
    // }
    // MethodSignature that = (MethodSignature) o2;
    // if (!(targetClass == that.targetClass)) {
    // return false;
    // }
    // if (!(methodName.equals(that.methodName))) {
    // return false;
    // }
    // if (argClasses.length != that.argClasses.length) {
    // return false;
    // }
    // for (int i = 0; i < argClasses.length; i++) {
    // if (!(argClasses[i] == that.argClasses[i])) {
    // return false;
    // }
    // }
    // return true;
    // }
    //
    // public int hashCode() {
    // return hashCode;
    // }
    // }

    /**
     * 在指定的类中获取方法名称和参数对应的方法对象（没有修改{@link Method#setAccessible(boolean) 可访问性}
     * ），如果没有找到的话，返回<code>null</code>
     *
     * @param classObject         类对象
     * @param methodName          方法名称
     * @param methodArgumentTypes 参数类型列表
     * @return 指定的类中获取方法名称和参数对应的方法对象，如果没有找到的话，返回<code>null</code>
     * @version 1.0.0
     * @see Method
     * @since 1.0.0 2012-1-5 下午01:36:46
     */
    public static Method getMethod(Class<?> classObject, String methodName, Class<?>... methodArgumentTypes) {
        Method method = null;

        try {
            method = classObject.getDeclaredMethod(methodName, methodArgumentTypes);
        } catch (Exception ignored) {
            method = null;
        }

        return method;
    }

    private static Method findMethod(Class<?> type, String methodName, Object... arguments) {
        List<Class<?>> methodArgumentTypesList = Lists.newArrayListWithCapacity(arguments.length);
        for (Object argument : arguments) {
            methodArgumentTypesList.add(argument.getClass());
        }
        Method method = findMethod(type, methodName, methodArgumentTypesList.toArray(new Class<?>[0]));
        return method;
    }

    /**
     * 调用非静态方法
     *
     * @param <T>
     * @param source     对象源
     * @param methodName 方法名
     * @param arguments  方法参数
     * @return 返回执行方法后的结果
     * @version 1.0.0
     * @since 1.0.0 2012-1-5 下午02:15:26
     */
    public static <T> T invokeMethod(Object source, String methodName, Object... arguments) {

        Class<?> type = source.getClass();

        Method method = findMethod(type, methodName, arguments);

        if (method == null)
            return null;

        return (T) invokeMethod(source, method, arguments);
    }

    /**
     * 调用静态方法，如果方法没有找到的话，返回<code>null</code>
     *
     * @param <T>        返回类型
     * @param type       指定的类
     * @param methodName 指定的方法名称
     * @param arguments  方法参数对象
     * @return 返回返回值。如果方法没有找到的话，返回<code>null</code>
     * @version 1.0.0
     * @since 1.0.0 2012-3-15 下午02:19:52
     */
    public static <T> T invokeStaticMethod(Class<?> type, String methodName, Object... arguments) {
        Method method = findMethod(type, methodName, arguments);
        if (method == null)
            return null;
        return (T) invokeMethod(null, method, arguments);
    }

    /**
     * 方法调用
     *
     * @param source
     * @param method
     * @param arguments
     * @return 方法返回值
     * @throws IllegalArgumentException 包装{@link Method#invoke(Object, Object...)}方法异常信息
     * @version 1.0.0
     * @see Method#invoke(Object, Object...)
     * @since 1.0.0 2012-3-15 下午02:03:12
     */
    private static Object invokeMethod(Object source, Method method, Object... arguments) throws IllegalArgumentException {
        final boolean accessible = method.isAccessible();
        Object value = null;
        try {
            method.setAccessible(true);
            value = method.invoke(source, arguments);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        } finally {
            method.setAccessible(accessible);
        }
        return value;
    }

    /**
     * 在指定的类中查找方法名称和参数对应的方法对象（没有修改{@link Method#setAccessible(boolean) 可访问性}
     * ），如果没有找到的话，递归地尝试其父类或查找。如果还是没有的话，返回<code>null</code>
     *
     * @param classObject         类对象
     * @param methodName          方法名称
     * @param methodArgumentTypes 参数类型列表
     * @return 在指定的类中查找方法名称和参数对应的方法对象（没有修改{@link Method#setAccessible(boolean)
     * 可访问性} ），如果没有找到的话，递归地尝试其父类或查找。如果还是没有的话，返回<code>null</code>
     * @version 1.0.0
     * @see Method
     * @since 1.0.0 2012-1-5 下午01:36:46
     */
    public static Method findMethod(Class<?> classObject, String methodName, Class<?>... methodArgumentTypes) {
        Method method = null;

        Class<?> classToFind = classObject;

        while (classToFind != null) {
            try {
                method = classToFind.getDeclaredMethod(methodName, methodArgumentTypes);
            } catch (Exception ignored) {
                method = null;
                classToFind = classToFind.getSuperclass();
            }

            if (method != null)
                break;

        }

        return method;
    }

}
