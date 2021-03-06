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