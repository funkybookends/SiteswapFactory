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
import java.util.List;
import java.util.stream.Collectors;

/**
 Created by caspar on 30/07/17.
 */
@RunWith(Parameterized.class)
public class FourHandedSiteswapGeneratorTest
{
    private static final Logger LOG = LoggerFactory.getLogger(FourHandedSiteswapGeneratorTest.class);

    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private final int numObjects;
    private final int maxPeriod;
    private final long groundSiteswaps;
    private final long allSiteswaps;
    private final long excited;

    public FourHandedSiteswapGeneratorTest(final int numObjects,
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

        tests.add(new Object[]{3, 5, 7, 9, 2});
        tests.add(new Object[]{4, 7, 142, 196, 54});
        //        tests.add(new Object[]{5, 9, 694, 10271});
        tests.add(new Object[]{7, 9, 1749, 1857, 2045 - 1937});
        //        tests.add(new Object[]{6, 10, 56138, 82853});

        return tests;
    }

    @Test
    public void testGround() throws Exception
    {
        final SiteswapGenerator generator = FourHandedSiteswapGenerator.ground(numObjects, maxPeriod);
        softly.assertThat(generator.generateDistinct()
//                                   .peek(siteswap -> LOG.info("{}", siteswap))
                                   .count())
              .isEqualTo(groundSiteswaps);
    }

    @Test
    public void testAll() throws Exception
    {
        final SiteswapGenerator generator = FourHandedSiteswapGenerator.all(numObjects, maxPeriod);
        softly.assertThat(generator.generateDistinct()
//                                   .peek(siteswap -> LOG.info("{}", siteswap))
                                   .count())
              .isEqualTo(allSiteswaps);
    }

    @Test
    public void testExcited() throws Exception
    {
        final SiteswapGenerator generator = FourHandedSiteswapGenerator.excited(numObjects, maxPeriod);
        softly.assertThat(generator.generateDistinct()
//                                   .peek(siteswap -> LOG.info("{}", siteswap))
                                   .count())
              .isEqualTo(excited);
    }

    @Test
    @Ignore("caused by fhs sorting strategy")
    public void testSorterProducesSameAmount() throws Exception
    {
        final SiteswapGenerator hfsSorted = FourHandedSiteswapGenerator.all(numObjects,
                maxPeriod,
                SiteswapFactoryConfiguration.DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY);

        final SiteswapGenerator passingSorted = FourHandedSiteswapGenerator.all(numObjects,
                maxPeriod,
                SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY);

        LOG.info("allHfsSorted");
        final List<Siteswap> allHfsSorted = hfsSorted.generateDistinct()
                                                     //                                                     .peek(s -> LOG.info("{}", s))
                                                     .collect(Collectors.toList());
        LOG.info("passing");
        final List<Siteswap> allPassingSorted = passingSorted.generateDistinct()
                                                             //                                                             .peek(s -> LOG.info("{}" , s))
                                                             .collect(Collectors.toList());

        softly.assertThat(allHfsSorted.size()).isEqualTo(allPassingSorted.size());
    }
}