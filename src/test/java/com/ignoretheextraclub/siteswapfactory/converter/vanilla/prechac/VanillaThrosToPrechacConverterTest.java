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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.prechac;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;

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