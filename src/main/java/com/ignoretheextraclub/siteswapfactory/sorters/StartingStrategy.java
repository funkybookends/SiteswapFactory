package com.ignoretheextraclub.siteswapfactory.sorters;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.function.UnaryOperator;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;

/**
 * Starting strategies must be able to tell which of two rotations are better. It must return {@code true} if the first
 * is preferable to the {@code second}
 *
 * @author Caspar Nonclercq
 */
public interface StartingStrategy extends UnaryOperator<GeneralCircuit>
{

    /**
     * The main method that needs to be implemented. When sorting, the state sorter will be asked which two state sequences is preferred.
     * <p>
     * In a tie break, the implementer can return either true or false.
     *
     * @param first  the first candidate.
     * @param second the second candidate.
     *
     * @return if the first is preferred
     *
     * @throws InvalidSiteswapException      if the sorter does not think the siteswap is valid
     * @throws UnsupportedOperationException if the sorter is unable to sort for this siteswap type.
     */
    boolean test(GeneralCircuit first, GeneralCircuit second) throws InvalidSiteswapException, UnsupportedOperationException;

    /**
     * Returns the simple name for this sorting strategy.
     *
     * @return the name.
     */
    String getName();

    /**
     * A human friendly description of how this sorter sorts.
     *
     * @return a description.
     */
    String getDescription(Locale locale);

    @Override
    default GeneralCircuit apply(GeneralCircuit generalCircuit)
    {
        return generalCircuit.getRotationStream()
            .reduce((first, second) -> this.test(first, second) ? first : second)
            .orElseThrow(NoSuchElementException::new);
    }
}
