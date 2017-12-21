package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound;

import java.util.function.Function;

import org.apache.commons.lang.StringUtils;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.IntsToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl.StringToIntsConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

/**
 * Converts a string of ints to an array of VanillaThros
 *
 * @author Caspar Nonclercq
 */
public class StringToVanillaThrosConverter implements Function<String, VanillaThro[]>
{
    public static StringToVanillaThrosConverter INSTANCE;

    private StringToVanillaThrosConverter()
    {
        // Singleton
    }

    public static StringToVanillaThrosConverter get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StringToVanillaThrosConverter();
        }
        return INSTANCE;
    }

    /**
     * Converts a string of ints to an array of VanillaThros
     *
     * @param siteswap a string of ints
     *
     * @return An array of vanilla thros
     */
    @Override
    public VanillaThro[] apply(final String siteswap)
    {
        if (StringUtils.isEmpty(siteswap))
        {
            throw new IllegalArgumentException("siteswap cannot be null or empty");
        }
        try
        {
            return convertToInts().andThen(convertToVanillaThros()).apply(siteswap);
        }
        catch (final BadThrowException cause)
        {
            throw new BadThrowException("String [" + siteswap + "] contained a bad throw", cause);
        }
    }

    private IntsToVanillaThrosConverter convertToVanillaThros()
    {
        return IntsToVanillaThrosConverter.get();
    }

    private StringToIntsConverter convertToInts()
    {
        return StringToIntsConverter.get();
    }

    /**
     * Convenient static method to apply a string to an array of vanilla thros
     *
     * @param string A string of ints
     *
     * @return An array of Vanilla thros
     */
    public static VanillaThro[] convert(final String string)
    {
        return get().apply(string);
    }
}
