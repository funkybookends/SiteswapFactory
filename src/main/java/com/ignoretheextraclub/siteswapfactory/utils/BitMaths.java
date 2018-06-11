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
