package com.ignoretheextraclub.siteswapfactory.diagram.causal.factory;

import java.awt.*;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Site;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;

/**
 * A factory for swap graphics
 */
public interface SwapFactory
{
	/**
	 * Returns a swap graphic for the given site
	 *
	 * @param site The site to get the swap graphic for.
	 * @return The swap graphic, never null. If it should not be displayed then the swap graphic should act appropriatly when {@link SwapGraphic#draw(Graphics2D)} is called.
	 */
	SwapGraphic getSwap(Site site);
}
