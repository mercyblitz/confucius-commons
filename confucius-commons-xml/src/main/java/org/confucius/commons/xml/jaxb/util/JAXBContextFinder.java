package org.confucius.commons.xml.jaxb.util;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * {@link JAXBContext} Finder
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @since 1.0.0
 */
abstract public class JAXBContextFinder {

    final private static JAXBContextInitializer jaxbContextInitializer = new JAXBContextInitializer();

    private JAXBContextFinder() {

    }

    /**
     * Find {@link JAXBContext} under specified {@link ClassLoader} and package
     *
     * @param classLoader
     *         {@link ClassLoader}
     * @param packageName
     *         the name of {@link Package package}
     * @return {@link JAXBContext}
     * @throws JAXBException
     */
    @Nullable
    static public JAXBContext find(@Nonnull ClassLoader classLoader, @Nonnull String packageName) throws JAXBException {

        if (packageName == null || StringUtils.isBlank(packageName)) {
            // TODO: Doc ME
            throw new IllegalArgumentException();
        }

        JAXBContext jaxbContext = jaxbContextInitializer.createJAXBContext(classLoader, packageName);

        return jaxbContext;
    }

    /**
     * ͨ��ָ����Ԫ�����Ͳ��������Ķ���
     *
     * @param type
     *         Ԫ�ض������
     * @return �����Ķ��� ����ܹ��ҵ��Ļ�
     * @throws JAXBException
     *         ������������ʧ��ʱ
     * @version 1.0.0
     * @see
     * @since 1.0.0 2012-1-10 ����10:04:30
     */
    public static JAXBContext find(Class<?> type) throws JAXBException {
        ClassLoader classLoader = type.getClassLoader();
        Package package_ = type.getPackage();
        String packageName = package_.getName();
        JAXBContext jaxbContent = find(classLoader, packageName);
        return jaxbContent;

    }

}
