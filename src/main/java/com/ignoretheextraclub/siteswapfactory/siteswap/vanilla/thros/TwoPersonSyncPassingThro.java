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

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

public class TwoPersonSyncPassingThro extends MultiHandThro
{
	public TwoPersonSyncPassingThro(final HandSpecificThro[] thros)
	{
		super(thros);
		if (thros.length != 2)
		{
			throw new IllegalArgumentException("Must only have two thros");
		}
	}

	@Override
	public String toString()
	{
		return String.format("<%s%s|%s%s>", thros[0].getNumBeats(), (thros[0].getToHand() == 0 ? "" : "p"),  thros[1].getNumBeats(), (thros[1].getToHand() == 1 ? "" : "p"));
	}

}
