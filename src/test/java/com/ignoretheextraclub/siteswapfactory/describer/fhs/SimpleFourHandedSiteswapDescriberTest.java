package com.ignoretheextraclub.siteswapfactory.describer.fhs;

import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.SimpleDescription;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    public void test7() throws Exception
    {
        final FourHandedSiteswap seven = SiteswapFactory.getFourHandedSiteswap("7");
        builder = new SimpleDescription.Builder<>(seven);
        builder.addSiteswapName("Seven");
        describer.contribute(seven, LOCALE, builder);
        final SimpleDescription<FourHandedSiteswap> simpleDescription = builder.createSimpleDescription();

        assertThat(simpleDescription.getDescription()).isEqualTo("");
        assertThat(simpleDescription.getLongDescription()).isEqualTo("");
    }
}