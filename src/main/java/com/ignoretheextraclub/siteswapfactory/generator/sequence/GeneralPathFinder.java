package com.ignoretheextraclub.siteswapfactory.generator.sequence;

import java.util.Iterator;
import java.util.stream.Stream;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;

/**
 * Finds all {@link GeneralPath}s.
 */
public interface GeneralPathFinder extends Iterable<GeneralPath>
{
	/**
	 * Create a new stream of general paths.
	 * @return A new stream of general paths
	 */
	Stream<GeneralPath> stream();

	/**
	 * Returns an iterator of general paths.
	 * @return A new iterator of general paths.
	 */
	Iterator<GeneralPath> iterator();
}
