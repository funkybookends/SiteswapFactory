package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.converters.GlobalLocal;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidFourHandedSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;

import java.util.Arrays;

/**
 * Created by caspar on 27/11/16.
 */
public class FourHandedSiteswap extends VanillaSiteswap
{
    private final static int[] ILLEGAL_THROWS = new int[]{1,3};
    private static final int MAX_THROW = 12; //C

    private static FourHandedSiteswap createGlobalOrLocal(int[] siteswap, boolean sort) throws InvalidSiteswapException
    {
        try
        {
            return new FourHandedSiteswap(siteswap, sort);
        }
        catch (InvalidSiteswapException e)
        {
            try
            {
                return new FourHandedSiteswap(GlobalLocal.globalToLocal(siteswap, 0), sort);
            }
            catch (InvalidSiteswapException e1)
            {
                throw new InvalidSiteswapException("Invalid as either Global or Local Siteswap");
            }
        }
    }

    public static FourHandedSiteswap create(int[] siteswap, boolean sort) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(siteswap, sort);
    }

    public static FourHandedSiteswap create(int[] siteswap) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(siteswap, true);
    }

    public static FourHandedSiteswap create(String siteswap, boolean sort) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(parseGlobalString(siteswap), sort);
    }

    public static FourHandedSiteswap create(String siteswap) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(parseGlobalString(siteswap), true);
    }

    private FourHandedSiteswap(int[] vanillaSiteswap, boolean sort) throws InvalidSiteswapException
    {
        super(vanillaSiteswap, sort);
        if (this.getHighestThrow() > MAX_THROW) throw new InvalidSiteswapException("Four Handed Siteswaps Cannot have throws larger than " + MAX_THROW);
        for (int thro : vanillaSiteswap)
        {
            for (int illegalThrow : ILLEGAL_THROWS)
            {
                if (thro == illegalThrow) throw new InvalidSiteswapException("Throw [" + thro + "] is illegal in Four Handed Siteswaps");
            }
        }
    }

    public String localLeader()
    {
        final int[] localLeaderIntSiteswap = GlobalLocal.globalToLocal(this.intSiteswap, 0);
        return VanillaSiteswap.toString(localLeaderIntSiteswap);
    }

    public String localFollower()
    {
        final int[] localFollowerIntSiteswap = GlobalLocal.globalToLocal(this.intSiteswap, 1);
        return VanillaSiteswap.toString(localFollowerIntSiteswap);
    }





    private static String vanillaToPrechac(int thro)
    {
        return thro % 2 == 1 ? String.valueOf(thro/2) + ".5p" : String.valueOf(thro/2);
    }

    private int[] parsePrecach(String precachString)
    {
    return null;
    }

}
