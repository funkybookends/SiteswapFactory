package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;

/**
 Created by caspar on 07/01/17.
 */
public class FourHandedSiteswapThro extends VanillaThro
{
    /**
     A static list of illegal throws. These throws are not technically illegal, however it is useful to define them
     as such since generally we do not consider siteswaps with these throws. If you need these, consider reimplementing
     the class without these constraints.
     */
    public static final int[] ILLEGAL_THROWS = new int[]{1, 3};

    /**
     The maximum allowed throw. This is reimplemented because generally we do not consider thro FHSs with higher throws
     */
    protected static final int MAX_THROW = VanillaThroUtils.charToInt('C');

    /**
     The minimum legal throw.
     */
    private static final int MIN_THROW = 0;

    /**
     Since there are a small number of throws we keep them all in an array in order to easily reuse them.
     */
    private static FourHandedSiteswapThro[] THROW_INSTANCES = setup();

    /**
     A method to return all legal throws in an array.

     @return All legal throws.
     */
    private static FourHandedSiteswapThro[] setup()
    {
        final FourHandedSiteswapThro[] thros = new FourHandedSiteswapThro[MAX_THROW + 1];
        for (int i = 0; i < thros.length; i++)
        {
            try
            {
                thros[i] = new FourHandedSiteswapThro(i);
            }
            catch (final BadThrowException ignoreIllegalThrow)
            {
            }
        }
        return thros;
    }

    /**
     Constructs a new FourHandedSiteswapThro.

     @param thro the size of the throw

     @throws BadThrowException if it is illegal or bad.
     */
    protected FourHandedSiteswapThro(int thro) throws BadThrowException
    {
        super(thro);
        if (thro > MAX_THROW)
        {
            throw new BadThrowException("Throw too large");
        }
        for (int illegalThrow : ILLEGAL_THROWS)
        {
            if (thro == illegalThrow)
            {
                throw new BadThrowException("Throw [" + thro + "] is illegal");
            }
        }
    }

    /**
     Since the constructor is protected, use this method in order to get a thro.

     @param thro the size of the throw

     @return the throw

     @throws BadThrowException if it is illegal
     */
    public static FourHandedSiteswapThro get(final int thro) throws BadThrowException
    {
        if (thro < MIN_THROW || thro > MAX_THROW || THROW_INSTANCES[thro] == null)
        {
            throw new BadThrowException("Throw [" + thro + "] is illegal");
        }
        return THROW_INSTANCES[thro];
    }

    /**
     A convienence method to get a throw via a char.

     @param thro

     @return
     */
    public static FourHandedSiteswapThro get(char thro) throws BadThrowException
    {
        return get(VanillaThroUtils.charToInt(thro));
    }

    /**
     A connvience method to a get a throw without having to catch an exception. Useful for static constants. You should
     not use this generally, and instead use {@link #get(int)}

     @param thro

     @return
     */
    public static FourHandedSiteswapThro getUnchecked(final int thro)
    {
        try
        {
            return get(thro);
        }
        catch (final BadThrowException cause)
        {
            throw new IllegalArgumentException(cause);
        }
    }

    /**
     A connvience method to a get a throw without having to catch an exception. Useful for static constants. You should
     not use this generally, and instead use {@link #get(char)}

     @param thro

     @return
     */
    public static FourHandedSiteswapThro getUnchecked(final char thro)
    {
        try
        {
            return get(VanillaThroUtils.charToInt(thro));
        }
        catch (final BadThrowException cause)
        {
            throw new IllegalArgumentException(cause);
        }
    }
}