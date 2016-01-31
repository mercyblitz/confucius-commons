/**
 *
 */
package org.confucius.commons.util.os.windows;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.confucius.commons.lang.constants.PathConstants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.StringTokenizer;
import java.util.prefs.Preferences;

/**
 * {@link WindowsRegistry}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see WindowsRegistry
 * @since 1.0.0
 */
public final class WindowsRegistry {

    /**
     * Windows handles to <tt>HKEY_CURRENT_USER</tt> and <tt>HKEY_LOCAL_MACHINE</tt> hives.
     */
    private static final int HKEY_CURRENT_USER = 0x80000001;
    private static final int HKEY_LOCAL_MACHINE = 0x80000002;
    /**
     * Mount point for <tt>Preferences</tt>'  user root.
     */
    private static final int USER_ROOT_NATIVE_HANDLE = HKEY_CURRENT_USER;
    /**
     * Mount point for <tt>Preferences</tt>'  system root.
     */
    private static final int SYSTEM_ROOT_NATIVE_HANDLE = HKEY_LOCAL_MACHINE;
    /**
     * Windows注册表 <tt>HKEY_CURRENT_USER</tt> 单体对象
     */
    private static final WindowsRegistry CURRENT_USER = new WindowsRegistry(USER_ROOT_NATIVE_HANDLE);
    /**
     * Windows注册表<tt>HKEY_LOCAL_MACHINE</tt>单体对象
     */
    private static final WindowsRegistry LOCAL_MACHINE = new WindowsRegistry(SYSTEM_ROOT_NATIVE_HANDLE);
    /**
     * Maximum byte-encoded relativePath length for Windows native functions, ending <tt>null</tt> character not
     * included.
     */
    private static final int MAX_WINDOWS_PATH_LENGTH = 256;
    /*  Windows error codes. */
    private static final int ERROR_SUCCESS = 0;
    private static final int ERROR_FILE_NOT_FOUND = 2;
    private static final int ERROR_ACCESS_DENIED = 5;
    /* Constants used to interpret returns of native functions    */
    private static final int NATIVE_HANDLE = 0;
    private static final int ERROR_CODE = 1;
    private static final int SUBKEYS_NUMBER = 0;
    private static final int VALUES_NUMBER = 2;
    private static final int MAX_KEY_LENGTH = 3;
    private static final int MAX_VALUE_NAME_LENGTH = 4;
    private static final int DISPOSITION = 2;
    private static final int REG_CREATED_NEW_KEY = 1;
    private static final int REG_OPENED_EXISTING_KEY = 2;
    private static final int NULL_NATIVE_HANDLE = 0;
    /* Windows security masks */
    private static final int DELETE = 0x10000;
    private static final int KEY_QUERY_VALUE = 1;
    private static final int KEY_SET_VALUE = 2;
    private static final int KEY_CREATE_SUB_KEY = 4;
    private static final int KEY_ENUMERATE_SUB_KEYS = 8;
    private static final int KEY_READ = 0x20019;
    private static final int KEY_WRITE = 0x20006;
    private static final int KEY_ALL_ACCESS = 0xf003f;
    private static Method WindowsRegOpenKey = null;
    private static Method WindowsRegCloseKey = null;
    private static Method WindowsRegCreateKeyEx = null;
    private static Method WindowsRegDeleteKey = null;
    private static Method WindowsRegFlushKey = null;
    private static Method WindowsRegQueryValueEx = null;
    private static Method WindowsRegSetValueEx = null;
    private static Method WindowsRegDeleteValue = null;
    private static Method WindowsRegQueryInfoKey = null;
    private static Class<?> windowsPreferencesType;
    /**
     * Initial time between registry access attempts, in ms. The time is doubled after each failing attempt (except the
     * first).
     */
    private static int INIT_SLEEP_TIME = 50;
    /**
     * Maximum number of registry access attempts.
     */
    private static int MAX_ATTEMPTS = 5;

    static {
        String className = "java.util.prefs.WindowsPreferences";
        try {
            windowsPreferencesType = Class.forName(className);
            WindowsRegOpenKey = findMethod("WindowsRegOpenKey", int.class, byte[].class, int.class);
            WindowsRegCloseKey = findMethod("WindowsRegCloseKey", int.class);
            WindowsRegCreateKeyEx = findMethod("WindowsRegCreateKeyEx", int.class, byte[].class);
            WindowsRegDeleteKey = findMethod("WindowsRegDeleteKey", int.class, byte[].class);
            WindowsRegFlushKey = findMethod("WindowsRegFlushKey", int.class);
            WindowsRegQueryValueEx = findMethod("WindowsRegQueryValueEx", int.class, byte[].class);
            WindowsRegSetValueEx = findMethod("WindowsRegSetValueEx", int.class, byte[].class, byte[].class);
            WindowsRegDeleteValue = findMethod("WindowsRegDeleteValue", int.class, byte[].class);
            WindowsRegQueryInfoKey = findMethod("WindowsRegQueryInfoKey", int.class);
        } catch (Throwable e) {
            String message = String.format("Current JVM ", SystemUtils.JAVA_SPECIFICATION_VERSION);
            throw new InstantiationError(message);
        }
    }

    final private int rootNativeHandle;
    /**
     * BackingStore availability flag.
     */
    private boolean isBackingStoreAvailable = true;

    private WindowsRegistry(int rootNativeHandle) {
        this.rootNativeHandle = rootNativeHandle;
    }

    private static Method findMethod(String name, Class<?>... parameterTypes) throws Exception {
        Method method = windowsPreferencesType.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method;
    }

    private static <T> T invokeMethod(Method method, Object... args) {
        try {
            return (T) method.invoke(null, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Java wrapper for Windows registry API RegOpenKey()
     */
    static int[] WindowsRegOpenKey(int hKey, byte[] subKey, int securityMask) {
        return invokeMethod(WindowsRegOpenKey, hKey, subKey, securityMask);
    }

    /**
     * Java wrapper for Windows registry API RegCloseKey()
     */
    static Integer WindowsRegCloseKey(int hKey) {
        return invokeMethod(WindowsRegCloseKey, hKey);
    }

    /**
     * Java wrapper for Windows registry API RegCreateKeyEx()
     */
    static int[] WindowsRegCreateKeyEx(int hKey, byte[] subKey) {
        return invokeMethod(WindowsRegCreateKeyEx, hKey, subKey);
    }

    /**
     * Java wrapper for Windows registry API RegDeleteKey()
     */
    static Integer WindowsRegDeleteKey(int hKey, byte[] subKey) {
        return invokeMethod(WindowsRegDeleteKey, hKey, subKey);
    }

    /**
     * Java wrapper for Windows registry API RegFlushKey()
     */
    static Integer WindowsRegFlushKey(int hKey) {
        return invokeMethod(WindowsRegFlushKey, hKey);
    }


    /**
     * Java wrapper for Windows registry API RegQueryValueEx()
     */
    static byte[] WindowsRegQueryValueEx(int hKey, byte[] valueName) {
        return invokeMethod(WindowsRegQueryValueEx, hKey, valueName);
    }

    /**
     * Java wrapper for Windows registry API RegSetValueEx()
     */
    static Integer WindowsRegSetValueEx(int hKey, byte[] valueName, byte[] value) {
        return invokeMethod(WindowsRegSetValueEx, hKey, valueName, value);
    }

    /**
     * Java wrapper for Windows registry API RegDeleteValue()
     */
    static Integer WindowsRegDeleteValue(int hKey, byte[] valueName) {
        return invokeMethod(WindowsRegDeleteValue, hKey, valueName);
    }

    /**
     * Java wrapper for Windows registry API RegQueryInfoKey()
     */
    static int[] WindowsRegQueryInfoKey(int hKey) {
        return invokeMethod(WindowsRegQueryInfoKey, hKey);
    }


    /**
     * Retries RegOpenKey() MAX_ATTEMPTS times before giving up.
     */
    private static int[] WindowsRegOpenKey1(int hKey, byte[] subKey,
                                            int securityMask) {
        int[] result = WindowsRegOpenKey(hKey, subKey, securityMask);
        if (result[ERROR_CODE] == ERROR_SUCCESS) {
            return result;
        } else if (result[ERROR_CODE] == ERROR_FILE_NOT_FOUND) {
            // Try recreation
            int handle = WindowsRegCreateKeyEx(hKey, subKey)[NATIVE_HANDLE];
            WindowsRegCloseKey(handle);
            return WindowsRegOpenKey(hKey, subKey, securityMask);
        } else if (result[ERROR_CODE] != ERROR_ACCESS_DENIED) {
            long sleepTime = INIT_SLEEP_TIME;
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegOpenKey(hKey, subKey, securityMask);
                if (result[ERROR_CODE] == ERROR_SUCCESS) {
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * Retries RegCreateKeyEx() MAX_ATTEMPTS times before giving up.
     */
    private static int[] WindowsRegCreateKeyEx1(int hKey, byte[] subKey) {
        int[] result = WindowsRegCreateKeyEx(hKey, subKey);
        if (result[ERROR_CODE] == ERROR_SUCCESS) {
            return result;
        } else {
            long sleepTime = INIT_SLEEP_TIME;
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegCreateKeyEx(hKey, subKey);
                if (result[ERROR_CODE] == ERROR_SUCCESS) {
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * Retries RegFlushKey() MAX_ATTEMPTS times before giving up.
     */
    private static int WindowsRegFlushKey1(int hKey) {
        int result = WindowsRegFlushKey(hKey);
        if (result == ERROR_SUCCESS) {
            return result;
        } else {
            long sleepTime = INIT_SLEEP_TIME;
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegFlushKey(hKey);
                if (result == ERROR_SUCCESS) {
                    return result;
                }
            }
        }
        return result;
    }


    /**
     * Retries RegSetValueEx() MAX_ATTEMPTS times before giving up.
     */
    private static int WindowsRegSetValueEx1(int hKey, byte[] valueName,
                                             byte[] value) {
        int result = WindowsRegSetValueEx(hKey, valueName, value);
        if (result == ERROR_SUCCESS) {
            return result;
        } else {
            long sleepTime = INIT_SLEEP_TIME;
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegSetValueEx(hKey, valueName, value);
                if (result == ERROR_SUCCESS) {
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * Retries RegQueryInfoKey() MAX_ATTEMPTS times before giving up.
     */
    private static int[] WindowsRegQueryInfoKey1(int hKey) {
        int[] result = WindowsRegQueryInfoKey(hKey);
        if (result[ERROR_CODE] == ERROR_SUCCESS) {
            return result;
        } else {
            long sleepTime = INIT_SLEEP_TIME;
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegQueryInfoKey(hKey);
                if (result[ERROR_CODE] == ERROR_SUCCESS) {
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * Java wrapper for Windows registry API RegEnumKeyEx()
     */
    private static native byte[] WindowsRegEnumKeyEx(int hKey, int subKeyIndex,
                                                     int maxKeyLength);

    /**
     * Retries RegEnumKeyEx() MAX_ATTEMPTS times before giving up.
     */
    private static byte[] WindowsRegEnumKeyEx1(int hKey, int subKeyIndex,
                                               int maxKeyLength) {
        byte[] result = WindowsRegEnumKeyEx(hKey, subKeyIndex, maxKeyLength);
        if (result != null) {
            return result;
        } else {
            long sleepTime = INIT_SLEEP_TIME;
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegEnumKeyEx(hKey, subKeyIndex, maxKeyLength);
                if (result != null) {
                    return result;
                }
            }
        }
        return result;
    }

//    /**
//     * Java wrapper for Windows registry API RegEnumValue()
//     */
//    private static native byte[] WindowsRegEnumValue(int hKey, int valueIndex,
//                                                     int maxValueNameLength);
//
//    /**
//     * Retries RegEnumValueEx() MAX_ATTEMPTS times before giving up.
//     */
//    private static byte[] WindowsRegEnumValue1(int hKey, int valueIndex,
//                                               int maxValueNameLength) {
//        byte[] result = WindowsRegEnumValue(hKey, valueIndex,
//                maxValueNameLength);
//        if (result != null) {
//            return result;
//        } else {
//            long sleepTime = INIT_SLEEP_TIME;
//            for (int i = 0; i < MAX_ATTEMPTS; i++) {
//                try {
//                    Thread.sleep(sleepTime);
//                } catch (InterruptedException e) {
//                    return result;
//                }
//                sleepTime *= 2;
//                result = WindowsRegEnumValue(hKey, valueIndex,
//                        maxValueNameLength);
//                if (result != null) {
//                    return result;
//                }
//            }
//        }
//        return result;
//    }

    /**
     * Converts value's or node's name from its byte array representation to java string. Two encodings, simple and
     * altBase64 are used. See {@link #toWindowsName(String) toWindowsName()} for a detailed description of encoding
     * conventions.
     *
     * @param windowsNameArray
     *         Null-terminated byte array.
     */
    private static String toJavaName(byte[] windowsNameArray) {
        String windowsName = byteArrayToString(windowsNameArray);
        // check if Alt64
        if ((windowsName.length() > 1) &&
                (windowsName.substring(0, 2).equals("/!"))) {
            return toJavaAlt64Name(windowsName);
        }
        StringBuffer javaName = new StringBuffer();
        char ch;
        // Decode from simple encoding
        for (int i = 0; i < windowsName.length(); i++) {
            if ((ch = windowsName.charAt(i)) == '/') {
                char next = ' ';
                if ((windowsName.length() > i + 1) &&
                        ((next = windowsName.charAt(i + 1)) >= 'A') && (next <= 'Z')) {
                    ch = next;
                    i++;
                } else if ((windowsName.length() > i + 1) && (next == '/')) {
                    ch = '\\';
                    i++;
                }
            } else if (ch == '\\') {
                ch = '/';
            }
            javaName.append(ch);
        }
        return javaName.toString();
    }

    /**
     * Converts value's or node's name from its Windows representation to java string, using altBase64 encoding. See
     * {@link #toWindowsName(String) toWindowsName()} for a detailed description of encoding conventions.
     */

    private static String toJavaAlt64Name(String windowsName) {
        byte[] byteBuffer =
                Base64.altBase64ToByteArray(windowsName.substring(2));
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < byteBuffer.length; i++) {
            int firstbyte = (byteBuffer[i++] & 0xff);
            int secondbyte = (byteBuffer[i] & 0xff);
            result.append((char) ((firstbyte << 8) + secondbyte));
        }
        return result.toString();
    }

    /**
     * Converts value's or node's name to its Windows representation as a byte-encoded string. Two encodings, simple and
     * altBase64 are used.
     * <p/>
     * <i>Simple</i> encoding is used, if java string does not contain any characters less, than 0x0020, or greater,
     * than 0x007f. Simple encoding adds "/" character to capital letters, i.e. "A" is encoded as "/A". Character '\' is
     * encoded as '//', '/' is encoded as '\'. The constructed string is converted to byte array by truncating the
     * highest byte and adding the terminating <tt>null</tt> character.
     * <p/>
     * <i>altBase64</i>  encoding is used, if java string does contain at least one character less, than 0x0020, or
     * greater, than 0x007f. This encoding is marked by setting first two bytes of the Windows string to '/!'. The java
     * name is then encoded using byteArrayToAltBase64() method from Base64 class.
     */
    private static byte[] toWindowsName(String javaName) {
        StringBuffer windowsName = new StringBuffer();
        for (int i = 0; i < javaName.length(); i++) {
            char ch = javaName.charAt(i);
            if ((ch < 0x0020) || (ch > 0x007f)) {
                // If a non-trivial character encountered, use altBase64
                return toWindowsAlt64Name(javaName);
            }
            if (ch == '\\') {
                windowsName.append("//");
            } else if (ch == '/') {
                windowsName.append('\\');
            } else if ((ch >= 'A') && (ch <= 'Z')) {
                windowsName.append("/" + ch);
            } else {
                windowsName.append(ch);
            }
        }
        return stringToByteArray(windowsName.toString());
    }

    /**
     * Converts value's or node's name to its Windows representation as a byte-encoded string, using altBase64 encoding.
     * See {@link #toWindowsName(String) toWindowsName()} for a detailed description of encoding conventions.
     */
    private static byte[] toWindowsAlt64Name(String javaName) {
        byte[] javaNameArray = new byte[2 * javaName.length()];
        // Convert to byte pairs
        int counter = 0;
        for (int i = 0; i < javaName.length(); i++) {
            int ch = javaName.charAt(i);
            javaNameArray[counter++] = (byte) (ch >>> 8);
            javaNameArray[counter++] = (byte) ch;
        }

        return stringToByteArray(
                "/!" + Base64.byteArrayToAltBase64(javaNameArray));
    }

    /**
     * Converts value string from its Windows representation to java string.  See {@link #toWindowsValueString(String)
     * toWindowsValueString()} for the description of the encoding algorithm.
     */
    private static String toJavaValueString(byte[] windowsNameArray) {
        // Use modified native2ascii algorithm
        String windowsName = byteArrayToString(windowsNameArray);
        StringBuffer javaName = new StringBuffer();
        char ch;
        for (int i = 0; i < windowsName.length(); i++) {
            if ((ch = windowsName.charAt(i)) == '/') {
                char next = ' ';

                if (windowsName.length() > i + 1 &&
                        (next = windowsName.charAt(i + 1)) == 'u') {
                    if (windowsName.length() < i + 6) {
                        break;
                    } else {
                        ch = (char) Integer.parseInt
                                (windowsName.substring(i + 2, i + 6), 16);
                        i += 5;
                    }
                } else if ((windowsName.length() > i + 1) &&
                        ((windowsName.charAt(i + 1)) >= 'A') && (next <= 'Z')) {
                    ch = next;
                    i++;
                } else if ((windowsName.length() > i + 1) &&
                        (next == '/')) {
                    ch = '\\';
                    i++;
                }
            } else if (ch == '\\') {
                ch = '/';
            }
            javaName.append(ch);
        }
        return javaName.toString();
    }

    /**
     * Converts value string to it Windows representation. as a byte-encoded string. Encoding algorithm adds "/"
     * character to capital letters, i.e. "A" is encoded as "/A". Character '\' is encoded as '//', '/' is encoded as
     * '\'. Then encoding scheme similar to jdk's native2ascii converter is used to convert java string to a byte array
     * of ASCII characters.
     */
    private static byte[] toWindowsValueString(String javaName) {
        StringBuffer windowsName = new StringBuffer();
        for (int i = 0; i < javaName.length(); i++) {
            char ch = javaName.charAt(i);
            if ((ch < 0x0020) || (ch > 0x007f)) {
                // write \udddd
                windowsName.append("/u");
                String hex = Integer.toHexString(javaName.charAt(i));
                StringBuffer hex4 = new StringBuffer(hex);
                hex4.reverse();
                int len = 4 - hex4.length();
                for (int j = 0; j < len; j++) {
                    hex4.append('0');
                }
                for (int j = 0; j < 4; j++) {
                    windowsName.append(hex4.charAt(3 - j));
                }
            } else if (ch == '\\') {
                windowsName.append("//");
            } else if (ch == '/') {
                windowsName.append('\\');
            } else if ((ch >= 'A') && (ch <= 'Z')) {
                windowsName.append("/" + ch);
            } else {
                windowsName.append(ch);
            }
        }
        return stringToByteArray(windowsName.toString());
    }

    /**
     * Returns this java string as a null-terminated byte array
     */
    private static byte[] stringToByteArray(String str) {
        byte[] result = new byte[str.length() + 1];
        for (int i = 0; i < str.length(); i++) {
            result[i] = (byte) str.charAt(i);
        }
        result[str.length()] = 0;
        return result;
    }

    /**
     * Converts a null-terminated byte array to java string
     */
    private static String byteArrayToString(byte[] array) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < array.length - 1; i++) {
            result.append((char) array[i]);
        }
        return result.toString();
    }

    /**
     * Get Windows Registry <tt>HKEY_CURRENT_USER</tt> Singleton
     *
     * @return non-null return
     * @throws UnsupportedOperationException
     *         If Non-Windows OS executes current method
     */
    @Nonnull
    public static WindowsRegistry currentUser() throws UnsupportedOperationException {
        if (!SystemUtils.IS_OS_WINDOWS) {
            String message = String.format("Non Windows System");
            throw new UnsupportedOperationException(message);
        }
        return CURRENT_USER;
    }

    /**
     * Returns Windows absolute relativePath of the current node as a byte array. Java "/" separator is transformed into
     * Windows "\".
     *
     * @see Preferences#absolutePath()
     */
    protected byte[] windowsAbsolutePath(String relativePath) {
        String resolvedPath = StringUtils.replace(relativePath, PathConstants.SLASH, PathConstants.BACK_SLASH);
        resolvedPath = StringUtils.replace(relativePath, PathConstants.BACK_SLASH + PathConstants.BACK_SLASH, PathConstants.BACK_SLASH);
        if (resolvedPath.startsWith(PathConstants.BACK_SLASH)) {
            resolvedPath = StringUtils.substringAfter(resolvedPath, PathConstants.BACK_SLASH);
        }
        byte[] relativePathHandle = stringToByteArray(resolvedPath);
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        bstream.write(relativePathHandle, 0, relativePathHandle.length - 1);
        StringTokenizer tokenizer = new StringTokenizer("/", "/");
        while (tokenizer.hasMoreTokens()) {
            bstream.write((byte) '\\');
            String nextName = tokenizer.nextToken();
            byte[] windowsNextName = toWindowsName(nextName);
            bstream.write(windowsNextName, 0, windowsNextName.length - 1);
        }
        bstream.write(0);
        return bstream.toByteArray();
    }

    /**
     * Opens Windows registry key at a given absolute relativePath using a given security mask.
     *
     * @param relativePath
     *         Windows absolute relativePath of the key
     * @param mask1
     *         Preferred Windows security mask.
     * @param mask2
     *         Alternate Windows security mask.
     * @return Windows registry key's handle.
     * @see #closeKey(int)
     */
    protected int openKey(String relativePath, int mask1, int mask2) {
        byte[] windowsAbsolutePath = windowsAbsolutePath(relativePath);
        /*  Check if key's relativePath is short enough be opened at once
            otherwise use a relativePath-splitting procedure */
        if (windowsAbsolutePath.length <= MAX_WINDOWS_PATH_LENGTH + 1) {
            int[] result = WindowsRegOpenKey1(rootNativeHandle(),
                    windowsAbsolutePath, mask1);
            if (result[ERROR_CODE] == ERROR_ACCESS_DENIED && mask2 != mask1)
                result = WindowsRegOpenKey1(rootNativeHandle(),
                        windowsAbsolutePath, mask2);

            if (result[ERROR_CODE] != ERROR_SUCCESS) {
                result[NATIVE_HANDLE] = NULL_NATIVE_HANDLE;
                if (result[ERROR_CODE] == ERROR_ACCESS_DENIED) {
                    throw new SecurityException("Could not open windows " + "registry node " + byteArrayToString(windowsAbsolutePath) + " at root 0x" + Integer.toHexString(rootNativeHandle()) + ": Access denied");
                }
            }
            return result[NATIVE_HANDLE];
        } else {
            return openKey(rootNativeHandle(), windowsAbsolutePath, mask1, mask2);
        }
    }

    /**
     * Opens Windows registry key at a given relative relativePath with respect to a given Windows registry key.
     *
     * @param windowsRelativePath
     *         Windows relative relativePath of the key as a byte-encoded string.
     * @param nativeHandle
     *         handle to the base Windows key.
     * @param mask1
     *         Preferred Windows security mask.
     * @param mask2
     *         Alternate Windows security mask.
     * @return Windows registry key's handle.
     * @see #closeKey(int)
     */
    private int openKey(int nativeHandle, byte[] windowsRelativePath,
                        int mask1, int mask2) {
        /* If the relativePath is short enough open at once. Otherwise split the relativePath */
        if (windowsRelativePath.length <= MAX_WINDOWS_PATH_LENGTH + 1) {
            int[] result = WindowsRegOpenKey1(nativeHandle,
                    windowsRelativePath, mask1);
            if (result[ERROR_CODE] == ERROR_ACCESS_DENIED && mask2 != mask1)
                result = WindowsRegOpenKey1(nativeHandle,
                        windowsRelativePath, mask2);

            if (result[ERROR_CODE] != ERROR_SUCCESS) {
                result[NATIVE_HANDLE] = NULL_NATIVE_HANDLE;
            }
            return result[NATIVE_HANDLE];
        } else {
            int separatorPosition = -1;
            // Be greedy - open the longest possible relativePath
            for (int i = MAX_WINDOWS_PATH_LENGTH; i > 0; i--) {
                if (windowsRelativePath[i] == ((byte) '\\')) {
                    separatorPosition = i;
                    break;
                }
            }
            // Split the relativePath and do the recursion
            byte[] nextRelativeRoot = new byte[separatorPosition + 1];
            System.arraycopy(windowsRelativePath, 0, nextRelativeRoot, 0,
                    separatorPosition);
            nextRelativeRoot[separatorPosition] = 0;
            byte[] nextRelativePath = new byte[windowsRelativePath.length -
                    separatorPosition - 1];
            System.arraycopy(windowsRelativePath, separatorPosition + 1,
                    nextRelativePath, 0, nextRelativePath.length);
            int nextNativeHandle = openKey(nativeHandle, nextRelativeRoot,
                    mask1, mask2);
            if (nextNativeHandle == NULL_NATIVE_HANDLE) {
                return NULL_NATIVE_HANDLE;
            }
            int result = openKey(nextNativeHandle, nextRelativePath,
                    mask1, mask2);
            closeKey(nextNativeHandle);
            return result;
        }
    }

    /**
     * Closes Windows registry key. Logs a warning if Windows registry is unavailable.
     *
     * @param nativeHandle
     *         key's Windows registry handle.
     */
    private void closeKey(int nativeHandle) {
        int result = WindowsRegCloseKey(nativeHandle);
        if (result != ERROR_SUCCESS) {
        }
    }

    /**
     * Set specified registry item in relative path
     *
     * @param relativePath
     *         relative path
     * @param name
     *         registry item name
     * @param value
     *         the value of registry item
     */
    public void set(String relativePath, String name, String value) {
        int nativeHandle = openKey(relativePath, KEY_SET_VALUE, KEY_SET_VALUE);
        if (nativeHandle == NULL_NATIVE_HANDLE) {
            isBackingStoreAvailable = false;
            return;
        }
        int result = WindowsRegSetValueEx1(nativeHandle, stringToByteArray(name), stringToByteArray(value));
        if (result != ERROR_SUCCESS) {
            isBackingStoreAvailable = false;
        }
        closeKey(nativeHandle);
    }

    /**
     * Get the value of  registry item in  specified relative path
     *
     * @param relativePath
     *         relative path
     * @param name
     *         registry item name
     * @return <code>null</code> if not found
     */
    @Nullable
    public String get(String relativePath, String name) {
        int nativeHandle = openKey(relativePath, KEY_QUERY_VALUE, KEY_QUERY_VALUE);
        if (nativeHandle == NULL_NATIVE_HANDLE) {
            return null;
        }
        Object resultObject = WindowsRegQueryValueEx(nativeHandle, stringToByteArray(name));
        if (resultObject == null) {
            closeKey(nativeHandle);
            return null;
        }
        closeKey(nativeHandle);
        return byteArrayToString((byte[]) resultObject);
    }

    /**
     * remove specified registry item in relative path
     *
     * @param relativePath
     *         relative path
     * @param name
     *         registry item name
     */
    public void remove(String relativePath, String name) {
        int nativeHandle = openKey(relativePath, KEY_SET_VALUE, KEY_SET_VALUE);
        if (nativeHandle == NULL_NATIVE_HANDLE) {
            return;
        }
        int result = WindowsRegDeleteValue(nativeHandle, stringToByteArray(name));
        if (result != ERROR_SUCCESS && result != ERROR_FILE_NOT_FOUND) {
            isBackingStoreAvailable = false;
        }
        closeKey(nativeHandle);
    }

    /**
     * Flush specified registry item in relative path
     *
     * @param relativePath
     *         路径
     */
    public void flush(String relativePath) {
        int nativeHandle = openKey(relativePath, KEY_SET_VALUE, KEY_SET_VALUE);
        if (nativeHandle == NULL_NATIVE_HANDLE) {
            return;
        }
        int result = WindowsRegFlushKey(nativeHandle);
        if (result != ERROR_SUCCESS && result != ERROR_FILE_NOT_FOUND) {
            isBackingStoreAvailable = false;
        }
        closeKey(nativeHandle);
    }

    /**
     * Returns native handle for the top Windows node for this node.
     */
    private int rootNativeHandle() {
        return rootNativeHandle;
    }
}
