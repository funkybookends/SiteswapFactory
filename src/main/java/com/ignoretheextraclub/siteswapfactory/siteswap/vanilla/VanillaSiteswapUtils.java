package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.siteswap.utils.ThroUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

/**
 Created by caspar on 25/07/17.
 */
public final class VanillaSiteswapUtils
{
    private VanillaSiteswapUtils(){}

    public static int getStartingNumberOfObjects(final int numberOfHands,
                                                 final int hand,
                                                 final VanillaThro[] vanillaThros,
                                                 final int numObjects)
    {
        final int period = vanillaThros.length;
        final int thro = ThroUtils.getHighestThro(vanillaThros).getNumBeats();
        final boolean[] landings = new boolean[period + thro];

        final ArrayLoopingIterator<VanillaThro> looper = new ArrayLoopingIterator<>(vanillaThros);

        for (int i = 0; i < landings.length; i++)
        {
            final int landing_position = i + looper.next().getNumBeats();
            if (landing_position < landings.length)
            {
                landings[landing_position] = true;
            }
        }

        int tot = 0;
        int i = 0;
        final int[] hands = new int[numberOfHands];

        while (tot < numObjects)
        {
            if (!landings[i])
            {
                hands[i % numberOfHands]++;
                tot++;
            }
            i++;
        }
        return hands[hand];
    }
}
