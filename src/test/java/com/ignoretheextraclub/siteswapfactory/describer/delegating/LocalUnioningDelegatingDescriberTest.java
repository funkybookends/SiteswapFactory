package com.ignoretheextraclub.siteswapfactory.describer.delegating;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.VanillaSiteswap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocalUnioningDelegatingDescriberTest
{
    @Test
    public void test() throws Exception
    {
        final DescriptionContributor<VanillaSiteswap> contributor1 = mock(DescriptionContributor.class);
        final DescriptionContributor<VanillaSiteswap> contributor2 = mock(DescriptionContributor.class);

        when(contributor1.getAvailableLocales()).thenReturn(Arrays.asList(Locale.ENGLISH, Locale.GERMAN));
        when(contributor2.getAvailableLocales()).thenReturn(Arrays.asList(Locale.ENGLISH, Locale.CHINESE));

        final DescriptionContributor<VanillaSiteswap> result = new LocalUnioningDelegatingDescriber<>(Arrays.asList(contributor1, contributor2));

        verify(contributor1).getAvailableLocales();
        verify(contributor2).getAvailableLocales();

        assertThat(result.getAvailableLocales()).containsExactlyInAnyOrder(Locale.ENGLISH, Locale.GERMAN, Locale.CHINESE);
    }
}