package com.ignoretheextraclub.siteswapfactory.graph;

import java.util.Stack;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

public class GeneralPath extends Stack<Thro>
{
	private Stack<State> states = new Stack<>();

	public GeneralPath(final State startingState)
	{
		states.push(startingState);
	}

	public State getStartingState()
	{
		return states.get(0);
	}

	@Override
	public Thro push(final Thro item)
	{
		states.push(states.lastElement().thro(item));
		return super.push(item);
	}

	@Override
	public synchronized Thro pop()
	{
		states.pop();
		return super.pop();
	}

	public State getLastState()
	{
		return this.states.lastElement();
	}

	public GeneralCircuit toGeneralCircuit()
	{
		return new GeneralCircuit(states.get(0), this.toArray(new Thro[this.size()]));
	}

	public boolean isGeneralCircuit()
	{
		return getStartingState().equals(getLastState());
	}

	public State[] getStates()
	{
		return states.toArray(new State[states.size()]);
	}
}
