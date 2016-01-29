/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.lang;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.EnumerationUtils;
import org.apache.commons.lang3.StringUtils;
import org.confucius.commons.lang.constants.Constants;
import org.confucius.commons.lang.constants.FileSuffixConstants;
import org.confucius.commons.lang.constants.PathConstants;
import org.confucius.commons.lang.net.URLUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

/**
 * {@link ClassLoader} Utility
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see ClassLoader
 * @since 1.0.0
 */
public class ClassLoaderUtil {

    /**
     * Get the resource URLs Set under specified resource name and type
     *
     * @param classLoader
     *         ClassLoader
     * @param resourceType
     *         {@link ResourceType} Enum
     * @param resourceName
     *         resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *         <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @throws IOException
     * @version 1.0.0
     * @since 1.0.0
     */
    public static Set<URL> getResources(ClassLoader classLoader, ResourceType resourceType, String resourceName) throws NullPointerException, IOException {
        String normalizedResourceName = resourceType.resolve(resourceName);
        return Sets.newLinkedHashSet(EnumerationUtils.toList(classLoader.getResources(normalizedResourceName)));
    }

    /**
     * Get the resource URLs list under specified resource name
     *
     * @param classLoader
     *         ClassLoader
     * @param resourceName
     *         resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *         <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @throws IOException
     * @version 1.0.0
     * @since 1.0.0
     */
    public static Set<URL> getResources(ClassLoader classLoader, String resourceName) throws NullPointerException, IOException {
        Set<URL> resourceURLs = Collections.emptySet();
        for (ResourceType resourceType : ResourceType.values()) {
            resourceURLs = getResources(classLoader, resourceType, resourceName);
            if (CollectionUtils.isNotEmpty(resourceURLs)) {
                break;
            }
        }
        return resourceURLs;
    }

    /**
     * Get the resource URL under specified resource name
     *
     * @param classLoader
     *         ClassLoader
     * @param resourceName
     *         resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *         <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @version 1.0.0
     * @since 1.0.0
     */
    public static URL getResource(ClassLoader classLoader, String resourceName) throws NullPointerException {
        URL resourceURL = null;
        for (ResourceType resourceType : ResourceType.values()) {
            resourceURL = getResource(classLoader, resourceType, resourceName);
            if (resourceURL != null) {
                break;
            }
        }
        return resourceURL;
    }

    /**
     * Get the resource URL under specified resource name and type
     *
     * @param classLoader
     *         ClassLoader
     * @param resourceType
     *         {@link ResourceType} Enum
     * @param resourceName
     *         resource name ，e.g : <br /> <ul> <li>Resource Name :<code>"/com/abc/def.log"</code></li> <li>Class Name :
     *         <code>"java.lang.String"</code></li> </ul>
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @version 1.0.0
     * @since 1.0.0
     */
    public static URL getResource(ClassLoader classLoader, ResourceType resourceType, String resourceName) throws NullPointerException {
        String normalizedResourceName = resourceType.resolve(resourceName);
        return classLoader.getResource(normalizedResourceName);
    }


    /**
     * Get the {@link Class} resource URL under specified {@link Class#getName() Class name}
     *
     * @param classLoader
     *         ClassLoader
     * @param className
     *         class name
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @version 1.0.0
     * @since 1.0.0
     */
    public static URL getClassResource(ClassLoader classLoader, String className) {
        return getResource(classLoader, ResourceType.CLASS, className);
    }

    /**
     * Get the {@link Class} resource URL under specified {@link Class}
     *
     * @param classLoader
     *         ClassLoader
     * @param type
     *         {@link Class type}
     * @return the resource URL under specified resource name and type
     * @throws NullPointerException
     *         If any argument is <code>null</code>
     * @version 1.0.0
     * @since 1.0.0
     */
    public static URL getClassResource(ClassLoader classLoader, Class<?> type) {
        String resourceName = type.getName();
        return getClassResource(classLoader, resourceName);
    }

    /**
     * Resource Type
     */
    public enum ResourceType {

        DEFAULT {
            @Override
            public String normalize(String name) {
                return name;
            }
        },
        CLASS {
            @Override
            public String normalize(String name) {
                return StringUtils.replace(name, Constants.DOT, PathConstants.SLASH) + FileSuffixConstants.CLASS;
            }
        };

        /**
         * resolves resource name
         *
         * @param name
         *         resource name
         * @return a newly resolved resource name
         */
        public String resolve(String name) {
            String normalizedName = normalize(name);
            if (normalizedName == null)
                return normalizedName;

            normalizedName = URLUtil.resolvePath(normalizedName);

            // 除去开头的"/"
            while (normalizedName.startsWith("/")) {
                normalizedName = normalizedName.substring(1);
            }

            return normalizedName;
        }

        abstract String normalize(String name);
    }
}
