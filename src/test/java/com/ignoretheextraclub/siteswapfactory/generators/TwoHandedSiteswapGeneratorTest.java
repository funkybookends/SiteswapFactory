package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 Created by caspar on 30/07/17.
 */
@RunWith(Parameterized.class)
public class TwoHandedSiteswapGeneratorTest
{
    private static final Logger LOG = LoggerFactory.getLogger(TwoHandedSiteswapGeneratorTest.class);

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private final int numObjects;
    private final int maxPeriod;
    private final long groundSiteswaps;
    private final long allSiteswaps;
    private final long excited;

    public TwoHandedSiteswapGeneratorTest(final int numObjects,
                                          final int maxPeriod,
                                          final long groundSiteswaps,
                                          final long allSiteswaps,
                                          final long excited)
    {
        this.numObjects = numObjects;
        this.maxPeriod = maxPeriod;
        this.groundSiteswaps = groundSiteswaps;
        this.allSiteswaps = allSiteswaps;
        this.excited = excited;
    }

    @Parameterized.Parameters(name = "{0} objects ({1} max throws) : expect: {2} ground, {3} all, {4} excited")
    public static Collection<Object[]> data()
    {
        final ArrayList<Object[]> tests = new ArrayList<>();

        tests.add(new Object[]{3, 5, 25+13+5+2+1-5, 27+14+6+3+1 -6, 4}); // Minus the 6 that are not reduced
        tests.add(new Object[]{4, 7, 893, 1112, 1112-893});
//        tests.add(new Object[]{5, 9, 694, 10271});
        tests.add(new Object[]{7, 9, 2797, 3034, 3034-2797});
//        tests.add(new Object[]{6, 10, 56138, 82853});

        return tests;
    }

    @Test
    public void testGround() throws Exception
    {
        final SiteswapGenerator generator = TwoHandedSiteswapGenerator.ground(numObjects, maxPeriod);
        softly.assertThat(generator.generateDistinct()
//                                   .peek(siteswap -> LOG.info("{}", siteswap))
                                   .count()).isEqualTo(groundSiteswaps);
    }

    @Test
    public void testAll() throws Exception
    {
        final SiteswapGenerator generator = TwoHandedSiteswapGenerator.all(numObjects, maxPeriod);
        softly.assertThat(generator.generateDistinct()
//                                   .peek(siteswap -> LOG.info("{}", siteswap))
                                   .count()).isEqualTo(allSiteswaps);

        generator.generateDistinct().sorted(Comparator.comparingInt(Siteswap::getPeriod))
                 .peek(siteswap -> LOG.info("{}", siteswap)).count();
    }

    @Test
    public void testExcited() throws Exception
    {
        final SiteswapGenerator generator = TwoHandedSiteswapGenerator.excited(numObjects, maxPeriod);
        softly.assertThat(generator.generateDistinct()
//                                   .peek(siteswap -> LOG.info("{}", siteswap))
                                   .count()).isEqualTo(excited);

        generator.generateDistinct().sorted(Comparator.comparingInt(Siteswap::getPeriod))
        .peek(siteswap -> LOG.info("{}", siteswap)).count();
    }

    @Test
    @Ignore("caused by fhs sorting strategy")
    public void testSorterProducesSameAmount() throws Exception
    {
        final SiteswapGenerator hfsSorted = TwoHandedSiteswapGenerator.all(numObjects, maxPeriod,
                SiteswapFactoryConfiguration.DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY);
        softly.assertThat(hfsSorted.generateDistinct()
//                                   .peek(siteswap -> LOG.info("{}", siteswap))
                                   .count()).isEqualTo(allSiteswaps);

        final SiteswapGenerator passingSorted = TwoHandedSiteswapGenerator.all(numObjects, maxPeriod,
                SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY);
        softly.assertThat(passingSorted.generateDistinct()
                                   .peek(siteswap -> LOG.info("{}", siteswap))
                                   .count()).isEqualTo(allSiteswaps);
    }
}