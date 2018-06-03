package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.MultiHandThroToPassingStringConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.MultiHandThro;

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
