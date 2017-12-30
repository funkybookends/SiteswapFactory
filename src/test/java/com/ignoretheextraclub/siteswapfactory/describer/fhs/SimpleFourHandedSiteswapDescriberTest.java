package com.ignoretheextraclub.siteswapfactory.describer.fhs;

import java.util.Locale;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.impl.SimpleDescription;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleFourHandedSiteswapDescriberTest
{
    private static final Locale LOCALE = Locale.forLanguageTag("en");

    private DescriptionContributor<FourHandedSiteswap> describer = new SimpleFourHandedSiteswapDescriber();
    private SimpleDescription.Builder<FourHandedSiteswap> builder;

    @Test
    public void testLoadsLocale() throws Exception
    {
        describer = new SimpleFourHandedSiteswapDescriber();
    }

    @Test
    public void test975() throws Exception
    {
        final FourHandedSiteswap siteswap = SiteswapFactory.getFourHandedSiteswap("975");
        builder = new SimpleDescription.Builder<>(siteswap);
        builder.addSiteswapName("Holy Grail");
        builder.withLocale(LOCALE);
        describer.contribute(siteswap, LOCALE, builder);
        final SimpleDescription<FourHandedSiteswap> simpleDescription = builder.createSimpleDescription();

        assertThat(simpleDescription.getDescription()).isEqualTo(
            "Holy Grail is a period 3 pattern with 7 clubs for 2 jugglers. " +
                "Aidan begins with Double, Zap, Pass, " +
                "and Becky responds with Pass, Double, Zap.");

        assertThat(simpleDescription.getLongDescription()).isEqualTo(
            "Holy Grail is a period 3 pattern with 7 clubs for 2 jugglers. " +
                "Aidan has 2 clubs in their right hand and 2 clubs in the left hand. " +
                "Aidan begins with the right hand and his sequence is " +
                "Double, Zap, Pass. " +
                "Becky has 2 clubs in her right hand, one club in the left hand and begins half a beat later. " +
                "Becky begins with the right hand and her sequence is " +
                "Pass, Double, Zap.");
    }
}