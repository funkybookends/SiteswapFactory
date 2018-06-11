/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
