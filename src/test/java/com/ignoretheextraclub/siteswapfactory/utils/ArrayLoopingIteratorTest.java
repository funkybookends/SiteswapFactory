package com.ignoretheextraclub.siteswapfactory.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by caspar on 09/10/17.
 */
public class ArrayLoopingIteratorTest
{
    @Test
    public void testHasNext() throws Exception
    {
        final ArrayLoopingIterator emptyArray = new ArrayLoopingIterator<>(new Boolean[0]);
        Assertions.assertThat(emptyArray.hasNext()).isFalse();

        final ArrayLoopingIterator<Boolean> arrayWithOneItem = new ArrayLoopingIterator<>(new Boolean[1]);

        for (int i = 0; i < 1000; i++)
        {
            Assertions.assertThat(arrayWithOneItem.hasNext()).isTrue();
        }
    }

    @Test
    public void testNext() throws Exception
    {
        final Integer[] integers = new Integer[2];
        integers[0] = 0;
        integers[1] = 1;

        final ArrayLoopingIterator<Integer> arrayLoopingIterator = new ArrayLoopingIterator<>(integers);

        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(0);
        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(0);
        Assertions.assertThat(arrayLoopingIterator.next()).isEqualTo(0);

        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(1);
        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(1);
        Assertions.assertThat(arrayLoopingIterator.next()).isEqualTo(1);

        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(0);
        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(0);
        Assertions.assertThat(arrayLoopingIterator.next()).isEqualTo(0);

        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(1);
        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(1);
        Assertions.assertThat(arrayLoopingIterator.next()).isEqualTo(1);

        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(0);
        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(0);
        Assertions.assertThat(arrayLoopingIterator.next()).isEqualTo(0);

        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(1);
        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(1);
        Assertions.assertThat(arrayLoopingIterator.next()).isEqualTo(1);

        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(0);
        Assertions.assertThat(arrayLoopingIterator.peek()).isEqualTo(0);
    }
}