package com.ignoretheextraclub.siteswapfactory.sorters;

import com.ignoretheextraclub.siteswapfactory.utils.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 Created by caspar on 12/12/16.
 */
public class SortingUtilsTest
{
    @Test
    public void getFirstMinIndex_int() throws Exception
    {
        Assert.assertEquals(2, ArrayUtils.getFirstMinIndex(new int[]{5, 5, 3, 7}));
        Assert.assertEquals(0, ArrayUtils.getFirstMinIndex(new int[]{3, 7}));
        Assert.assertEquals(4, ArrayUtils.getFirstMinIndex(new int[]{5, 5, 3, 7, 1}));
        Assert.assertEquals(3, ArrayUtils.getFirstMinIndex(new int[]{5, 8, 5, 3, 3}));
    }

    @Test
    public void getFirstMinIndex_Object() throws Exception
    {
        Assert.assertEquals(2,
                            ArrayUtils.getFirstMinIndex(Arrays.stream(new int[]{5, 5, 3, 7})
                                                              .boxed()
                                                              .collect(Collectors.toList())));
        Assert.assertEquals(0,
                            ArrayUtils.getFirstMinIndex(Arrays.stream(new int[]{3, 7})
                                                              .boxed()
                                                              .collect(Collectors.toList())));
        Assert.assertEquals(4,
                            ArrayUtils.getFirstMinIndex(Arrays.stream(new int[]{5, 5, 3, 7, 1})
                                                              .boxed()
                                                              .collect(Collectors.toList())));
        Assert.assertEquals(3,
                            ArrayUtils.getFirstMinIndex(Arrays.stream(new int[]{5, 8, 5, 3, 3})
                                                              .boxed()
                                                              .collect(Collectors.toList())));
    }

    @Test
    public void getFirstMaxIndex_Object() throws Exception
    {
        Assert.assertEquals(3,
                            ArrayUtils.getFirstMaxIndex(Arrays.stream(new int[]{5, 5, 3, 7})
                                                              .boxed()
                                                              .collect(Collectors.toList())));
        Assert.assertEquals(1,
                            ArrayUtils.getFirstMaxIndex(Arrays.stream(new int[]{3, 7})
                                                              .boxed()
                                                              .collect(Collectors.toList())));
        Assert.assertEquals(3,
                            ArrayUtils.getFirstMaxIndex(Arrays.stream(new int[]{5, 5, 3, 7, 1})
                                                              .boxed()
                                                              .collect(Collectors.toList())));
        Assert.assertEquals(1,
                            ArrayUtils.getFirstMaxIndex(Arrays.stream(new int[]{5, 8, 5, 3, 3})
                                                              .boxed()
                                                              .collect(Collectors.toList())));
    }

    @Test
    public void getFirstMaxIndex_int() throws Exception
    {
        Assert.assertEquals(3, ArrayUtils.getFirstMaxIndex(new int[]{5, 5, 3, 7}));
        Assert.assertEquals(1, ArrayUtils.getFirstMaxIndex(new int[]{3, 7}));
        Assert.assertEquals(3, ArrayUtils.getFirstMaxIndex(new int[]{5, 5, 3, 7, 1}));
        Assert.assertEquals(1, ArrayUtils.getFirstMaxIndex(new int[]{5, 8, 5, 3, 3}));
    }
}