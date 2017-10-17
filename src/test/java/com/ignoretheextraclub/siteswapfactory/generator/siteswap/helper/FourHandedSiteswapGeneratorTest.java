package com.ignoretheextraclub.siteswapfactory.generator.siteswap.helper;

import java.util.function.Predicate;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.generator.siteswap.SiteswapGenerator;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by caspar on 09/10/17.
 */
public class FourHandedSiteswapGeneratorTest
{
    private static final Logger LOG = LoggerFactory.getLogger(FourHandedSiteswapGeneratorTest.class);

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testGround() throws Exception
    {
        final int numObjects = 5;
        final int maxPeriod = 3;
        final int maxThro = 9;
        final SiteswapGenerator<FourHandedSiteswap> ground = FourHandedSiteswapGenerator.ground(numObjects, maxThro, maxPeriod);

        Predicate<FourHandedSiteswap> test = numObjectsEquals(numObjects)
            .and(periodLessThan(maxPeriod))
            .and(classIsExactly())
            .and(isGrounded())
            .and(fourHandedSiteswap -> fourHandedSiteswap.getHighestThro().getNumBeats() < 9);

        assertThat(ground.generate().allMatch(test)).isTrue();
    }

    @Test
    public void testExcited() throws Exception
    {
        final int numObjects = 5;
        final int maxPeriod = 3;
        final int maxThro = 9;
        final SiteswapGenerator<FourHandedSiteswap> excited = FourHandedSiteswapGenerator.excited(numObjects, maxThro, maxPeriod);

        Predicate<FourHandedSiteswap> test = numObjectsEquals(numObjects)
            .and(periodLessThan(maxPeriod))
            .and(classIsExactly())
            .and(isNotGrounded())
            .and(highestThroLessThanOrEqualTo(maxThro));

        assertThat(excited.generate().allMatch(test)).isTrue();
    }

    @Test
    public void testAll() throws Exception
    {
        final int numObjects = 5;
        final int maxPeriod = 3;
        final int maxThro = 9;
        final SiteswapGenerator<FourHandedSiteswap> all = FourHandedSiteswapGenerator.all(numObjects, maxThro, maxPeriod);

        Predicate<FourHandedSiteswap> test = numObjectsEquals(numObjects)
            .and(periodLessThan(maxPeriod))
            .and(classIsExactly())
            .and(highestThroLessThanOrEqualTo(maxThro));

        assertThat(all.generate().allMatch(test)).isTrue();
    }

    @Test
    public void WHEN_maxThroLessThanNumObjects_EXPECT_exception() throws Exception
    {
        softly.assertThatThrownBy(() -> FourHandedSiteswapGenerator.all(5, 4, 8))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> FourHandedSiteswapGenerator.ground(5, 4, 8))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> FourHandedSiteswapGenerator.excited(5, 4, 8))
            .isInstanceOf(IllegalArgumentException.class);
    }

    // TODO add more

    private Predicate<FourHandedSiteswap> isNotGrounded()
    {
        return fourHandedSiteswap ->
        {
            if (!fourHandedSiteswap.isGrounded())
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed isNotGrounded", fourHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<FourHandedSiteswap> periodLessThan(final int maxPeriod)
    {
        return fourHandedSiteswap ->
        {
            if (fourHandedSiteswap.getPeriod() <= maxPeriod)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed periodLessThan", fourHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<FourHandedSiteswap> isGrounded()
    {
        return (fourHandedSiteswap) ->
        {
            if (fourHandedSiteswap.isGrounded())
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed isGrounded", fourHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<FourHandedSiteswap> highestThroLessThanOrEqualTo(final int maxThro)
    {
        return fourHandedSiteswap ->
        {
            if (fourHandedSiteswap.getHighestThro().getNumBeats() <= maxThro)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed highestThroLessThanOrEqualTo", fourHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<FourHandedSiteswap> classIsExactly()
    {
        return fourHandedSiteswap ->
        {
            if (fourHandedSiteswap.getClass() == FourHandedSiteswap.class)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed classIsExactly", fourHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<FourHandedSiteswap> numObjectsEquals(final int numObjects)
    {
        return fourHandedSiteswap ->
        {
            if (fourHandedSiteswap.getNumObjects() == numObjects)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed numObjectsEquals", fourHandedSiteswap);
                return false;
            }
        };
    }
}