//package com.ignoretheextraclub.siteswapfactory.generators;
//
//import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
//import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.AbstractState;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.AbstractThro;
//import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
//import com.ignoretheextraclub.siteswapfactory.sorters.utils.SortingUtils;
//
//import java.lang.reflect.Array;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Queue;
//import java.util.Set;
//import java.util.Stack;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.function.Consumer;
//import java.util.function.Predicate;
//import java.util.function.Supplier;
//import java.util.stream.Stream;
//
///**
// Created by caspar on 11/12/16.
// */
//public class StateSearcher<State,Thro> implements Supplier<Siteswap<State, Thro>>
//{
//    public StateSearcher(final Set<State[]> startingStates,
//                         final Set<Predicate<State> statePredicates>,
//                         final Set<Thro> throPredicates,
//                         final Set<Predicate<Transition>)
//    {
//
//    }
//
//    @Override
//    public Siteswap<State, Thro> get()
//    {
//        while (!startingStates.isEmpty())
//        {
//            final State startingState = startingStates.poll();
//            if (!statesStartedFrom.contains(startingState))
//            {
//                Stack<State> path = new Stack<>();
//                path.push(startingState);
//                statesStartedFrom.add(startingState);
//                try
//                {
//                    generate(path);
//                }
//                catch (final ResultLimitReached | TimeLimitReached stopper)
//                {
//                    break;
//                }
//                catch (final InterruptedException e)
//                {
//                    break;
//                }
//            }
//        }
//        completed = true;
//    }
//}
