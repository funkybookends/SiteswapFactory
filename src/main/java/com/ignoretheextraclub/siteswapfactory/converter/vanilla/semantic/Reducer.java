package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

/**
 * Created by caspar on 26/09/17.
 */
public interface Reducer
{
    /**
     * Returns a non repeating array
     * @param duplicated a repeated array
     * @return a non repeating array
     */
    <T> T[] reduce(T[] duplicated);

    interface IntReducer
    {
        int[] apply(int[] duplicated);
    }

    static Reducer identity()
    {
        return new Reducer()
        {
            @Override
            public <T> T[] reduce(final T[] duplicated)
            {
                return duplicated;
            }
        };
    }
}
