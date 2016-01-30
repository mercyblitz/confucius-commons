package org.confucius.commons.lang.util;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * {@link ServiceLoader} Utility
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ServiceLoader
 * @since 1.0.0
 */
public abstract class ServiceLoaderUtil {

    /**
     * 利用{@link ClassLoader}的层次性， 各层次的ClassLoader（ ClassLoader 、其双亲ClassLoader以及更上层）将会能加载其class
     * path下的配置文件/META-INF/services/ <code>serviceInterfaceType</code>。 每个service interface type 的配置文件可以定义多个实现类列表。
     * <p/>
     *
     * @param <T>
     *         service interface type
     * @param classLoader
     *         {@link ClassLoader}
     * @param serviceInterfaceType
     *         service interface type
     * @return service interface type 的所有实现对象{@link Collections#unmodifiableList(List) 只读列表}
     * @throws IllegalArgumentException
     *         如果指没有定义<code>serviceInterfaceType</code> 的实现类在配置文件/META-INF/services/<code>serviceInterfaceType</code>
     *         中的话，将会抛出 IllegalArgumentException
     * @version 1.0.0
     * @since 1.0.0
     */
    public static <T> List<T> loadServicesList(ClassLoader classLoader, Class<T> serviceInterfaceType) throws IllegalArgumentException {
        return Collections.unmodifiableList(loadServicesList0(classLoader, serviceInterfaceType));
    }

    /**
     * Load all instances of service interface type
     *
     * @param <T>
     *         service interface type
     * @param classLoader
     *         {@link ClassLoader}
     * @param serviceInterfaceType
     *         service interface type
     * @return Load all instances of service interface type
     * @throws IllegalArgumentException
     *         see {@link #loadServicesList(ClassLoader, Class)}
     * @version 1.0.0
     * @since 1.0.0
     */
    private static <T> List<T> loadServicesList0(ClassLoader classLoader, Class<T> serviceInterfaceType) throws IllegalArgumentException {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(serviceInterfaceType, classLoader);
        Iterator<T> iterator = serviceLoader.iterator();
        List<T> serviceList = Lists.newArrayList(iterator);

        if (serviceList.isEmpty()) {
            String className = serviceInterfaceType.getName();
            String message = String.format("No Service interface[type : %s] implementation was defined in service loader configuration file[/META-INF/services/%s] under ClassLoader[%s]", className, className, classLoader);
            IllegalArgumentException e = new IllegalArgumentException(message);
            throw e;
        }

        return serviceList;
    }

    /**
     * Load the first instance of {@link #loadServicesList(ClassLoader, Class) Service interface instances list}
     * <p/>
     * Design Purpose : 利用{@link ClassLoader}的层次性， 各层次的ClassLoader将会能访问其class path下的配置文件/META-INF/services/<code>serviceInterfaceType</code>
     * 。那么，覆盖 ClassLoader 的class path下的配置文件的第一个实现类，从而提供覆盖实现类机制。
     *
     * @param <T>
     *         service interface type
     * @param serviceInterfaceType
     * @return 如果存在的话，{@link #loadServicesList(ClassLoader, Class) 加载service interface type 的实现对象列表} 中的第一个.
     * @throws IllegalArgumentException
     *         如果指没有定义<code>serviceInterfaceType</code> 的实现类在配置文件/META-INF/services/<code>serviceInterfaceType</code>
     *         中的话，将会抛出 IllegalArgumentException
     * @version 1.0.0
     * @since 1.0.0
     */
    public static <T> T loadFirstService(ClassLoader classLoader, Class<T> serviceInterfaceType) throws IllegalArgumentException {
        List<T> serviceList = loadServicesList0(classLoader, serviceInterfaceType);
        return serviceList.get(0);
    }

    /**
     * 如果存在的话，加载service interface type 的实现对象列表中的最后一个。
     * <p/>
     * <p/>
     * 设计目的： 利用{@link ClassLoader}的层次性，一旦较高层次（这里最高层次ClassLoader为Bootstrap ClassLoader）双亲的ClassLoader中加载了配置文件/META-INF/services/
     * <code>serviceInterfaceType</code>中的最后一个实现类的话，低层次的ClassLoader将无法覆盖前定义。
     *
     * @param <T>
     *         service interface type
     * @param serviceInterfaceType
     * @return 如果存在的话，加载service interface type 的实现对象列表中的最后一个。
     * @throws IllegalArgumentException
     *         see {@link #loadServicesList(ClassLoader, Class)}
     * @version 1.0.0
     * @since 1.0.0
     */
    public static <T> T loadLastService(ClassLoader classLoader, Class<T> serviceInterfaceType) throws IllegalArgumentException {
        List<T> serviceList = loadServicesList0(classLoader, serviceInterfaceType);
        return serviceList.get(serviceList.size() - 1);
    }

}
