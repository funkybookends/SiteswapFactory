package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;

public interface SwapFactory
{
	SwapGraphic getSwap(Site site);
}
