package com.ignoretheextraclub.siteswapfactory.siteswap;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.iterators.TransformIterator;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.sun.org.apache.bcel.internal.generic.IUSHR;

/**
 * Encapulates the state of a siteswap at any point.
 *
 * @author Caspar Nonclercq
 */
public interface State extends Comparable
{
    /**
     * Get the highest throw available to this state, this throw may not be legal.
     *
     * @return the maxiumum throw
     */
    Thro getMaxThrow();

    /**
     * Returns a measure of how far this state is from the ground state.
     *
     * Different implementations may have their own definition, therefore excitedness may not be comparable across
     * implementations.
     *
     * Ground states must have the lowest excitedness.
     *
     * @return excitedness
     */
    long excitedness();

    /**
     * The set of states this state can move to from any throw.
     *
     * @return the set of states
     */
    default Iterator<State> getNextStates()
    {
        return new TransformIterator<>(getAvailableThrows(), this::thro);
    }

    /**
     * Get the set of available throws to this state.
     *
     * @return the set of throws
     */
    Iterator<Thro> getAvailableThrows();

    /**
     * Returns the throw that would cause a transition to the next state. Users are encouraged to use
     * {@link #canTransition(State)} to first check if a transition exists.
     *
     * @param toNextState
     * @return the throw
     * @throws TransitionException if no throw exists
     */
    default Thro getThrow(State toNextState) throws TransitionException
    {
        final Iterator<Thro> availableThrows = getAvailableThrows();

        while (availableThrows.hasNext())
        {
            final Thro thro = availableThrows.next();

            if (this.thro(thro).equals(toNextState))
            {
                return thro;
            }
        }

        throw new TransitionException("Thro to next state could not be found");
    }

    /**
     * Similar to {@link #getThrow(State)} however may be optimized.
     *
     * @param toNextState The next state
     * @return If the next state is reachable in a single transition.
     */
    default boolean canTransition(State toNextState)
    {
	    return IteratorUtils.contains(getNextStates(), this);
    }

    /**
     * Returns the next state if this throw were to be thrown.
     *
     * @param thro
     * @return the next state
     * @throws BadThrowException if this throw cannot be throw.
     * @see #getAvailableThrows()
     */
    State thro(Thro thro) throws BadThrowException;

    /**
     * Returns the number of objects needed for this state
     *
     * @return
     */
    int getNumObjects();

    /**
     * returns if this is a ground state
     *
     * @return true if this is the ground state.
     */
    boolean isGroundState();
}
