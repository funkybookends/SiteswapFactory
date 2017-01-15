package com.ignoretheextraclub.siteswapfactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import org.junit.Test;

/**
 * Created by caspar on 10/12/16.
 */
public class ToJson
{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void fhstojson() throws Exception, InvalidSiteswapException
    {
        final String[] fhs = {"6789A"};
        final String[] ths = {"6789A"};

        for (String fh : fhs)
        {
            FourHandedSiteswap fourHandedSiteswap = FourHandedSiteswap.create(fh);
            System.out.println(objectMapper.writeValueAsString(fourHandedSiteswap));
            System.out.println();
        }
        System.out.println();
        for (String fh : ths)
        {
            System.out.println(objectMapper.writeValueAsString(TwoHandedSiteswap.create(fh)));
            System.out.println();
        }


    }
}