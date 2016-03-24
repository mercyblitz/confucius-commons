package org.confucius.commons.lang.management;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.confucius.commons.lang.ClassLoaderUtil;

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
 * @see ManagementUtil
 * @since 1.0.0 2016-03-23
 */
public abstract class ManagementUtil {

    /**
     * {@link RuntimeMXBean}
     */
    private final static RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    /**
     * sun.management.ManagementFactory class name
     */
    private final static String MANAGEMENT_FACTORY_CLASS_NAME = "sun.management.ManagementFactory";
    /**
     * sun.management.ManagementFactory {@link Class}
     */
    final static Class<?> managementFactoryClass = loadManagementFactoryClass();
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


    private static Class<?> loadManagementFactoryClass() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class<?> managementFactoryClass = ClassLoaderUtil.loadClass(classLoader, MANAGEMENT_FACTORY_CLASS_NAME);
        if (managementFactoryClass == null) {
            System.err.printf("Management Factory Class[%s] can't be loaded by ClassLoader[%s]!\n", MANAGEMENT_FACTORY_CLASS_NAME, classLoader.getClass());
        }
        return managementFactoryClass;
    }

    private static Object initJvm() {
        Object jvm = null;
        Field jvmField = null;
        if (managementFactoryClass != null) {
            try {
                jvmField = managementFactoryClass.getDeclaredField(JVM_FIELD_NAME);
                jvmField.setAccessible(true);
                jvm = jvmField.get(null);
            } catch (Exception ignored) {
                System.err.printf("The Field[name : %s] can't be found in Management Factory Class[%s]!\n", JVM_FIELD_NAME, managementFactoryClass.getName());
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
