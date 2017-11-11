package com.ignoretheextraclub.siteswapfactory.converter.vanilla.prechac;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.junit.Assert.*;

/**
 * Created by caspar on 11/11/17.
 */
public class VanillaThrosToPrechacConverterTest
{
	@Rule
	public JUnitSoftAssertions softly = new JUnitSoftAssertions();

	public VanillaThrosToPrechacConverter converter = VanillaThrosToPrechacConverter.get();

	@Test
	public void testConvert() throws Exception
	{
		softly.assertThat(converter.apply(thros(get(1), get(4), get(7)))).isEqualTo("0.5p 2 3.5p");
		// todo add more
	}
}