package com.ignoretheextraclub.siteswapfactory.siteswap;

import com.ignoretheextraclub.siteswapfactory.sorters.StateSorter;
import com.ignoretheextraclub.siteswapfactory.state.AbstractState;
import com.ignoretheextraclub.siteswapfactory.thros.AbstractThro;

/**
 Created by caspar on 26/06/17.
 */
public interface Siteswap<Thro extends AbstractThro, State extends AbstractState<Thro>>
{
    /**
     The number of people needed for the siteswap to be juggled

     @return the number of jugglers.
     */
    int getNumJugglers();

    /**
     The number of hands needed to juggled this siteswap.

     @return the number of hands.
     */
    int getNumHands();

    /**
     The total number of objects required for the siteswap to be juggled

     @return the number of objects.
     */
    int getNumObjects();

    /**
     The number of beats before the siteswap repeats. True for the siteswap, not
     neccessarily for the jugglers.

     @return the period.
     */
    int getPeriod();

    /**
     The throws needed to juggled the siteswap.

     @return an array of the {@link Thro} type.

     @see #getThrowsForJuggler(int)
     */
    Thro[] getThrows();

    /**
     Returns the throws for a specific juggler

     @param forJuggler the juggler to return the throws for

     @return the list of throws for that juggler

     @throws IndexOutOfBoundsException if the forJuggler param is less than zero or greater than the number of jugglers
     @see #getNumJugglers()
     @see #getThrows()
     */
    Thro[] getThrowsForJuggler(int forJuggler) throws IndexOutOfBoundsException;

    /**
     The states this siteswap moves through

     @return an array of the {@link State} type.
     */
    State[] getStates();

    /**
     True if the any state in the list of states is a ground state.
     <p>
     A ground state is the lowest "energy" state, and usually indicates, that a there is a
     throw from the ground state that results in the ground state.

     @return if this siteswap moves through the ground state.
     */
    boolean isGrounded();

    /**
     True if the the list of states is unique.

     @return if there is no state repeated.

     @see #getStates()
     */
    boolean isPrime();

    /**
     The highest throw. It is up to the {@link Thro} type to determine what is "highest".

     @return The highest {@link Thro}.
     */
    Thro getHighestThro();

    /**
     @return
     */
    StateSorter<Thro, State> getSorter();

    /**
     Get the number of objects required for a hand when starting the siteswap

     @param forHand the hand for which the number should be returned

     @return the number of objects needed

     @throws IndexOutOfBoundsException if the forHand param is less than zero or greater than the number of hands.
     @see #getNumHands()
     */
    int getStartingNumberOfObjects(int forHand) throws IndexOutOfBoundsException;

    /**
     Return a concise name for the type of this Pattern. e.g. "Two Handed Siteswap".

     @return a concise name for the type.
     */
    String getType();

    /**
     A concise representation of the siteswap.

     @return a concise represetation of the siteswap.
     */
    String toString();

    /**
     True if the other siteswap is the same as this one. It is up to the implementation to decide what constitutes
     equality.

     @param other The other siteswap to test for equality

     @return if they are equal.

     @see #same(Siteswap)
     */
    boolean equals(Siteswap other);

    /**
     Less stringent than {@link #equals(Object)}. Two siteswaps may be the same, but not equal. For example a rotation.

     @param other the other siteswap to test for sameness

     @return if they are the same.

     @see #equals(Siteswap)
     */
    boolean same(Siteswap other);
}
