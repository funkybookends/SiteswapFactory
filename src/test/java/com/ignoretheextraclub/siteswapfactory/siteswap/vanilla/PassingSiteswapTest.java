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
 *
 *
 */

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.impl.PassingSiteswapFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class PassingSiteswapTest
{

	@Test
	public void test7ClubPassPassSelfTwice() throws Exception
	{
		final PassingSiteswap passingSiteswap = (PassingSiteswap) PassingSiteswapFactory.getPassingSiteswap("<3|4p1|3|4p3><4|3|4|3><4p2|3|4p4|3>");

		assertThat(passingSiteswap.getStartingNumberOfObjects(0)).isEqualTo(2);
		assertThat(passingSiteswap.getStartingNumberOfObjects(4)).isEqualTo(1);

		assertThat(passingSiteswap.getStartingNumberOfObjects(1)).isEqualTo(2);
		assertThat(passingSiteswap.getStartingNumberOfObjects(5)).isEqualTo(2);

		assertThat(passingSiteswap.getStartingNumberOfObjects(2)).isEqualTo(2);
		assertThat(passingSiteswap.getStartingNumberOfObjects(6)).isEqualTo(1);

		assertThat(passingSiteswap.getStartingNumberOfObjects(3)).isEqualTo(2);
		assertThat(passingSiteswap.getStartingNumberOfObjects(7)).isEqualTo(2);
	}

	@Test
	public void test7ClubPassPassSelf() throws Exception
	{
		final PassingSiteswap passingSiteswap = (PassingSiteswap) PassingSiteswapFactory.getPassingSiteswap("<3|4p1><4|3><4p2|3|>");

		assertThat(passingSiteswap.getStartingNumberOfObjects(0)).isEqualTo(2);
		assertThat(passingSiteswap.getStartingNumberOfObjects(2)).isEqualTo(1);

		assertThat(passingSiteswap.getStartingNumberOfObjects(1)).isEqualTo(2);
		assertThat(passingSiteswap.getStartingNumberOfObjects(3)).isEqualTo(2);
	}
}