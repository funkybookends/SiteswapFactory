package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Created by caspar on 30/07/17.
 */
public class FourHandedSiteswapGeneratorTest
{
    private static final Logger LOG = LoggerFactory.getLogger(FourHandedSiteswapGeneratorTest.class);

    @Test
    public void name() throws Exception
    {
        final TwoHandedSiteswapGenerator fourHandedSiteswapGenerator = new TwoHandedSiteswapGenerator(13);
        fourHandedSiteswapGenerator.addStartingState(VanillaStateUtils.getGroundState(5, 3));
        System.out.println(fourHandedSiteswapGenerator.generate()
                                                      .distinct()
//                                                      .limit(1000)
                                                      .peek(siteswap -> LOG.info("{}\t {}",
                                                              siteswap.toString(),
                                                              siteswap.getStates()))
                                                      .count());
    }
}