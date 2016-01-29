/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.lang.net;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.confucius.commons.lang.constants.PathConstants;

import javax.annotation.Nonnull;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * {@link URL} Utility class
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">Mercy<a/>
 * @version 1.0.0
 * @see URL
 * @see URLEncoder
 * @see URLDecoder
 * @since 1.0.0
 */
public abstract class URLUtil {


    /**
     *
     */
    private static final String DEFAULT_ENCODING = "UTF-8";


    private URLUtil() {

    }


    /**
     * Resolve parameters {@link Map} from specified URL，The parameter name as key ，parameter value as key
     *
     * @param url
     *         URL
     * @return Read-only {@link Map}
     */
    @Nonnull
    public static Map<String, List<String>> resolveParametersMap(String url) {
        String queryString = StringUtils.substringAfterLast(url, "?");
        if (StringUtils.isNotBlank(queryString)) {
            Map<String, List<String>> parametersMap = Maps.newHashMap();
            String[] queryParams = StringUtils.split(queryString, "&");
            if (queryParams != null) {
                for (String queryParam : queryParams) {
                    String[] paramNameAndValue = StringUtils.split(queryParam, "=");
                    if (paramNameAndValue.length > 0) {
                        String paramName = paramNameAndValue[0];
                        String paramValue = paramNameAndValue.length > 1 ? paramNameAndValue[1] : StringUtils.EMPTY;
                        List<String> paramValueList = parametersMap.get(paramName);
                        if (paramValueList == null) {
                            paramValueList = Lists.newLinkedList();
                            parametersMap.put(paramName, paramValueList);
                        }
                        paramValueList.add(paramValue);
                    }
                }
            }
            return Collections.unmodifiableMap(parametersMap);
        } else {
            return Collections.emptyMap();
        }
    }

    /**
     * Normalize Path(maybe from File or URL), will remove duplicated slash or backslash from path. For example,
     * <p/>
     * <code> resolvePath("C:\\Windows\\\\temp") == "C:/Windows/temp"; resolvePath("C:\\\\\Windows\\/temp") ==
     * "C:/Windows/temp"; resolvePath("/home/////index.html") == "/home/index.html"; </code>
     *
     * @param path
     *         Path
     * @return a newly resolved path
     * @version 1.0.0
     * @since 1.0.0
     */
    public static String resolvePath(final String path) {

        if (StringUtils.isBlank(path)) {
            return path;
        }

        String resolvedPath = path.trim();

        while (resolvedPath.contains(PathConstants.BACK_SLASH)) {
            resolvedPath = StringUtils.replace(resolvedPath, PathConstants.BACK_SLASH, PathConstants.SLASH);
        }

        while (resolvedPath.contains(PathConstants.DOUBLE_SLASH)) {
            resolvedPath = StringUtils.replace(resolvedPath, PathConstants.DOUBLE_SLASH, PathConstants.SLASH);
        }
        return resolvedPath;
    }

    /**
     * Translates a string into <code>application/x-www-form-urlencoded</code> format using a specific encoding scheme.
     * This method uses the supplied encoding scheme to obtain the bytes for unsafe characters.
     * <p/>
     * <em><strong>Note:</strong> The <a href= "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars"> World
     * Wide Web Consortium Recommendation</a> states that UTF-8 should be used. Not doing so may introduce
     * incompatibilites.</em>
     *
     * @param value
     *         <code>String</code> to be translated.
     * @param encoding
     *         The name of a supported character encoding</a>.
     * @return the translated <code>String</code>.
     * @throws IllegalArgumentException
     *         If the named encoding is not supported
     * @see URLDecoder#decode(java.lang.String, java.lang.String)
     * @since 1.4
     */
    public static String encode(String value, String encoding) throws IllegalArgumentException {
        String encodedValue = null;
        try {
            encodedValue = URLEncoder.encode(value, encoding);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return encodedValue;
    }

    /**
     * {@link #encode(String, String)} with "UTF-8" encoding
     *
     * @param value
     *         the <code>String</code> to decode
     * @return the newly encoded <code>String</code>
     */
    public static String encode(String value) {
        return encode(value, DEFAULT_ENCODING);
    }

    /**
     * {@link #decode(String, String)} with "UTF-8" encoding
     *
     * @param value
     *         the <code>String</code> to decode
     * @return the newly decoded <code>String</code>
     */
    public static String decode(String value) {
        return decode(value, DEFAULT_ENCODING);
    }


    /**
     * Decodes a <code>application/x-www-form-urlencoded</code> string using a specific encoding scheme. The supplied
     * encoding is used to determine what characters are represented by any consecutive sequences of the form
     * "<code>%<i>xy</i></code>".
     * <p/>
     * <em><strong>Note:</strong> The <a href= "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars"> World
     * Wide Web Consortium Recommendation</a> states that UTF-8 should be used. Not doing so may introduce
     * incompatibilites.</em>
     *
     * @param value
     *         the <code>String</code> to decode
     * @param encoding
     *         The name of a supported encoding
     * @return the newly decoded <code>String</code>
     * @throws IllegalArgumentException
     *         If character encoding needs to be consulted, but named character encoding is not supported
     */
    public static String decode(String value, String encoding) throws IllegalArgumentException {
        String decodedValue = null;
        try {
            decodedValue = URLDecoder.decode(value, encoding);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return decodedValue;
    }

}
