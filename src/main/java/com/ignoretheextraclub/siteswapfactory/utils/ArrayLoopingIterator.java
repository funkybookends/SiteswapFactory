package com.ignoretheextraclub.siteswapfactory.utils;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 Created by caspar on 01/08/17.
 */
public class ArrayLoopingIterator<T> implements Iterator<T>
{
    private final T[] array;
    private int pointer;

    public ArrayLoopingIterator(final T[] array)
    {
        this(array, 0);
    }

    public ArrayLoopingIterator(final T[] array, final int pointer)
    {
        this.array = array;
        this.pointer = pointer;
    }

    @Override
    public boolean hasNext()
    {
        return array.length > 0;
    }

    @Override
    public T next()
    {
        final T next = array[pointer];
        pointer = (pointer + 1) % array.length;
        return next;
    }

    public T peek()
    {
        return array[pointer];
    }

    public T previous()
    {
        pointer--;
        if (pointer < 0)
        {
            pointer = array.length -1;
        }
        return array[pointer];
    }

    public T previous(final int positions)
    {
        pointer -= positions;

        while (pointer < 0)
        {
            pointer =  pointer + array.length -1;
        }

        if (pointer >= array.length)
        {
            pointer %= array.length;
        }

        return array[pointer];
    }

    public ArrayLoopingIterator<T> clone()
    {
        return new ArrayLoopingIterator<T>(array, pointer);
    }

    @Override
    public void forEachRemaining(final Consumer<? super T> action)
    {
        throw new UnsupportedOperationException("forEachRemaining: Method would never terminate. Consider using a stream and using .limit(int)");
    }
}
