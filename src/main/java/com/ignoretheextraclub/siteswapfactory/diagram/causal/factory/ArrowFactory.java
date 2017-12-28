package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.ArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;

public interface ArrowFactory
{
	ArrowGraphic getArrow(final Site originSite, SwapGraphic originGraphic, final Site causesSite, SwapGraphic causesGraphic);
}
