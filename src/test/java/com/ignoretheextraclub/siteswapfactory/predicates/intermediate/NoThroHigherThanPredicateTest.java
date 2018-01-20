package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StartingStateAndThrosToGeneralPathConverter;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;

@RunWith(JUnitParamsRunner.class)
public class NoThroHigherThanPredicateTest
{
    @Test
    @Parameters
    public void testApply(final Thro maxThro, final GeneralPath states, final boolean expected) throws Exception
    {
        final NoThroHigherThanPredicate noThroHigherThanPredicate = new NoThroHigherThanPredicate(maxThro);

        Assertions.assertThat(noThroHigherThanPredicate.test(states)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{VanillaThro.get(3), StartingStateAndThrosToGeneralPathConverter.getSequence(XXX__, VanillaThro.get(3)), true},
            new Object[]{VanillaThro.get(3), StartingStateAndThrosToGeneralPathConverter.getSequence(XXX__, VanillaThro.get(4)), false},
            new Object[]{VanillaThro.get(3), StartingStateAndThrosToGeneralPathConverter.getSequence(XX_X, VanillaThro.get(2)), true},
            // TODO add more
        };
    }
}