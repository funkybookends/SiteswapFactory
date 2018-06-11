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

package com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MultiHandThroTest
{
	@Test
	public void GIVEN_classicSyncThrow_WHEN_toString_EXPECT_classicRepresentation() throws Exception
	{
		final MultiHandThro fourXfourX = thros(thro(1, 2), thro(0, 2));
		assertThat(fourXfourX.toString()).isEqualTo("(4x,4x)");

		final MultiHandThro fourFour = thros(thro(0, 2), thro(1, 2));
		assertThat(fourFour.toString()).isEqualTo("(4,4)");
	}

	@Test
	public void GIVEN_onlyOneHand_WHEN_toString_EXPECT_justTheNumberOfBeats() throws Exception
	{
		assertThat(thros(thro(0, 5)).toString()).isEqualTo("5");
		assertThat(thros(thro(0, 7)).toString()).isEqualTo("7");
		assertThat(thros(thro(0, 0)).toString()).isEqualTo("0");
		assertThat(thros(thro(0, 2)).toString()).isEqualTo("2");
	}

	@Test
	public void WHEN_throwOutOfRange_EXPECT_IAE() throws Exception
	{
		assertThatThrownBy(() -> thros(thro(1, 4), thro(2, 4)))
			.isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(() -> new MultiHandThro(null))
			.isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(MultiHandThroTest::thros)
			.isInstanceOf(IllegalArgumentException.class);
	}

	public static MultiHandThro thros(MultiHandThro.HandSpecificThro... thros)
	{
		return new MultiHandThro(thros);
	}

	public static MultiHandThro.HandSpecificThro thro(final int h, final int b)
	{
		return MultiHandThro.HandSpecificThro.get(h, b);
	}
}