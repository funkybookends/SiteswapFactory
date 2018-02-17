package com.ignoretheextraclub.siteswapfactory.generator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

public class RouteIterator implements Iterator<GeneralPath>
{
	private static final Logger LOG = LoggerFactory.getLogger(RouteIterator.class);

	private final Predicate<GeneralPath> predicate;
	private final int maxDepth;
	private final State startingState;

	private PathNode firstNode;
	private int currentTargetDepth;
	private boolean anyFoundForCurrentDepth;
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

		anyFoundForCurrentDepth = true;

		return next;
	}

	private Optional<GeneralPath> getNext()
	{
		try
		{
			while (mustMove || !isAcceptablePath() || !isTargetDepth())
			{
				if (mustMove)
				{
					mustMove = false;
					safeMoveIterator();
				}
				else if (!isAcceptablePath())
				{
					safeMoveIterator();
				}
				else
				{
					firstNode.increaseDepth();
				}
			}

			return Optional.of(toGeneralPath());
		}
		catch (final NoElementsFoundForCurrentDepthException | MaxDepthReachedException finished)
		{
			return Optional.empty();
		}
	}

	private void safeMoveIterator() throws NoElementsFoundForCurrentDepthException, MaxDepthReachedException
	{
		if (firstNode.hasNextThro())
		{
			firstNode.moveLastIterator();
		}
		else
		{
			if (!anyFoundForCurrentDepth)
			{
				throw new NoElementsFoundForCurrentDepthException();
			}
			if (currentTargetDepth == maxDepth)
			{
				throw new MaxDepthReachedException();
			}
			anyFoundForCurrentDepth = false;
			++currentTargetDepth;
			firstNode = new PathNode(startingState);
		}
	}

	private boolean isTargetDepth()
	{
		return getDepth() == currentTargetDepth;
	}

	private boolean isAcceptablePath()
	{
		return predicate.test(toGeneralPath());
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

	private int getDepth()
	{
		return firstNode.getDepth();
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
			throwNextThrow();
		}

		public boolean hasNextThro()
		{
			return this.throIterator.hasNext();
		}

		public void increaseDepth()
		{
			if (nextNode != null)
			{
				this.nextNode.increaseDepth();
			}
			else
			{
				this.nextNode = new PathNode(this.state.thro(this.currentThro));
			}
		}

		private void throwNextThrow()
		{
			this.currentThro = throIterator.next();
			this.nextNode = null;
		}

		private boolean canMoveLastIterator()
		{
			if (nextNode != null)
			{
				return this.nextNode.canMoveLastIterator() || this.hasNextThro();
			}
			return this.hasNextThro();
		}

		public void moveLastIterator()
		{
			if (nextNode != null && this.nextNode.canMoveLastIterator())
			{
				this.nextNode.moveLastIterator();
			}
			else
			{
				this.throwNextThrow();
			}
		}

		public int getDepth()
		{
			if (currentThro == null)
			{
				return 0;
			}
			if (nextNode == null)
			{
				return 1;
			}
			else
			{
				return 1 + nextNode.getDepth();
			}
		}
	}

	private class NoElementsFoundForCurrentDepthException extends Exception
	{
	}

	private class MaxDepthReachedException extends Exception
	{
	}

	@Override
	public String toString()
	{
		return "RouteIterator{" + toGeneralPath() + "}";
	}
}
