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

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest.multiHandThro;

public class MultiHandThrosToPassingStringConverterTest
{
	private MultiHandThrosToPassingStringConverter converter = MultiHandThrosToPassingStringConverter.get();

	@Test
	public void test6ClubTwoCount() throws Exception
	{
		Assertions.assertThat(converter.apply(new Thro[]{
			multiHandThro(1, 3, 0, 3),
			multiHandThro(0, 3, 1, 3)
		})).isEqualTo("<3p|3p><3|3>");
	}

	@Test
	public void testMultipleJugglerPassesAndSelves() throws Exception
	{
		Assertions.assertThat(converter.apply(new Thro[]{
			multiHandThro(1, 3, 0, 3, 2, 4),
			multiHandThro(0, 3, 2, 3, 0, 4),
			multiHandThro(0, 4, 0, 3, 2, 4)
		})).isEqualTo("<3p2|3p1|4><3|3p3|4p1><4|3p1|4>");
	}
}