package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.CharToIntConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.single.impl.IntToCharConverter;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 Represents a Vanilla Siteswap Throw
 <p>
 Contains validation to ensure it is valid. {@link #MAX_THROW} is 'Z' == 35. Has methods which are guranteed to not throw
 exceptions, and will return null instead. Has convenience methods for converting between representations.
 <p>
 */
@Immutable
public class VanillaThro implements Thro
{
    /**
     The char returned if an int is invalid or greater than 'z'.
     */
    public static final char INVALID_CHAR = '?';

    /**
     The int returned if a char is invalid.
     */
    public static final int INVALID_INT = -1;

    /**
     The lowest throw
     */
    private static final int MIN_THROW = 0;

    /**
     The maximum throw. Whilst you can throw higher throws technically, there is little use in reality. If you need this
     functionality, then reimplement this class without this constraint.
     */
    public static final int MAX_THROW = 35;

    /**
     Given the small number of throws, we keep them all in an array so we can reuse them.
     */
    private static final VanillaThro[] THROWS = setup();

    /**
     Static method to build all the legal throws.
     @return All legal throws.
     */
    private static VanillaThro[] setup()
    {
        try
        {
            final VanillaThro[] vanillaThros = new VanillaThro[MAX_THROW + 1];
            for (int thro = 0; thro < vanillaThros.length; thro++)
            {
                vanillaThros[thro] = new VanillaThro(thro);
            }
            return vanillaThros;
        }
        catch (final BadThrowException cause)
        {
            throw new IllegalStateException("Could not create throws", cause);
        }
    }

    /**
     The size of the throw.
     */
    protected final int thro;

    /**
     Constructs a throw
     @param thro the size of the throw.
     @throws BadThrowException if the throw is too small
     */
    protected VanillaThro(final int thro) throws BadThrowException
    {
        if (thro < MIN_THROW)
        {
            throw new BadThrowException("Cannot throw Vanilla Throw less than 0");
        }
        this.thro = thro;
    }

    /**
     A static method to obtain a {@link VanillaThro} object.
     <p>
     Use {@link #getUnchecked(int)} if you prefer a null value returned instead of an exception for illegal throws.

     @param thro

     @return VanillaThro

     @throws BadThrowException if the throw is illegal.
     */
    public static VanillaThro get(final int thro) throws BadThrowException
    {
        if (thro < MIN_THROW || thro > MAX_THROW)
        {
            throw new BadThrowException("Throw ["+thro+"] out of range");
        }
        return THROWS[thro];
    }

    /**
     An alternative to {@link #get(int)} which will convert to an int for you.
     <p>
     Use {@link #getUnchecked(char)} if you prefer a null value to an exception if the throw is not valid.

     @param thro

     @return
     */
    public static VanillaThro get(final char thro) throws BadThrowException
    {
        return get(CharToIntConverter.get().apply(thro));
    }

    /**
     Gets a throw but will not throw an exception if illegal and instead will return null. Useful for static constant
     generation. You should not use this generally and use {@link #get(char)} instead.
     @param thro
     @return The thro
     */
    public static VanillaThro getUnchecked(final char thro)
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
     Gets a throw but will not throw an exception if illegal and instead will return null. Useful for static constant
     generation. You should not use this generally and use {@link #get(int)} instead.
     @param thro
     @return The thro
     */
    public static VanillaThro getUnchecked(final int thro)
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

    @Override
    public int getNumBeats()
    {
        return thro;
    }

    @Override
    public int compareTo(Object o)
    {
        return this.getNumBeats() - ((VanillaThro) o).getNumBeats();
    }

    @Override
    public int getNumObjectsThrown()
    {
        return thro == 0 ? 0 : 1;
    }

    @Override
    public int hashCode()
    {
        return thro;
    }

    @Override
    public boolean equals(Object o)
    {
        try
        {
            final VanillaThro other = (VanillaThro) o;
            return other.thro == this.thro;
        }
        catch (final Exception any)
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        return String.valueOf(IntToCharConverter.get().apply(thro));
    }
}
