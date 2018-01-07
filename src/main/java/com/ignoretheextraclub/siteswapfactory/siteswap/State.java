package com.ignoretheextraclub.siteswapfactory.siteswap;

import java.util.Set;
import java.util.stream.Collectors;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

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
    default Set<State> getNextStates()
    {
        return getAvailableThrows().stream()
            .map(this::thro)
            .collect(Collectors.toSet());
    }

    /**
     * Get the set of available throws to this state.
     *
     * @return the set of throws
     */
    Set<Thro> getAvailableThrows();

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
        return getAvailableThrows().stream()
            .filter(thro -> this.thro(thro).equals(toNextState))
            .findFirst()
            .orElseThrow(() -> new TransitionException("Thro to next state could not be found"));
    }

    /**
     * Similar to {@link #getThrow(State)} however may be optimized.
     *
     * Use {@link #getThrow(State)} to get the throw.
     *
     * @param toNextState
     * @return
     */
    default boolean canTransition(State toNextState)
    {
        return getNextStates().contains(toNextState);
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
