package com.ignoretheextraclub.siteswapfactory.converter.vanilla.prechac;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 11/11/17.
 */
public class VanillaThroToPrechacConverterTest
{
	@Rule
	public JUnitSoftAssertions softly = new JUnitSoftAssertions();

	private VanillaThroToPrechacConverter converter = VanillaThroToPrechacConverter.get();

	@Test
	public void testConvert() throws Exception
	{
		softly.assertThat(converter.apply(VanillaThro.get(0))).isEqualTo("0");
		softly.assertThat(converter.apply(VanillaThro.get(2))).isEqualTo("1");
		softly.assertThat(converter.apply(VanillaThro.get(4))).isEqualTo("2");
		softly.assertThat(converter.apply(VanillaThro.get(6))).isEqualTo("3");
		softly.assertThat(converter.apply(VanillaThro.get(8))).isEqualTo("4");
		softly.assertThat(converter.apply(VanillaThro.get(10))).isEqualTo("5");
		softly.assertThat(converter.apply(VanillaThro.get(12))).isEqualTo("6");
		softly.assertThat(converter.apply(VanillaThro.get(14))).isEqualTo("7");
		softly.assertThat(converter.apply(VanillaThro.get(1))).isEqualTo("0.5p");
		softly.assertThat(converter.apply(VanillaThro.get(3))).isEqualTo("1.5p");
		softly.assertThat(converter.apply(VanillaThro.get(5))).isEqualTo("2.5p");
		softly.assertThat(converter.apply(VanillaThro.get(7))).isEqualTo("3.5p");
		softly.assertThat(converter.apply(VanillaThro.get(9))).isEqualTo("4.5p");
		softly.assertThat(converter.apply(VanillaThro.get(11))).isEqualTo("5.5p");
		softly.assertThat(converter.apply(VanillaThro.get(13))).isEqualTo("6.5p");
		softly.assertThat(converter.apply(VanillaThro.get(15))).isEqualTo("7.5p");
	}

	@Test
	public void testNull() throws Exception
	{
		assertThatThrownBy(() -> converter.apply(null)).isInstanceOf(NullPointerException.class)
			.hasMessageContaining("thro");
	}
}