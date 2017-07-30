package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 Created by caspar on 25/06/17.
 */
@RunWith(Parameterized.class)
public class TwoHandedSiteswapTest
{
    public static final NoSortingStrategy NO_SORTING_STRATEGY = new NoSortingStrategy<>();
    private static final String VALID_TWO_HANDED_SITESWAPS_LIST = "validDups.list";
    private final String stringSiteswap;

    public TwoHandedSiteswapTest(final String stringSiteswap)
    {
        this.stringSiteswap = stringSiteswap;
        System.out.println(stringSiteswap);
    }

    @Parameterized.Parameters
    public static Collection getValidTwoHandedSiteswaps() throws FileNotFoundException
    {
        final ClassLoader classLoader = TwoHandedVanillaSiteswap.class.getClassLoader();
        final File file = new File(classLoader.getResource(VALID_TWO_HANDED_SITESWAPS_LIST).getFile());
        final List<String> siteswaps = new ArrayList<>();

        try (final Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine())
            {
                siteswaps.add(scanner.nextLine());
            }
        }

        return siteswaps;
    }

    @Test
    public void testCreateWithNoSortingStrategy() throws Exception
    {
        SiteswapFactory.createTHS(stringSiteswap, NO_SORTING_STRATEGY, true);
    }

    @Test
    public void testCreateWithHighestThrowFirstStrategy() throws Exception
    {
        SiteswapFactory.createTHS(stringSiteswap, HighestThrowFirstStrategy.get(), true);
    }
}