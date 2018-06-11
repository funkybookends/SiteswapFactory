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

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.state.MultiHandedSyncStateTest;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SyncTwoHandedVanillaStringToMultiHandThrosConverterTest
{
	final SyncTwoHandedVanillaStringToMultiHandThrosConverter converter = SyncTwoHandedVanillaStringToMultiHandThrosConverter.get();

	@Test
	public void testFourFour() throws Exception
	{
		final MultiHandThro[] actual = converter.apply("(4,4)");
		assertThat(actual).isEqualTo(new MultiHandThro[]{MultiHandedSyncStateTest.multiHandThro(0, 2, 1, 2)});
	}

	@Test
	public void testSixXFourFour6X() throws Exception
	{
		final MultiHandThro[] actual = converter.apply("(6x,4)(4,6x)");
		assertThat(actual).isEqualTo(new MultiHandThro[]{
			MultiHandedSyncStateTest.multiHandThro(1, 3, 1, 2),
			MultiHandedSyncStateTest.multiHandThro(0, 2, 0, 3)
		});
	}

	@Test
	public void testSixXFourStar() throws Exception
	{
		final MultiHandThro[] actual = converter.apply("(6x,4)*");
		assertThat(actual).isEqualTo(new MultiHandThro[]{
			MultiHandedSyncStateTest.multiHandThro(1, 3, 1, 2),
			MultiHandedSyncStateTest.multiHandThro(0, 2, 0, 3)
		});
	}

	@Test
	public void testWithTooManyThros() throws Exception
	{
		assertThatThrownBy(() -> converter.apply("(4,4,4)"))
			.isInstanceOf(BadThrowException.class);
	}
}