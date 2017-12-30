package com.ignoretheextraclub.siteswapfactory.describer.impl;

import java.util.Locale;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ToStringSiteswapDescriptionContributorTest
{
    @Test
    public void test() throws Exception
    {
        final Siteswap siteswap = mock(Siteswap.class);
        final SimpleDescription.Builder builder = mock(SimpleDescription.Builder.class);

        final ToStringSiteswapDescriptionContributor<Siteswap> contributor = new ToStringSiteswapDescriptionContributor<>();

        when(siteswap.toString()).thenReturn("toString");
        contributor.contribute(siteswap, Locale.CANADA, builder);

        verify(builder).addSiteswapName("toString");
    }
}