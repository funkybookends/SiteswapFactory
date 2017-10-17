package com.ignoretheextraclub.siteswapfactory.generator.siteswap.premade;

import java.util.function.Predicate;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.generator.siteswap.SiteswapGenerator;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by caspar on 09/10/17.
 */
public class TwoHandedSiteswapGeneratorTest
{ 
    private static final Logger LOG = LoggerFactory.getLogger(TwoHandedSiteswapGeneratorTest.class);

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testGround() throws Exception
    {
        final int numObjects = 5;
        final int maxPeriod = 3;
        final int maxThro = 9;
        final SiteswapGenerator<TwoHandedSiteswap> ground = TwoHandedSiteswapGenerator.ground(numObjects, maxThro, maxPeriod);

        Predicate<TwoHandedSiteswap> test = numObjectsEquals(numObjects)
            .and(periodLessThan(maxPeriod))
            .and(classIsExactly())
            .and(isGrounded())
            .and(twoHandedSiteswap -> twoHandedSiteswap.getHighestThro().getNumBeats() < 9);

        assertThat(ground.generate().allMatch(test)).isTrue();
    }

    @Test
    public void testExcited() throws Exception
    {
        final int numObjects = 5;
        final int maxPeriod = 3;
        final int maxThro = 9;
        final SiteswapGenerator<TwoHandedSiteswap> excited = TwoHandedSiteswapGenerator.excited(numObjects, maxThro, maxPeriod);

        Predicate<TwoHandedSiteswap> test = numObjectsEquals(numObjects)
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
        final SiteswapGenerator<TwoHandedSiteswap> all = TwoHandedSiteswapGenerator.all(numObjects, maxThro, maxPeriod);

        Predicate<TwoHandedSiteswap> test = numObjectsEquals(numObjects)
            .and(periodLessThan(maxPeriod))
            .and(classIsExactly())
            .and(highestThroLessThanOrEqualTo(maxThro));

        assertThat(all.generate().allMatch(test)).isTrue();
    }

    @Test
    public void WHEN_maxThroLessThanNumObjects_EXPECT_exception() throws Exception
    {
        softly.assertThatThrownBy(() -> TwoHandedSiteswapGenerator.all(5, 4, 8))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> TwoHandedSiteswapGenerator.ground(5, 4, 8))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> TwoHandedSiteswapGenerator.excited(5, 4, 8))
            .isInstanceOf(IllegalArgumentException.class);
    }

    // TODO add more

    private Predicate<TwoHandedSiteswap> isNotGrounded()
    {
        return twoHandedSiteswap ->
        {
            if (!twoHandedSiteswap.isGrounded())
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed isNotGrounded", twoHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<TwoHandedSiteswap> periodLessThan(final int maxPeriod)
    {
        return twoHandedSiteswap ->
        {
            if (twoHandedSiteswap.getPeriod() <= maxPeriod)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed periodLessThan", twoHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<TwoHandedSiteswap> isGrounded()
    {
        return (twoHandedSiteswap) ->
        {
            if (twoHandedSiteswap.isGrounded())
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed isGrounded", twoHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<TwoHandedSiteswap> highestThroLessThanOrEqualTo(final int maxThro)
    {
        return twoHandedSiteswap ->
        {
            if (twoHandedSiteswap.getHighestThro().getNumBeats() <= maxThro)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed highestThroLessThanOrEqualTo", twoHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<TwoHandedSiteswap> classIsExactly()
    {
        return twoHandedSiteswap ->
        {
            if (twoHandedSiteswap.getClass() == TwoHandedSiteswap.class)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed classIsExactly", twoHandedSiteswap);
                return false;
            }
        };
    }

    private Predicate<TwoHandedSiteswap> numObjectsEquals(final int numObjects)
    {
        return twoHandedSiteswap ->
        {
            if (twoHandedSiteswap.getNumObjects() == numObjects)
            {
                return true;
            }
            else
            {
                LOG.warn("{} failed numObjectsEquals", twoHandedSiteswap);
                return false;
            }
        };
    }
}