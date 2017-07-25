package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import java.util.Map;
import java.util.TreeMap;

/**
 Represents a Vanilla Siteswap Throw
 <p>
 Contains validation to ensure it is valid. Maximum throw is 'Z' == 35. Has methods which are guranteed to not throw
 exceptions, perhaps will return null instead. Has convenience methods for converting between representations.
 <p>
 Since there are a limited number of throws, this class denies access to the constructor. Instead use the static
 {@link #get(int)}, {@link #get(char)}, {@link #getOrNull(int)} or {@link #getOrNull(char)} to get a
 {@link VanillaThro} object. Internally we keep a {@link Map} of throws. You can call {@link #reset()} to empty the
 map if required.
 */
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
     The lowest possible throw.
     */
    public static final int MIN_THROW = 0;
    /**
     A store for the instances. This prevents thousands of the same instances without confining us to enums.
     */
    private static Map<Integer, VanillaThro> instances = new TreeMap<>();
    @JsonPropertyDescription("The size of the throw. Better thought of as the number of beats before this object will" + "be thrown again.") protected final int thro;

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

    public static Thro getOrNull(final char thro)
    {
        try
        {
            return get(thro);
        }
        catch (BadThrowException ignored)
        {
            return null;
        }
    }

    /**
     An alternative to {@link #get(int)} which will convert to an int for you.
     <p>
     Use {@link #getOrNull(char)} if you prefer a null value to an exception if the throw is not valid.

     @param thro

     @return
     */
    public static Thro get(final char thro) throws BadThrowException
    {
        return get(VanillaThroUtils.charToInt(thro));
    }

    /**
     A static method to obtain a {@link VanillaThro} object.
     <p>
     Use {@link #getOrNull(int)} if you prefer a null value returned instead of an exception for illegal throws.

     @param thro

     @return VanillaThro

     @throws BadThrowException if the throw is illegal.
     */
    public static VanillaThro get(final int thro) throws BadThrowException
    {
        if (!instances.containsKey(thro))
        {
            instances.put(thro, new VanillaThro(thro));
        }
        return instances.get(thro);
    }

    public static VanillaThro getOrNull(final int thro)
    {
        try
        {
            return get(thro);
        }
        catch (BadThrowException ignored)
        {
            return null;
        }
    }

    /**
     A method to empty the internal map of instances.
     */
    public static void reset()
    {
        instances = null;
    }

    /**
     Get the int size of the throw.

     @return
     */
    @Override
    @JsonProperty("throw_int")
    public int getNumBeats()
    {
        return thro;
    }

    @Override
    public int compareTo(Object o)
    {
        return this.getNumBeats() - ((VanillaThro) o).getNumBeats();
    }

    /**
     Get the number of objects needed to make this throw.

     @return int number of objects
     */
    @Override
    @JsonProperty("num_objects_thrown")
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
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        //TODO maybe we can say the FHS throws are equal?

        VanillaThro that = (VanillaThro) o;

        return thro == that.thro;
    }

    @Override
    @JsonProperty("throw_string")
    public String toString()
    {
        return String.valueOf(VanillaThroUtils.intToChar(thro));
    }
}
