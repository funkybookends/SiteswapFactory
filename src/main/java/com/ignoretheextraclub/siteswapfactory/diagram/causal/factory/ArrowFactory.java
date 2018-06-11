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
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.ArrowGraphic;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic;

/**
 * A factory for creating arrow graphics
 */
public interface ArrowFactory
{
	/**
	 * Returns an arrow graphic that connects the origin graphic to the causes graphic. Is provided with the origin site
	 * and causes site to ensure all information is available.
	 *
	 * @param originSite    The Causal definition of the origin
	 * @param originGraphic The graphic representation of the origin site
	 * @param causesSite    The Causal definition of the causes
	 * @param causesGraphic The graphic representation of the causes site
	 * @return The arrow connecting them, never null. If something should not be drawn, the returned graphic should act appropriately when {@link ArrowGraphic#draw(Graphics2D)} is called.
	 */
	ArrowGraphic getArrow(Site originSite, SwapGraphic originGraphic, Site causesSite, SwapGraphic causesGraphic);
}
