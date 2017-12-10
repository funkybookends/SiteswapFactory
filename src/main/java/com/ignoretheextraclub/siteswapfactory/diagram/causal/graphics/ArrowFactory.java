package com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;

public interface ArrowFactory
{
	ArrowGraphic getArrow(final Site originSite, SwapGraphic originGraphic, final Site causesSite, SwapGraphic causesGraphic);
}
