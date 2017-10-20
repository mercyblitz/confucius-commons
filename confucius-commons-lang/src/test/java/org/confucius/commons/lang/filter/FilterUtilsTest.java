/**
 * Confucius commons project
 */
package org.confucius.commons.lang.filter;

import junit.framework.Assert;
import org.confucius.commons.lang.AbstractTestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * {@link FilterUtils} Test Case
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see FilterUtilsTest
 * @since 1.0.0
 */
public class FilterUtilsTest extends AbstractTestCase {

    @Test
    public void testFilter() {
        List<Integer> integerList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> result = FilterUtils.filter(integerList, FilterOperator.AND, new Filter<Integer>() {
            @Override
            public boolean accept(Integer filteredObject) {
                return filteredObject % 2 == 0;
            }
        });

        Assert.assertEquals(Arrays.asList(0, 2, 4, 6, 8), result);

        result = FilterUtils.filter(integerList, new Filter<Integer>() {
            @Override
            public boolean accept(Integer filteredObject) {
                return filteredObject % 2 == 0;
            }
        });

        Assert.assertEquals(Arrays.asList(0, 2, 4, 6, 8), result);

        result = FilterUtils.filter(integerList, FilterOperator.OR, new Filter<Integer>() {
            @Override
            public boolean accept(Integer filteredObject) {
                return filteredObject % 2 == 1;
            }
        });

        Assert.assertEquals(Arrays.asList(1, 3, 5, 7, 9), result);
    }
}
