package com.ignoretheextraclub.siteswapfactory.converter.vanilla.hefflish;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.impl.FourHandedSiteswapFactory;

/**
 * Created by caspar on 26/12/17.
 */
public class FourHandedSiteswapToHefflishSequenceTest
{
	@Rule
	public JUnitSoftAssertions softly = new JUnitSoftAssertions();

	public FourHandedSiteswapToHefflishSequence converter = FourHandedSiteswapToHefflishSequence.get();

	@Test
	public void testConvert() throws Exception
	{
		softly.assertThat(converter.apply(FourHandedSiteswapFactory.getFourHandedSiteswap("789A6"), 0)).isEqualTo("Pass, Double, Self, Heff, Trelf");
		softly.assertThat(converter.apply(FourHandedSiteswapFactory.getFourHandedSiteswap("789A6"), 1)).isEqualTo("Heff, Trelf, Pass, Double, Self");
		// todo add more
	}
}