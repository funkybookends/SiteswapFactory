package com.ignoretheextraclub.siteswapfactory.utils;

public final class BitMaths
{
	public static boolean isSet(final long on, final int position)
	{
	    return (on & (1 << position)) > 0;
	}

	public static int numBitsSet(long state)
	{
	    int count = 0;

	    while (state > 0)
	    {
	        count += state & 1;
	        state >>= 1;
	    }

	    return count;
	}
}
