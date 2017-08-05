package com.ignoretheextraclub.siteswapfactory.siteswap;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import java.util.Set;

/**
 Created by caspar on 26/07/17.
 */
public interface State extends Comparable
{
    /**
     Get the highest throw available to this state, this throw may not be legal.
     @return the maxiumum throw
     */
    Thro getMaxThrow();

    /**
     Returns a measure of how far this state is from the ground state.

     Different implementations may have their own definition, therefore excitedness may not be comparable across
     implementations.

     Ground states must have the lowest excitedness.
     @return excitedness
     */
    int excitedness();

    /**
     The set of states this state can move to from any throw
     @return the set of states
     */
    Set<State> getNextStates();

    /**
     Get the set of available throws to this state.
     @return the set of throws
     */
    Set<Thro> getAvailableThrows();

    /**
     Returns the throw that would cause a transition to the next state. Users are encouraged to use
     {@link #canTransition(State)} to first check if a transition exists.
     @param toNextState
     @return the throw
     @throws TransitionException if no throw exists
     */
    Thro getThrow(State toNextState) throws TransitionException;

    /**
     Similar to {@link #getThrow(State)} however may be optimized.

     Use {@link #getThrow(State)} to get the throw.
     @param toNextState
     @return
     */
    boolean canTransition(State toNextState);

    /**
     Returns the next state if this throw were to be thrown.
     @param thro
     @return the next state
     @throws BadThrowException if this throw cannot be throw.
     @see #getAvailableThrows()
     */
    State thro(Thro thro) throws BadThrowException;

    /**
     Returns the number of objects needed for this state
     @return
     */
    int getNumObjects();

    /**
     returns if this is a ground state
     @return true if this is the ground state.
     */
    boolean isGroundState();

    /**
     returns the state that prior to the given throw
     @param thro
     @return the prior state
     @throws UnsupportedOperationException if it cannot be determined.
     */
    VanillaState undo(VanillaThro thro) throws UnsupportedOperationException;
}
