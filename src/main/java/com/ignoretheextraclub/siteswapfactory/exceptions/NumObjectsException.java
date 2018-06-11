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

package com.ignoretheextraclub.siteswapfactory.exceptions;

/**
 * Exception indicating an issue with the number of objects in a
 * {@link com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap} or between two
 * {@link com.ignoretheextraclub.siteswapfactory.siteswap.State}s or in the construction of a
 * {@link com.ignoretheextraclub.siteswapfactory.siteswap.State}.
 */
public class NumObjectsException extends InvalidSiteswapException
{
    public NumObjectsException(String message)
    {
        super(message);
    }
}
