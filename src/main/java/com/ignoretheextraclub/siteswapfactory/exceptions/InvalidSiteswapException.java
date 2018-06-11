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
 * Indicates that a provided {@link com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap} did not conform to the
 * expectations of the interface, or that the provided constructor parameter could not be converted into a
 * {@link com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap}
 * @author Caspar Nonclercq
 */
public class InvalidSiteswapException extends RuntimeException
{
    public InvalidSiteswapException(String message)
    {
        super(message);
    }

    public InvalidSiteswapException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidSiteswapException(final InvalidSiteswapException cause)
    {
        super(cause);
    }
}
