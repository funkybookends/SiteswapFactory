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

package com.ignoretheextraclub.siteswapfactory.testutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 Created by caspar on 06/08/17.
 */
public final class Utils
{
    private Utils(){}

    public static List<String> load(final String fileName) throws FileNotFoundException
    {
        final ClassLoader classLoader = Utils.class.getClassLoader();
        final File file = new File(classLoader.getResource(fileName).getFile());
        final List<String> lines = new ArrayList<>();

        try (final Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine())
            {
                lines.add(scanner.nextLine());
            }
        }
        return lines;
    }
}
