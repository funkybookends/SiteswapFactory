package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import com.ignoretheextraclub.siteswapfactory.siteswap.sync.thros.MultiHandThro;

public class TwoPersonSyncPassingThro extends MultiHandThro
{
	public TwoPersonSyncPassingThro(final HandSpecificThro[] thros)
	{
		super(thros);
		if (thros.length != 2)
		{
			throw new IllegalArgumentException("Must only have two thros");
		}
	}

	@Override
	public String toString()
	{
		return String.format("<%s%s|%s%s>", thros[0].getNumBeats(), (thros[0].getToHand() == 0 ? "" : "p"),  thros[1].getNumBeats(), (thros[1].getToHand() == 1 ? "" : "p"));
	}

}
