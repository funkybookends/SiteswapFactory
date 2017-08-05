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
