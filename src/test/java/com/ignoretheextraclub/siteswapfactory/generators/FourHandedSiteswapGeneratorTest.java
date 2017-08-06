package com.ignoretheextraclub.siteswapfactory.generators;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 Created by caspar on 30/07/17.
 */
@RunWith(Parameterized.class)
public class FourHandedSiteswapGeneratorTest
{
    private static final Logger LOG = LoggerFactory.getLogger(FourHandedSiteswapGeneratorTest.class);

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private final int numObjects;
    private final int maxPeriod;
    private final long groundSiteswaps;
    private final long allSiteswaps;

    public FourHandedSiteswapGeneratorTest(final int numObjects,
                                           final int maxPeriod,
                                           final long groundSiteswaps,
                                           final long allSiteswaps)
    {
        this.numObjects = numObjects;
        this.maxPeriod = maxPeriod;
        this.groundSiteswaps = groundSiteswaps;
        this.allSiteswaps = allSiteswaps;
    }

    @Parameterized.Parameters(name = "{0} objects ({1} max throws) : expect {2} ground siteswaps, {3} all siteswaps")
    public static Collection<Object[]> data()
    {
        final ArrayList<Object[]> tests = new ArrayList<>();

        tests.add(new Object[]{3, 5, 8, 10});
        tests.add(new Object[]{4, 7, 195, 255});
//        tests.add(new Object[]{5, 9, 694, 10271});
        tests.add(new Object[]{7, 9, 1937, 2045});
//        tests.add(new Object[]{6, 10, 56138, 82853});

        return tests;
    }

    @Test
    public void testGround() throws Exception
    {
        final SiteswapGenerator generator = FourHandedSiteswapGenerator.ground(numObjects, maxPeriod);
        softly.assertThat(generator.generateDistinct()
//                                   .peek(siteswap -> LOG.info("{}", siteswap))
                                   .count()).isEqualTo(groundSiteswaps);
    }

    @Test
    public void testAll() throws Exception
    {
        final SiteswapGenerator generator = FourHandedSiteswapGenerator.all(numObjects, maxPeriod);
        softly.assertThat(generator.generateDistinct()
//                                   .peek(siteswap -> LOG.info("{}", siteswap))
                                   .count()).isEqualTo(allSiteswaps);
    }
}