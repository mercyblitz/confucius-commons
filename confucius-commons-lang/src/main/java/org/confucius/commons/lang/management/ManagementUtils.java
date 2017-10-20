package org.confucius.commons.lang.management;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Management Utility class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ManagementUtils
 * @since 1.0.0
 */
public abstract class ManagementUtils {

    /**
     * {@link RuntimeMXBean}
     */
    private final static RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    /**
     * "jvm" Field name
     */
    private final static String JVM_FIELD_NAME = "jvm";
    /**
     * sun.management.ManagementFactory.jvm
     */
    final static Object jvm = initJvm();
    /**
     * "getProcessId" Method name
     */
    private final static String GET_PROCESS_ID_METHOD_NAME = "getProcessId";
    /**
     * "getProcessId" Method
     */
    final static Method getProcessIdMethod = initGetProcessIdMethod();


    private static Object initJvm() {
        Object jvm = null;
        Field jvmField = null;
        if (runtimeMXBean != null) {
            try {
                jvmField = runtimeMXBean.getClass().getDeclaredField(JVM_FIELD_NAME);
                jvmField.setAccessible(true);
                jvm = jvmField.get(runtimeMXBean);
            } catch (Exception ignored) {
                System.err.printf("The Field[name : %s] can't be found in RuntimeMXBean Class[%s]!\n", JVM_FIELD_NAME, runtimeMXBean.getClass());
            }
        }
        return jvm;
    }

    private static Method initGetProcessIdMethod() {
        Class<?> jvmClass = jvm.getClass();

        Method getProcessIdMethod = null;
        try {
            getProcessIdMethod = jvmClass.getDeclaredMethod(GET_PROCESS_ID_METHOD_NAME);
            getProcessIdMethod.setAccessible(true);
        } catch (Exception ignored) {
            System.err.printf("%s method can't be found in class[%s]!\n", GET_PROCESS_ID_METHOD_NAME, jvmClass.getName());
        }
        return getProcessIdMethod;
    }

    private static Object invoke(Method method, Object object, Object... arguments) {
        Object result = null;
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            result = method.invoke(object, arguments);
        } catch (Exception ignored) {
            System.err.printf("%s method[arguments : %s] can't be invoked in object[%s]!\n",
                    method.getName(), Arrays.asList(arguments), object);
        }

        return result;
    }

    /**
     * Get the process ID of current JVM
     *
     * @return If can't get the process ID , return <code>-1</code>
     */
    public static int getCurrentProcessId() {
        int processId = -1;
        Object result = invoke(getProcessIdMethod, jvm);
        if (result instanceof Integer) {
            processId = (Integer) result;
        } else {
            // no guarantee
            String name = runtimeMXBean.getName();
            String processIdValue = StringUtils.substringBefore(name, "@");
            if (NumberUtils.isNumber(processIdValue)) {
                processId = Integer.parseInt(processIdValue);
            }
        }
        return processId;
    }

}
