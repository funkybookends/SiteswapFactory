package com.ignoretheextraclub.siteswapfactory.generator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

public class RouteIterator implements Iterator<GeneralPath>
{
	/**
	 * A predicate that will prevent the returning of paths and all sub paths.
	 */
	private final Predicate<GeneralPath> predicate;

	/**
	 * A maximum depth to ensure termination
	 */
	private final int maxDepth;

	/**
	 * The starting state for all paths returned.
	 */
	private final State startingState;

	private PathNode firstNode;
	private int currentTargetDepth;
	private GeneralPath next;
	private boolean mustMove;

	public RouteIterator(final State startingState,
	                     final Predicate<GeneralPath> predicate,
	                     final int maxDepth,
	                     final int startingDepth)
	{
		this.startingState = startingState;
		this.predicate = predicate;
		this.maxDepth = maxDepth;
		this.currentTargetDepth = startingDepth;

		if (maxDepth < 1)
		{
			throw new IllegalArgumentException("max depth cannot be less than one");
		}

		if (startingDepth > maxDepth)
		{
			throw new IllegalArgumentException("starting depth cannot be greater than max depth");
		}

		if (startingDepth < 1)
		{
			throw new IllegalArgumentException("starting depth cannot be less than 1");
		}

		firstNode = new PathNode(startingState);

		next = getNext().orElse(null);
	}

	@Override
	public boolean hasNext()
	{
		return next != null;
	}

	@Override
	public GeneralPath next()
	{
		if (this.next == null)
		{
			throw new NoSuchElementException();
		}

		final GeneralPath next = this.next;

		this.mustMove = true;
		this.next = getNext().orElse(null);

		return next;
	}

	private Optional<GeneralPath> getNext()
	{
		boolean isNotAcceptablePath = isNotAcceptablePath();
		boolean isNotTargetDepth = isNotTargetDepth();

		while (mustMove || isNotAcceptablePath || isNotTargetDepth)
		{
			if (mustMove || isNotAcceptablePath())
			{
				mustMove = false;
				if (!safeMoveIterator())
				{
					return Optional.empty();
				}
			}
			else
			{
				firstNode.increaseDepth();
			}

			isNotAcceptablePath = isNotAcceptablePath();
			isNotTargetDepth = isNotTargetDepth();
		}

		return Optional.of(toGeneralPath());
	}

	/**
	 * Moves the last iterator and returns if the search can continue.
	 */
	private boolean safeMoveIterator()
	{
		if (!firstNode.moveLastIterator())
		{
			if (currentTargetDepth == maxDepth)
			{
				return false;
			}
			++currentTargetDepth;
			firstNode = new PathNode(startingState);
		}
		return true;
	}

	private boolean isNotTargetDepth()
	{
		return firstNode.size() != currentTargetDepth;
	}

	private boolean isNotAcceptablePath()
	{
		return !predicate.test(toGeneralPath());
	}

	private GeneralPath toGeneralPath()
	{
		final GeneralPath generalPath = new GeneralPath(firstNode.state);

		PathNode node = firstNode;
		generalPath.push(node.currentThro);

		while (node.nextNode != null)
		{
			node = node.nextNode;
			generalPath.push(node.currentThro);
		}

		return generalPath;
	}

	private static class PathNode
	{
		private final State state;
		private final Iterator<Thro> throIterator;

		private Thro currentThro;
		private PathNode nextNode;

		public PathNode(final State state)
		{
			this.state = state;
			this.throIterator = state.getAvailableThrows().iterator();
			moveIterator();
		}

		public void increaseDepth()
		{
			if (this.nextNode != null)
			{
				this.nextNode.increaseDepth();
			}
			else
			{
				this.nextNode = new PathNode(this.state.thro(this.currentThro));
			}
		}

		private void moveIterator()
		{
			this.currentThro = this.throIterator.next();
			this.nextNode = null;
		}

		/**
		 * Attempts to move the last iterator. Returning true if it was moved,
		 * and false otherwise.
		 * <p>
		 * If this node has a {@link #nextNode} then that node will be asked to
		 * move the last iterator, and if successful will return true. Otherwise
		 * it will check to see if it can move its iterator, and will return true
		 * if it can, otherwise it will return false.
		 *
		 * @return true if a deeper node moved the last iterator or it moved the
		 * last iterator, otherwise false.
		 */
		public boolean moveLastIterator()
		{
			if (this.nextNode != null && this.nextNode.moveLastIterator())
			{
				return true;
			}

			if (this.throIterator.hasNext())
			{
				this.moveIterator();
				return true;
			}

			return false;
		}

		public int size()
		{
			if (this.nextNode == null)
			{
				return 1;
			}
			else
			{
				return 1 + this.nextNode.size();
			}
		}
	}

	@Override
	public String toString()
	{
		return "RouteIterator{" + toGeneralPath() + "}";
	}
}
