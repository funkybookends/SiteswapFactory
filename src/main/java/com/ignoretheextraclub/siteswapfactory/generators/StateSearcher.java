package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.sorters.SortingUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.StateSorter;
import com.ignoretheextraclub.siteswapfactory.state.AbstractState;
import com.ignoretheextraclub.siteswapfactory.thros.AbstractThro;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by caspar on 11/12/16.
 */
public class StateSearcher<Throw extends AbstractThro, State extends AbstractState<Throw>> extends Thread
{
    private final int resultLimit;
    private final long timeLimitDuration;
    private final int finalPeriod;

    /**
     * The predicates will be examined at every level of the search.
     * So when receiving a State[] of size n, you can be assured it was accepted with size n-1.
     * You are encouraged to keep these cheap.
     */
    private final List<Predicate<State[]>> predicates;
    private final Queue<State> startingStates;
    private final Set<State> statesStartedFrom = new HashSet<>();
    private final Consumer<State[]> consumer;
    private final StateSorter<Throw, State> sorter;
    private final Mode mode;

    private int results;
    private long timeLimitMoment;

    private enum Mode
    {
        FIXED_STARTING_STATES,
        FIND_NEW_STARTING_STATES;
    }

    public StateSearcher(final int resultLimit,
                         final long timeLimitDuration,
                         final int finalPeriod,
                         final List<Predicate<State[]>> predicates,
                         final Queue<State> startingStates,
                         final Consumer<State[]> consumer,
                         @Nullable final StateSorter<Throw, State> sorter)
    {
        this.resultLimit = resultLimit;
        this.timeLimitDuration = timeLimitDuration;
        this.finalPeriod = finalPeriod;
        this.predicates = predicates;
        this.startingStates = startingStates;
        this.consumer = consumer;
        this.sorter = sorter;
        results = 0;

        this.mode = Mode.FIXED_STARTING_STATES;
    }

    public StateSearcher(final int resultLimit,
                         final long timeLimitDuration,
                         final int finalPeriod,
                         final List<Predicate<State[]>> predicates,
                         final State startingState,
                         final Consumer<State[]> consumer,
                         @Nullable final StateSorter<Throw, State> sorter,
                         final boolean fromAllStates)
    {
        this.resultLimit = resultLimit;
        this.timeLimitDuration = timeLimitDuration;
        this.finalPeriod = finalPeriod;
        this.predicates = predicates;
        this.consumer = consumer;
        this.sorter = sorter;
        this.startingStates = new LinkedBlockingQueue<>();
        this.startingStates.add(startingState);

        if (fromAllStates)
        {
            this.mode = Mode.FIXED_STARTING_STATES;
        }
        else
        {
            this.mode = Mode.FIND_NEW_STARTING_STATES;
        }

    }

    @Override
    public void run()
    {
        this.timeLimitMoment = System.currentTimeMillis() + timeLimitDuration;
        while (!startingStates.isEmpty())
        {
            final State startingState = startingStates.poll();
            if (!statesStartedFrom.contains(startingState))
            {
                Stack<State> path = new Stack<>();
                path.push(startingState);
                statesStartedFrom.add(startingState);
                try
                {
                    generate(path);
                }
                catch (final ResultLimitReached | TimeLimitReached stopper)
                {
                    break;
                }
                catch (InterruptedException e)
                {
                    return;
                }

            }
        }
    }

    private boolean acceptable(final State[] candidate)
    {
        for (Predicate<State[]> predicate : predicates)
        {
            if (!predicate.test(candidate))
            {
                return false;
            }
        }
        return true;
    }

    private void generate(Stack<State> path) throws ResultLimitReached, TimeLimitReached, InterruptedException
    {
        if (isInterrupted())
        {
            throw new InterruptedException("Interrupted after finding " + results + " results of " + resultLimit);
        }

        if (System.currentTimeMillis() > timeLimitMoment)
        {
            throw new TimeLimitReached();
        }

        if (path.size() < finalPeriod)
        {
            for (Object abstractChild : path.peek().getNextStates())
            {
                final State child = (State) abstractChild;
                if (mode == Mode.FIND_NEW_STARTING_STATES && !statesStartedFrom.contains(child) && !startingStates.contains(child))
                {
                    startingStates.add(child);
                    // If the first state is a ground state then the current path is the transition!
                }
                path.push(child);
                if (acceptable(getPathAsStateArray(path))) generate(path);
            }
        }
        else if (path.size() == finalPeriod && path.lastElement().canTransition(path.firstElement()))
        {
            handleResult(getPathAsStateArray(path));
            if (++results >= resultLimit) throw new ResultLimitReached();
        }
        else if (path.size() > finalPeriod)
        {
            throw new RuntimeException("Period should never be longer than final period");
        }

        path.pop();
    }

    private void handleResult(State[] result)
    {
        try
        {
            if (sorter != null)
            {
                result = SortingUtils.sort(result, sorter);
            }
            consumer.accept(result);
        }
        catch (final InvalidSiteswapException cause)
        {
            // TODO remove when not debugging
            throw new RuntimeException("Found an invalid siteswap - should all be valid!", cause);
        }
    }

    @SuppressWarnings("unchecked")
    private State[] getPathAsStateArray(Stack<State> path)
    {
        return path.toArray((State[]) Array.newInstance(path.lastElement().getClass(), path.size()));
    }

    private class ResultLimitReached extends Exception {}

    private class TimeLimitReached extends Exception {}
}
