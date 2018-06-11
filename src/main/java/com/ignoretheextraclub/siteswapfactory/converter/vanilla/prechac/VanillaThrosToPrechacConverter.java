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

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Converts an array of {@link VanillaThro} to their Prechac Representation. Does not effect
 * the ordering, and therefore likely users want to use {@link com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.GlobalToLocalBiConverter}
 * to get the local representation.
 * @author Caspar Nonclercq
 */
public class VanillaThrosToPrechacConverter implements Function<VanillaThro[], String>
{
    private static VanillaThrosToPrechacConverter INSTANCE;

    private static final String DELIMETER = " ";

    private VanillaThrosToPrechacConverter()
    {
        // Singleton
    }

    public static VanillaThrosToPrechacConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VanillaThrosToPrechacConverter();
        }
        return INSTANCE;
    }

    @Override
    public String apply(VanillaThro[] vanillaThros)
    {
        return Arrays.stream(vanillaThros)
            .map(VanillaThroToPrechacConverter.get())
            .collect(Collectors.joining(DELIMETER));
    }
}
