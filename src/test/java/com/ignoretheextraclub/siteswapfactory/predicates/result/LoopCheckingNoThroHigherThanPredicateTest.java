package com.ignoretheextraclub.siteswapfactory.predicates.result;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedVanillaSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Created by caspar on 23/10/17.
 */
@RunWith(JUnitParamsRunner.class)
public class LoopCheckingNoThroHigherThanPredicateTest
{
    @Test
    @Parameters
    public void testApply(final Thro maxThro, final String siteswapConstructor, final boolean expected) throws Exception
    {
        final LoopCheckingNoThroHigherThanPredicate noThroHigherThanPredicate = new LoopCheckingNoThroHigherThanPredicate(maxThro);
        final GeneralCircuit states = TwoHandedVanillaSiteswapFactory.getTwoHandedSiteswap(siteswapConstructor).getGeneralCircuit();
        Assertions.assertThat(noThroHigherThanPredicate.test(states)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{VanillaThro.get(3), "4", false},
            new Object[]{VanillaThro.get(3), "3", true},
            new Object[]{VanillaThro.get(3), "2", true},
            new Object[]{VanillaThro.get(7), "456", true},
            new Object[]{VanillaThro.get(5), "456", false},
            // TODO add more
        };
    }
}