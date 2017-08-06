package com.ignoretheextraclub.siteswapfactory.predicates;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 Created by caspar on 30/07/17.
 */
public interface SequencePredicate
{
    default boolean test(Siteswap siteswap)
    {
        if (!supportsTestingLoops())
        {
            throw new UnsupportedOperationException("This implementation does not support testing loops, such as siteswaps.");
        }
        return testLoop(siteswap.getStates());
    }

    boolean testLoop(State[] looping);

    boolean testSequence(State... sequence);

    boolean supportsTestingLoops();

    boolean supportsTestingSequences();

    default SequencePredicate and(final SequencePredicate other)
    {
        final SequencePredicate me = SequencePredicate.this;

        if (other == null)
        {
            return SequencePredicate.this;
        }

        return new SequencePredicate()
        {
            @Override
            public boolean testLoop(final State[] looping)
            {
                if (me.supportsTestingLoops() && other.supportsTestingLoops())
                {
                    return me.testLoop(looping) && other.testLoop(looping);
                }
                else if (me.supportsTestingLoops())
                {
                    return me.testLoop(looping);
                }
                else if (other.supportsTestingLoops())
                {
                    return other.testLoop(looping);
                }
                else
                {
                    throw new IllegalAccessError("(AND) Neither predicate supports testing loops, but was asked to test loop");
                }
            }

            @Override
            public boolean testSequence(final State[] sequence)
            {
                if (me.supportsTestingSequences() && other.supportsTestingSequences())
                {
                    return me.testSequence(sequence) && other.testSequence(sequence);
                }
                else if (me.supportsTestingSequences())
                {
                    return me.testSequence(sequence);
                }
                else if (other.supportsTestingSequences())
                {
                    return other.testSequence(sequence);
                }
                else
                {
                    throw new IllegalAccessError("(AND) Neither predicate supports testing sequences, but was asked to test sequence");
                }
            }

            @Override
            public boolean supportsTestingLoops()
            {
                return me.supportsTestingLoops() || other.supportsTestingLoops();
            }

            @Override
            public boolean supportsTestingSequences()
            {
                return me.supportsTestingSequences() || other.supportsTestingSequences();
            }
        };
    }

    default SequencePredicate or(final SequencePredicate other)
    {
        final SequencePredicate me = SequencePredicate.this;

        if (other == null)
        {
            return me;
        }

        return new SequencePredicate()
        {
            @Override
            public boolean testLoop(final State[] looping)
            {
                if (me.supportsTestingLoops() && other.supportsTestingLoops())
                {
                    return me.testLoop(looping) || other.testLoop(looping);
                }
                else if (me.supportsTestingLoops())
                {
                    return me.testLoop(looping);
                }
                else if (other.supportsTestingLoops())
                {
                    return other.testLoop(looping);
                }
                else
                {
                    throw new IllegalAccessError("(OR) Neither predicate supports testing loops, but was asked to test loop");
                }
            }

            @Override
            public boolean testSequence(final State[] sequence)
            {
                if (me.supportsTestingSequences() && other.supportsTestingSequences())
                {
                    return me.testSequence(sequence) || other.testSequence(sequence);
                }
                else if (me.supportsTestingSequences())
                {
                    return me.testSequence(sequence);
                }
                else if (other.supportsTestingSequences())
                {
                    return other.testSequence(sequence);
                }
                else
                {
                    throw new IllegalAccessError("(OR) Neither predicate supports testing sequences, but was asked to test sequence");
                }
            }

            @Override
            public boolean supportsTestingLoops()
            {
                return me.supportsTestingLoops() || other.supportsTestingLoops();
            }

            @Override
            public boolean supportsTestingSequences()
            {
                return me.supportsTestingSequences() || other.supportsTestingSequences();
            }
        };
    }

    default SequencePredicate negate()
    {
        return new SequencePredicate()
        {
            @Override
            public boolean testLoop(final State[] looping)
            {
                return !SequencePredicate.this.testLoop(looping);
            }

            @Override
            public boolean testSequence(final State[] sequence)
            {
                return !SequencePredicate.this.testSequence(sequence);
            }

            @Override
            public boolean supportsTestingLoops()
            {
                return SequencePredicate.this.supportsTestingLoops();
            }

            @Override
            public boolean supportsTestingSequences()
            {
                return SequencePredicate.this.supportsTestingSequences();
            }
        };
    }
}
