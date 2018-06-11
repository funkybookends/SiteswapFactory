/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
