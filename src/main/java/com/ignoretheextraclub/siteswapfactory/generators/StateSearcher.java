package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.sorters.SortingUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.StateSorter;
import com.ignoretheextraclub.siteswapfactory.state.AbstractState;
import com.ignoretheextraclub.siteswapfactory.thros.AbstractThro;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by caspar on 11/12/16.
 */
public class StateSearcher<Throw extends AbstractThro, State extends AbstractState<Throw>> extends Thread
{
    private static final int MAX_PERIOD = 9;
    private static final int DEFAULT_RESULT_LIMIT = 1000;
    private static final long DEFAULT_TIME_LIMIT_DURATION = 20 * 60 * 1000;

    private final int resultLimit;
    private final long timeLimitDuration;
    private final int finalPeriod;

    /**
     * The predicates will be examined at every level of the search.
     * So when receiving a State[] of size n, you can be assured it was accepted with size n-1.
     * You are encouraged to keep these cheap.
     */
    private final Collection<Predicate<State[]>> predicates;
    private final Queue<State> startingStates;
    private final Set<State> statesStartedFrom = new HashSet<>();
    private final Consumer<State[]> consumer;
    private final StateSorter<Throw, State> sorter;
    private final Mode mode;

    private int numResults = 0;
    private long timeLimitMoment;
    private boolean completed = false;
    private boolean suspended = false;

    public StateSearcher(final int finalPeriod,
                         final Queue<State> startingStates,
                         final Consumer<State[]> consumer)
    {
        this(DEFAULT_RESULT_LIMIT, DEFAULT_TIME_LIMIT_DURATION, finalPeriod, null, startingStates, consumer, null);
    }

    public StateSearcher(final int resultLimit,
                         final long timeLimitDuration,
                         final int finalPeriod,
                         /*Nullable*/ final Collection<Predicate<State[]>> predicates,
                         final Queue<State> startingStates,
                         final Consumer<State[]> consumer,
                         /*Nullable*/  final StateSorter<Throw, State> sorter)
    {
        this(resultLimit,
                timeLimitDuration,
                finalPeriod,
                predicates != null ? predicates : Collections.emptySet(),
                startingStates,
                consumer,
                sorter,
                Mode.FIXED_STARTING_STATES);
    }

    private StateSearcher(final int resultLimit,
                          final long timeLimitDuration,
                          final int finalPeriod,
                          final Collection<Predicate<State[]>> predicates,
                          final Queue<State> startingStates,
                          final Consumer<State[]> consumer,
                          final StateSorter<Throw, State> sorter,
                          final Mode mode)
    {
        this.resultLimit = resultLimit;
        this.timeLimitDuration = timeLimitDuration;
        this.finalPeriod = validatePeriod(finalPeriod);
        this.predicates = predicates;
        this.startingStates = startingStates;
        this.consumer = consumer;
        this.sorter = sorter;
        this.mode = mode;
    }

    private static int validatePeriod(final int period)
    {
        if (period > MAX_PERIOD)
        {
            throw new IllegalArgumentException("Period cannot be greater than " + MAX_PERIOD);
        }
        if (period < 1)
        {
            throw new IllegalArgumentException("Period must be at least 1");
        }
        return period;
    }

    public StateSearcher(final int finalPeriod,
                         /*Nullable*/  final Collection<Predicate<State[]>> predicates,
                         final State startingState,
                         final Consumer<State[]> consumer,
                         /*Nullable*/  final StateSorter<Throw, State> sorter,
                         final boolean fromAllStates)
    {
        this(DEFAULT_RESULT_LIMIT, DEFAULT_TIME_LIMIT_DURATION, finalPeriod, predicates, startingState, consumer,
                sorter,
                fromAllStates);
    }

    public StateSearcher(final int resultLimit,
                         final long timeLimitDuration,
                         final int finalPeriod,
                         /*Nullable*/  final Collection<Predicate<State[]>> predicates,
                         final State startingState,
                         final Consumer<State[]> consumer,
                         /*Nullable*/  final StateSorter<Throw, State> sorter,
                         final boolean fromAllStates)
    {
        this(resultLimit,
                timeLimitDuration,
                finalPeriod,
                predicates != null ? predicates : Collections.emptySet(),
                queueOf(startingState),
                consumer,
                sorter,
                fromAllStates ? Mode.FIXED_STARTING_STATES : Mode.FIND_NEW_STARTING_STATES);
    }

    private static <Throw extends AbstractThro, State extends AbstractState<Throw>> Queue<State> queueOf(
            final State startingState)
    {
        final LinkedBlockingQueue<State> queue = new LinkedBlockingQueue<>();
        queue.add(startingState);
        return queue;
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
                catch (final InterruptedException e)
                {
                    break;
                }
            }
        }
        completed = true;
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
            throw new InterruptedException("Interrupted after finding " + numResults + " numResults of " + resultLimit);
        }

        if (System.currentTimeMillis() > timeLimitMoment)
        {
            throw new TimeLimitReached();
        }

        synchronized (this)
        {
            while (suspended)
            {
                wait();
            }
        }

        if (path.size() < finalPeriod)
        {
            for (Object abstractChild : path.peek().getNextStates())
            {
                final State child = (State) abstractChild;
                if (mode == Mode.FIND_NEW_STARTING_STATES && !statesStartedFrom.contains(child) &&
                        !startingStates.contains(child))
                {
                    startingStates.add(child);
                    // If the first state is a ground state then the current path is the transition!
                }
                path.push(child);
                if (acceptable(getPathAsStateArray(path))) { generate(path); }
            }
        }
        else if (path.size() == finalPeriod && path.lastElement().canTransition(path.firstElement()))
        {
            handleResult(getPathAsStateArray(path));
            if (++numResults >= resultLimit) { throw new ResultLimitReached(); }
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

    public boolean isCompleted()
    {
        return completed;
    }

    public void pause()
    {
        this.suspended = true;
    }

    public void play()
    {
        this.suspended = false;
    }

    public int getNumResults()
    {
        return numResults;
    }

    private enum Mode
    {
        FIXED_STARTING_STATES,
        FIND_NEW_STARTING_STATES;
    }

    private static class ResultLimitReached extends Exception {}

    private static class TimeLimitReached extends Exception {}
}
