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

package com.ignoretheextraclub.siteswapfactory.predicates.result;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

import org.apache.commons.collections4.iterators.ArrayIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.ThroCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

/**
 * Returns a predicate that is suitable for resultPredicates
 *
 * @author Caspar Nonclercq
 */
public class LoopCheckingThroCombinationPredicate implements Predicate<GeneralCircuit>
{
    private static final Logger LOG = LoggerFactory.getLogger(ThroCombinationPredicate.class);

    protected final Thro[] throCombination;

    public LoopCheckingThroCombinationPredicate(final Thro... throCombination)
    {
        if (throCombination[0] == null)
        {
            throw new IllegalArgumentException("first element cannot be null");
        }
        if (throCombination[throCombination.length -1] == null)
        {
            throw new IllegalArgumentException("last element cannot be null");
        }
        this.throCombination = throCombination;
    }

    @Override
    public boolean test(final GeneralCircuit generalCircuit)
    {
        final Iterator<GeneralCircuit> rotationIteator = generalCircuit.getRotationIteator();

        while (rotationIteator.hasNext())
        {
            final GeneralCircuit circuit = rotationIteator.next();
            if (innerTest(circuit))
            {
                return true;
            }
        }

        return false;
    }

    private boolean innerTest(final GeneralCircuit circuit)
    {
        final ArrayLoopingIterator<Thro> throArrayLoopingIterator = new ArrayLoopingIterator<>(circuit.getThros());
        final ArrayIterator<Thro> combinationIterator = new ArrayIterator<>(throCombination);

        while (combinationIterator.hasNext())
        {
            final Thro next = combinationIterator.next();
            final Thro actual = throArrayLoopingIterator.next();

            if (next != null && !next.equals(actual))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a predicate that requires the state
     *
     * @param thros
     * @return
     */
    public static Predicate<GeneralCircuit> requireAnyOneOf(final Thro[] thros)
    {
        if (thros.length == 0)
        {
            throw new IllegalArgumentException("No thros provided");
        }

        Predicate<GeneralCircuit> predicate = new LoopCheckingThroCombinationPredicate(thros[0]);

        if (thros.length > 1)
        {
            for (int i = 1; i < thros.length; i++)
            {
                predicate = predicate.or(new LoopCheckingThroCombinationPredicate(thros[i]));
            }
        }

        return predicate;
    }

    /**
     * Returns a predicate than bans all of these single throws.
     *
     * @param thros the throws to ban. Not a combination, but all individually.
     * @return a predicate.
     */
    public static Predicate<GeneralCircuit> banAllSingleThros(final Thro... thros)
    {
        if (thros.length == 0)
        {
            throw new IllegalArgumentException("No thros provided");
        }

        Predicate<GeneralCircuit> predicate = new LoopCheckingThroCombinationPredicate(thros[0]).negate();

        if (thros.length > 1)
        {
            for (int i = 1; i < thros.length; i++)
            {
                predicate = predicate.and(new LoopCheckingThroCombinationPredicate(thros[i]).negate());
            }
        }

        return predicate;
    }

    @Override
    public String toString()
    {
        return "LoopCheckingThroCombinationPredicate{" + Arrays.toString(throCombination) + "}";
    }
}
