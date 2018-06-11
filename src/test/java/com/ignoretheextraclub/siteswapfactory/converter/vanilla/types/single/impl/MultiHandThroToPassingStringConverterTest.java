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

package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl;

import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest.multiHandThro;
import static org.assertj.core.api.Assertions.assertThat;

public class MultiHandThroToPassingStringConverterTest
{
	private MultiHandThroToPassingStringConverter converter = MultiHandThroToPassingStringConverter.get();

	@Test
	public void testOneHand() throws Exception
	{
		assertThat(converter.apply(multiHandThro(0, 3))).isEqualTo("<3>");
	}

	@Test
	public void test6ClubUltimates() throws Exception
	{
		assertThat(converter.apply(multiHandThro(1, 3, 0, 3))).isEqualTo("<3p|3p>");
	}

	@Test
	public void testExchangeAndSelfer() throws Exception
	{
		assertThat(converter.apply(multiHandThro(2, 3, 1, 3, 0, 3))).isEqualTo("<3p3|3|3p1>");
	}

	@Test
	public void testDoublerZapperAndSelfer() throws Exception
	{
		assertThat(converter.apply(multiHandThro(2, 4, 1, 3, 0, 1))).isEqualTo("<4p3|3|1p1>");
	}

	@Test
	public void testDoubleZappWithTwoJugglers() throws Exception
	{
		assertThat(converter.apply(multiHandThro(1, 4, 0, 1))).isEqualTo("<4p|1p>");
	}
}