package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.MultiHandThroToStringConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

public class MultiHandThrosToStringConverter implements Function<Thro[], String>
{
	private static final MultiHandThroToStringConverter CONVERTER = MultiHandThroToStringConverter.get();

	private static MultiHandThrosToStringConverter INSTANCE;

	private MultiHandThrosToStringConverter()
	{
		// Singleton
	}

	public static MultiHandThrosToStringConverter get()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new MultiHandThrosToStringConverter();
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