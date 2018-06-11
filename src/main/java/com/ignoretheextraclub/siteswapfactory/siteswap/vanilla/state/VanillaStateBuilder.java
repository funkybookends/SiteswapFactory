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

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.utils.BitMaths.isSet;
import static com.ignoretheextraclub.siteswapfactory.utils.BitMaths.numBitsSet;

/**
 * A {@link VanillaState} builder that accepts throws and builds the state as the throws are made.
 * New objects are thrown if no existing object is available.
 *
 * @author Caspar Nonclercq
 */
public class VanillaStateBuilder
{
    private int state;

    public VanillaStateBuilder() throws PeriodException, NumObjectsException
    {
        this.state = 0;
    }

    public VanillaStateBuilder thenThrow(final VanillaThro aThro) throws BadThrowException, NumObjectsException
    {
        final int thro = aThro.getNumBeats();

        // Check landing
        if (isSet(state, thro))
        {
            throw new BadThrowException("VanillaThro [" + thro + "] cannot be made: [" + this.toString() + "]");
        }

        // Do throw
        state |= (1 << thro);
        state >>= 1;

        return this;
    }

    public int getGivenObjects()
    {
        return numBitsSet(state);
    }

    @Override
    public String toString()
    {
        return new VanillaState(state).toString();
    }

    public VanillaState getState()
    {
        return new VanillaState(this.state);
    }
}
