package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.sorters.impl.NoSortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.thros.VanillaThrow;
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
 * Created by caspar on 25/06/17.
 */
@RunWith(Parameterized.class)
public class TwoHandedSiteswapTest
{
    private static final String VALID_TWO_HANDED_SITESWAPS_LIST = "validDups.list";
    public static final NoSortingStrategy<VanillaThrow, VanillaState<VanillaThrow>> NO_SORTING_STRATEGY = new NoSortingStrategy<>();

    @Parameterized.Parameters
    public static Collection getValidTwoHandedSiteswaps() throws FileNotFoundException
    {
        final ClassLoader classLoader = TwoHandedSiteswap.class.getClassLoader();
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

    private final String stringSiteswap;

    public TwoHandedSiteswapTest(final String stringSiteswap)
    {
        this.stringSiteswap = stringSiteswap;
        System.out.println(stringSiteswap);
    }

    @Test
    public void testCreateWithNoSortingStrategy() throws Exception
    {
        TwoHandedSiteswap.create(stringSiteswap, NO_SORTING_STRATEGY);
    }

    @Test
    public void testCreateWithHighestThrowFirstStrategy() throws Exception
    {
        TwoHandedSiteswap.create(stringSiteswap, HighestThrowFirstStrategy.get());
    }
}