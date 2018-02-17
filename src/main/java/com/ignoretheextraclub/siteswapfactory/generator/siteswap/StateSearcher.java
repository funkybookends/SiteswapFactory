package com.ignoretheextraclub.siteswapfactory.generator.siteswap;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralCircuit;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import javafx.scene.shape.PathBuilder;

import static java.util.Collections.emptyIterator;

/**
 * The iterator underlying the siteswap generator.
 *
 * @author Caspar Nonclercq
 */
public class StateSearcher<T extends Siteswap> implements Iterator<T>, SiteswapGenerator<T>
{
    private static final Logger LOG = LoggerFactory.getLogger(StateSearcher.class);

    /**
     * Configuration
     */
    private final Iterator<State> startingStates;
    private final int maxPeriod;
    private final Predicate<GeneralPath> intermediatePredicate;
    private final Predicate<GeneralCircuit> resultPredicate;
    private final SiteswapRequestBuilder siteswapRequestBuilder;
    private final SiteswapConstructor<T> siteswapConstructor;

    /**
     * Internal state
     */
    private T next;
    private PathBuilder builder;

    /**
     * Creates a {@link StateSearcher} which will find successive {@link Siteswap}s based on the provided configuration.
     *
     * @param startingStates        The list of starting states which are acceptable.
     * @param maxPeriod             The maximum period of {@link Siteswap}. To restrict to a specific period, include an {@code resultPredicate} requiring this period. This should be set accurately to ensure good performance.
     * @param intermediatePredicate A predicate that will be tested after each addition of a new {@link State} to the internal State[]. If the intermediate state fails, then the last state will be removed, and the next state will be found.
     * @param resultPredicate       A predicate that will be tested prior to attempting to construct a {@link Siteswap}. If it fails then the {@code siteswapConstructor} will not be invoked.
     * @param siteswapConstructor   A function which takes a {@link State}[] and returns a {@link Siteswap}.
     */
    public StateSearcher(final Set<State> startingStates,
                         final int maxPeriod,
                         final Predicate<GeneralPath> intermediatePredicate,
                         final Predicate<GeneralCircuit> resultPredicate,
                         final SiteswapConstructor<T> siteswapConstructor,
                         final SiteswapRequestBuilder siteswapRequestBuilder)
    {
        this.startingStates = startingStates != null ? startingStates.iterator() : emptyIterator();
        this.siteswapConstructor = Objects.requireNonNull(siteswapConstructor);
        this.maxPeriod = maxPeriod;
        this.intermediatePredicate = intermediatePredicate == null ? acceptAll() : intermediatePredicate;
        this.resultPredicate = resultPredicate == null ? acceptAll() : resultPredicate;
        this.siteswapRequestBuilder = siteswapRequestBuilder == null ? new SiteswapRequestBuilder() : siteswapRequestBuilder;
    }

    @Override
    public boolean hasNext()
    {
        try
        {
            this.next = get();
            return true;
        }
        catch (final NoMoreSiteswapsException noMoreSiteswapsException)
        {
            LOG.trace("No more elements");
            this.next = null;
            return false;
        }
    }

    @Override
    public T next()
    {
        if (next == null)
        {
            throw new NoSuchElementException("No call to next(), or call to next() after hasNext() returned false.");
        }
        return next;
    }

    public T get() throws NoMoreSiteswapsException
    {
        while (true)
        {
            LOG.trace("get(): Getting next element");
            do
            {
                getNextState();
            }
            while (!canBeReturned());

            try
            {
                return siteswapConstructor.apply(siteswapRequestBuilder.createSiteswapRequest(builder.toGeneralPath().toGeneralCircuit()));
            }
            catch (final Throwable ignored)
            {
                // Probably an illegal siteswap. Just return the next one we find.
                LOG.debug("SiteswapConstructor {} rejected. {}. See stack trace at trace level.", siteswapConstructor, ignored);
                LOG.trace("Stack Trace: ", ignored);
            }
        }
    }

    private void getNextState() throws NoMoreSiteswapsException
    {
        do
        {
            if (builder == null && startingStates.hasNext())
            {
                builder = new PathBuilder(startingStates.next(), maxPeriod);
                builder.buildStack();
            }
            else if (builder == null && !startingStates.hasNext())
            {
                throw new NoMoreSiteswapsException();
            }
            else if (builder.size() < maxPeriod)
            {
                builder.buildStack();
            }
            else
            {
                try
                {
                    builder.moveLastIterator();
                }
                catch (PathBuilder.StartingStateFinishedException e)
                {
                    builder = null;
                }
            }
        }
        while (!isLegalSequence());
    }

    private boolean canBeReturned()
    {
        final GeneralPath generalPath = builder.toGeneralPath();
        return generalPath.isGeneralCircuit() && resultPredicate.test(generalPath.toGeneralCircuit());
    }

    private boolean isLegalSequence()
    {
        return builder != null && intermediatePredicate.test(builder.toGeneralPath());
    }

    public Stream<T> generate()
    {
        final Spliterator<T> siteswapSpliterator = Spliterators.spliteratorUnknownSize(this,
            Spliterator.ORDERED | Spliterator.NONNULL);

        return StreamSupport.stream(siteswapSpliterator, false).unordered();
    }

    static <T> Predicate<T> acceptAll()
    {
        return (any) -> true;
    }

    private static class NoMoreSiteswapsException extends Exception
    {
    }

    private static class PathBuilder
    {
        private Vector<PathNode> path;

        private PathBuilder(final State startingState, final int maxLength)
        {
            path = new Vector<>(maxLength, 1);
            path.add(new PathNode(startingState));
        }

        private void buildStack()
        {
            final PathNode lastNode = path.lastElement();

            if (lastNode.hasNextThro())
            {
                path.add(new PathNode(lastNode.state.thro(lastNode.nextThro())));
            }
        }

        private void moveLastIterator() throws StartingStateFinishedException
        {
            if (path.isEmpty())
            {
                throw new StartingStateFinishedException();
            }
            if (path.lastElement().hasNextThro())
            {
                path.lastElement().nextThro();
            }
            else
            {
                path.remove(path.size() - 1);
                moveLastIterator();
            }
        }

        private int size()
        {
            return path.size();
        }

        private GeneralPath toGeneralPath()
        {
            final GeneralPath generalPath = new GeneralPath(path.firstElement().state);

            for (final PathNode pathNode : path)
            {
                if (pathNode.currentThro != null)
                {
                    generalPath.push(pathNode.currentThro);
                }
            }

            return generalPath;
        }

        private static class PathNode
        {
            private final State state;
            private final Iterator<Thro> throIterator;
            private Thro currentThro;

            public PathNode(final State state)
            {
                this.state = state;
                this.throIterator = state.getAvailableThrows().iterator();
            }

            public State getState()
            {
                return state;
            }

            public Thro getCurrentThro()
            {
                return currentThro;
            }

            public boolean hasNextThro()
            {
                return this.throIterator.hasNext();
            }

            public Thro nextThro()
            {
                this.currentThro = this.throIterator.next();
                return currentThro;
            }
        }

        private class StartingStateFinishedException extends Exception
        {
        }
    }
}
