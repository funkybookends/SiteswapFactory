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

package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.VanillaThrosToStartingStateConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.StringToFourHandedSiteswapThrosConverter;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

/**
 * Creates a {@link FourHandedSiteswap} give a {@link SiteswapRequest}. Respects the reducing and sorting preferences.
 *
 * @author Caspar Nonclercq
 */
public class StringToFourHandedSiteswapConstructor implements SiteswapConstructor<FourHandedSiteswap>
{
    private static StringToFourHandedSiteswapConstructor INSTANCE;

    private StringToFourHandedSiteswapConstructor()
    {
        // Singleton
    }

    public static StringToFourHandedSiteswapConstructor get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StringToFourHandedSiteswapConstructor();
        }
        return INSTANCE;
    }


    @Override
    public FourHandedSiteswap apply(final SiteswapRequest siteswapRequest)
    {
        final String siteswap = siteswapRequest.getConstructor().toString();
        FourHandedSiteswapThro[] fourHandedSiteswapThros = StringToFourHandedSiteswapThrosConverter.convert(siteswap);

        final VanillaState startingState = VanillaThrosToStartingStateConverter.getFirstState(fourHandedSiteswapThros);

        final GeneralPath generalPath = new GeneralPath(startingState);

        for (final FourHandedSiteswapThro fourHandedSiteswapThro : fourHandedSiteswapThros)
        {
            generalPath.push(fourHandedSiteswapThro);
        }

        final GeneralCircuit generalCircuit = generalPath.toGeneralCircuit();

        return GeneralCircuitToFourHandedSiteswapConstructor.get().apply(siteswapRequest.toBuilder().createSiteswapRequest(generalCircuit));
    }

    @Override
    public boolean accepts(final Object object)
    {
        return String.class.isInstance(object);
    }

    @Override
    public String toString()
    {
        return "StringToFourHandedSiteswapConstructor{}";
    }
}