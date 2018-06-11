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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.MultiHandThroToPassingStringConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

public class MultiHandThrosToPassingStringConverter implements Function<Thro[], String>
{
	private static final MultiHandThroToPassingStringConverter CONVERTER = MultiHandThroToPassingStringConverter.get();

	private static MultiHandThrosToPassingStringConverter INSTANCE;

	private MultiHandThrosToPassingStringConverter()
	{
	    // Singleton
	}

	public static MultiHandThrosToPassingStringConverter get()
	{
	    if (INSTANCE == null)
	    {
	        INSTANCE = new MultiHandThrosToPassingStringConverter();
	    }
	    return INSTANCE;
	}

	@Override
	public String apply(final Thro[] thros)
	{
		return Arrays.stream(thros)
			.map(thro -> ((MultiHandThro) thro))
			.map(CONVERTER)
			.collect(Collectors.joining());
	}
}
