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

import java.util.Set;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.RotationMarkerGraphic;

/**
 * A factory for rotation markers
 */
public interface RotationMarkerFactory
{
	/**
	 * Returns all the rotation markers for the causal diagram.
	 *
	 * @param causalDiagram The causal diagram.
	 * @return The set of rotation markers
	 * @see CausalDiagram#getFullRotationBeat()
	 */
	Set<RotationMarkerGraphic> getRotationMarkers(CausalDiagram causalDiagram);
}
