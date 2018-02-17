package com.ignoretheextraclub.siteswapfactory.generator.sequence;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;

/**
 * Joins {@link GeneralPath}s and {@link GeneralCircuit}s together ensuring transitions are dealt with.
 *
 * @author Caspar Nonclercq
 */
public interface StateJoiner
{
	/**
	 * Returns a state array that contains the first {@code State[]), followed by the second {@code State[]} with transition throws if needed
	 * and the transition throws to return to the first state.
	 *
	 * @param first  The first sequence to include
	 * @param second The second sequence to include
	 * @return A full loop
	 * @throws TransitionException if they cannot be joined, or if no join was found.
	 */
	GeneralPath connectPath(GeneralPath first, GeneralPath second) throws TransitionException;

	/**
	 * Joins the two paths on both ends, creating a general circuit
	 *
	 * @param first  The first path
	 * @param second The second path
	 * @return The general circuit containing both paths.
	 * @throws TransitionException
	 */
	GeneralCircuit joinToCircuit(GeneralPath first, GeneralPath second) throws TransitionException;

	/**
	 * Joins the first general circuit to the second providing the intermediate throws that join
	 * the first to the second, and the throws that join the second to the first. Neither first
	 * nor second will be rotated, and the result will begin with the first.
	 *
	 * @param first  The first circuit
	 * @param second The second circuit
	 * @return The general circuit beginning with the first, connecting to the second, then the second and connecting to the first.
	 */
	default GeneralCircuit joinExactly(GeneralCircuit first, GeneralCircuit second)
	{
		return joinToCircuit(first.toGeneralPath(), second.toGeneralPath());
	}

	/**
	 * Joins the first circuit to the second where the resultant circuit is the shortest possible,
	 * or one of the shortest possible.
	 *
	 * @param first  The first circuit
	 * @param second The second circuit
	 * @return The shortest general circuit that contains the first and the second as sub paths.
	 */
	GeneralCircuit joinShortest(GeneralCircuit first, GeneralCircuit second);
}
