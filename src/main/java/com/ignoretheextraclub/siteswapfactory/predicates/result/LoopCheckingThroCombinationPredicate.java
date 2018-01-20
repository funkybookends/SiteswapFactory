package com.ignoretheextraclub.siteswapfactory.predicates.result;

import java.util.Arrays;
import java.util.function.Predicate;

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
        for (int i = 0; i < generalCircuit.size(); i++)
        {
            final ArrayLoopingIterator<Thro> throArrayLoopingIterator = new ArrayLoopingIterator<>(generalCircuit.getThros());
            final Thro[] thros = getThros(throArrayLoopingIterator, throCombination.length);

            if (ThroCombinationPredicate.testCombination(thros, throCombination))
            {
                return true;
            }
        }
        return false;
    }

    private Thro[] getThros(final ArrayLoopingIterator<Thro> throArrayLoopingIterator, final int length)
    {
        final Thro[] thros = new Thro[length];

        for (int i = 0; i < thros.length; i++)
        {
            thros[i] = throArrayLoopingIterator.next();
        }

        return thros;
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
