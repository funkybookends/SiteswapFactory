package com.ignoretheextraclub.siteswapfactory.describer.fhs;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.text.StrSubstitutor;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.SimpleDescription;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;

public class SimpleFourHandedSiteswapDescriber implements DescriptionContributor<FourHandedSiteswap>
{
    private static final String DEFAULT_BASE_NAME = "i18n/simpleFourHandedSiteswapDescriber/simpleFourHandedSiteswapDescriber";

    private final Map<Locale, ResourceBundle> resourceBundles;

    public SimpleFourHandedSiteswapDescriber(final String baseName, final Locale... locales)
    {
        resourceBundles = Arrays.stream(locales).collect(Collectors.toMap(Function.identity(), locale -> ResourceBundle.getBundle(baseName, locale)));
    }

    public SimpleFourHandedSiteswapDescriber()
    {
        this(DEFAULT_BASE_NAME, Locale.forLanguageTag("en"));
    }

    @Override
    public Collection<Locale> getAvailableLocales()
    {
        return resourceBundles.keySet();
    }

    @Override
    public void contribute(final FourHandedSiteswap siteswap, final Locale locale, final SimpleDescription.Builder<FourHandedSiteswap> builder)
    {
        final Map<String, String> features = getFeatures(siteswap, builder);
        final String pattern = resourceBundles.get(locale).getString("longDescription");
        final StrSubstitutor strSubstitutor = new StrSubstitutor(features);
        final String description = strSubstitutor.replace(pattern);
        builder.withLongDescription(description);
    }

    private Map<String, String> getFeatures(final FourHandedSiteswap siteswap, final SimpleDescription.Builder<FourHandedSiteswap> builder)
    {
        final Map<String, String> features = new HashMap<>();
    }
}
