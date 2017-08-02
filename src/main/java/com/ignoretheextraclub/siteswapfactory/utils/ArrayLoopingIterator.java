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
        return true;
    }

    @Override
    public T next()
    {
        final T next = array[pointer];
        pointer = (pointer + 1) % array.length;
        return next;
    }

    @Override
    public void forEachRemaining(final Consumer<? super T> action)
    {
        throw new UnsupportedOperationException("forEachRemaining: Method would never terminate. Consider using a streamn and using .limit(int)");
    }
}
