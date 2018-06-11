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

package com.ignoretheextraclub.siteswapfactory.diagram.causal;

import java.util.Set;

/**
 * A object that provides information on a causal diagram.
 */
public interface CausalDiagram
{
	/**
	 * Get all the sites of this causal diagram.
	 * @return All the sites.
	 */
	Set<Site> getSites();

	/**
	 * Get the beat at which this Causal Diagram repeats itself.
	 * @return
	 */
	double getFullRotationBeat();

	/**
	 * Get the total number of jugglers in this causal diagram.
	 * @return The number of jugglers.
	 */
	default int getNumJugglers()
	{
		return getSites().stream().mapToInt(Site::getJuggler).max().orElse(0);
	}

	/**
	 * Get the beat of the last site in this causal diagram.
	 * @return The max causal beat.
	 */
	default double getMaxCausalBeat()
	{
		return getSites().stream().mapToDouble(Site::getCausalBeat).max().orElse(0);
	}
}
