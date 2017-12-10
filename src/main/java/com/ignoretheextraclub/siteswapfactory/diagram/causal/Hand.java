package com.ignoretheextraclub.siteswapfactory.diagram.causal;

public enum Hand
{
	RIGHT,
	LEFT;

	public Hand other(final Hand hand)
	{
		return hand == RIGHT ? LEFT : RIGHT;
	}
}
