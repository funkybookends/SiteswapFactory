package com.ignoretheextraclub.siteswapfactory.generator.sequence.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.generator.sequence.RouteSearcher;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * An Iterative Deepening Depth First Searcher.
 *
 * This does not generate siteswaps, but instead finds a state path from the source to the goal, and is
 * more intended to find transtions, entries and exits.
 *
 * This implementation sorts the {@link State}s using their {@link Comparable} interface to find the route.
 * It also provides many protected methods to allow for the behaviour to be modified.
 *
 * @author Caspar Nonclercq
 */
public class IddfsRouteSearcher implements RouteSearcher
{
    private static final Logger LOG = LoggerFactory.getLogger(IddfsRouteSearcher.class);

    /**
     * The default maximum depth. Can be overridden in {@link #getMaxDepth()}
     */
    private static final int MAX_DEPTH = 15;

    private final int maxDepth;

    public IddfsRouteSearcher()
    {
        maxDepth = MAX_DEPTH;
    }

    public IddfsRouteSearcher(final int maxDepth)
    {
        this.maxDepth = maxDepth;
    }

    @Override
    public GeneralPath findRoute(final State source, final State goal) throws TransitionException
    {
        Objects.requireNonNull(source, "source cannot be null");
        Objects.requireNonNull(goal, "goal cannot be null");
        if (source.equals(goal))
        {
            throw new IllegalArgumentException("source and goal are equal");
        }

        final List<Thro> result = iterativeDeepeningDepthFirstSearch(source, goal);

        if (result == null)
        {
            throw new TransitionException("A route could not be found between [" + source + "] and [" + goal + "]");
        }

        final GeneralPath generalPath = new GeneralPath(source);

        result.forEach(generalPath::push);

        return generalPath;

    }

    /**
     * Defines the iterative deepening depth first search algorightm.
     *
     * @param source The starting state
     * @param goal   the goal state
     * @return The path between the source and the goal.
     * @see #getStartingDepth() to manually set the first depth to search for
     * @see #getMaxDepth()  to override the {@link #MAX_DEPTH} value
     */
    protected List<Thro> iterativeDeepeningDepthFirstSearch(final State source, final State goal)
    {
        int currentMaxDepth = getStartingDepth();

        List<Thro> route;

        do
        {
            LOG.trace("DLS to depth: {}", currentMaxDepth);
            route = depthLimitedSearch(source, goal, currentMaxDepth);
            currentMaxDepth++;
        }
        while (route == null && currentMaxDepth < getMaxDepth());

        return route;
    }


    /**
     * The depth limited search from the source to the goal.
     *
     * @param source The start.
     * @param goal   The goal.
     * @param depth  The current depth.
     * @return A path from source to goal.
     * @see #getTransitions(State) to modofy the searching behaviour
     * @see #isAcceptable(List) to block intermedate results and continue searching
     */
    protected List<Thro> depthLimitedSearch(final State source, final State goal, final int depth)
    {
        if (depth > 0)
        {
            LOG.trace("Looking at depth {}", depth);
            for (final Thro transition : getTransitions(source))
            {
                LOG.trace("Looking at {} -> {}", source, transition);

                final State nextState = source.thro(transition);

                if (nextState.equals(goal))
                {
                    final LinkedList<Thro> result = new LinkedList<>();
                    result.add(transition);
                    return result;
                }

                final List<Thro> result = depthLimitedSearch(nextState, goal, depth - 1);

                if (result != null)
                {
                    if (isAcceptable(result))
                    {
                        result.add(0, transition);
                        return result;
                    }
                    else
                    {
                        LOG.debug("intermediate result: {} was not acceptable.", result);
                    }
                }

            }
        }
        return null;
    }

    /**
     * This method allows for subclasses to define certain routes as unacceptable, and force the search to continue.
     *
     * By default all routes are allowed.
     *
     * @param subPath the path from some state to the goal.
     * @return true if this subPath can be part of the result route, otherwise false.
     */
    protected boolean isAcceptable(final List<Thro> subPath)
    {
        return true;
    }

    /**
     * Return an iterator of the next states that can be searched for to find the goal.
     *
     * @param source The source
     * @return An iterator of descendants.
     */
    private Iterable<Thro> getTransitions(final State source)
    {
        return source.getAvailableThrows();
    }

    /**
     * Allows for shortcutting of lower levels of the search.
     *
     * Useful if it is a requirement that the route is n states long.
     *
     * @return The first depth to search.
     */
    protected int getStartingDepth()
    {
        return 0;
    }

    /**
     * The maximum depth of the search.
     *
     * @return The maximum allowed depth
     */
    protected int getMaxDepth()
    {
        return maxDepth;
    }
}
