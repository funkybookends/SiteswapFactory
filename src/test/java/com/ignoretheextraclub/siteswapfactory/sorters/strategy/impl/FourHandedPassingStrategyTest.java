package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 Created by caspar on 15/01/17.
 */
@Ignore("need to agree for all these what is the best and then ensure implementation matches")
public class FourHandedPassingStrategyTest
{
    private static final Map<String, String[]> correctToRotations = new HashMap<>();

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Before
    public void setUp() throws Exception
    {
        input("9A678");
        input("975");
        input("78686");
        input("777777975");
        input("77777975");
        input("777779757");
        input("777779955");
        input("7777975");
        input("77779757");
        input("777797577");
        input("77779955");
        input("777799557");
        input("777975");
        input("7779757");
        input("77797577");
        input("777975777");
        input("777975975");
        input("7779955");
        input("77799557");
        input("777995577");
        input("77867885");
        input("77867966");
        input("778686786");
        input("778686885");
        input("778686966");
        input("77868956");
        input("77868974");
        input("77869685");
        input("77869784");
        input("77869964");
        input("778884786");
        input("778884885");
        input("778884966");
        input("77975");
        input("779757");
        input("7797577");
        input("77975777");
        input("779757777");
        input("779757975");
        input("77975975");
        input("779759757");
        input("779759955");
        input("779955");
        input("7799557");
        input("77995577");
        input("779955777");
        input("779955975");
        input("786786885");
        input("786786966");
        input("786788586");
        input("786788946");
        input("786885");
        input("786885786");
        input("786885885");
        input("786885966");
        input("786888972");
        input("786889485");
        input("786889782");
        input("786889962");
        input("786895686");
        input("786895884");
        input("786897486");
        input("786897882");
        input("786899484");
        input("786899682");
        input("786966");
        input("786966786");
        input("786966885");
        input("786966966");
        input("786968586");
        input("786968946");
        input("786978486");
        input("786978882");
        input("786996486");
        input("786996882");
        input("788586885");
        input("788586966");
        input("78858956");
        input("78858974");
        input("78859685");
        input("78859784");
        input("78859964");
        input("788946885");
        input("788946966");
        input("79667885");
        input("796686786");
        input("796686885");
        input("796686966");
        input("79668956");
        input("79668974");
        input("79669685");
        input("79669784");
        input("79669964");
        input("796884786");
        input("796884885");
        input("796884966");
        input("7975");
        input("79757");
        input("797577");
        input("7975777");
        input("79757777");
        input("797577777");
        input("797577975");
        input("797579757");
        input("797579955");
        input("7975975");
        input("79759757");
        input("797597577");
        input("79759955");
        input("797599557");
        input("79955");
        input("799557");
        input("7995577");
        input("79955777");
        input("799557777");
        input("799557975");
        input("79955975");
        input("799559757");
        input("799559955");
        input("86868884");
        input("868884");
        input("86888486");
        input("86888882");
        input("885885786");
        input("885885966");
        input("885888972");
        input("885889485");
        input("885889782");
        input("885889962");
        input("885895686");
        input("885895884");
        input("885897486");
        input("885897882");
        input("885899484");
        input("885899682");
        input("885966786");
        input("89748956");
        input("966885");
        input("966885786");
        input("966885885");
        input("966885966");
        input("966888972");
        input("966889485");
        input("966889782");
        input("966889962");
        input("966895686");
        input("966895884");
        input("966897486");
        input("966897882");
        input("966899484");
        input("966899682");
        input("966966786");
        input("966966885");
        input("966968586");
        input("966968946");
        input("968586885");
        input("96858956");
        input("96858974");
        input("968946885");
        input("975797577");
        input("975799557");
        input("9759757");
        input("97597577");
        input("975975777");
        input("9759955");
        input("97599557");
        input("975995577");
        input("978486885");
        input("978486966");
        input("97848956");
        input("97848974");
        input("97849685");
        input("97849964");
        input("978882885");
        input("978882966");
        input("99559757");
        input("995597577");
        input("995599557");
        input("996486885");
        input("996486966");
        input("99648956");
        input("99648974");
        input("99649685");
        input("996882885");
        input("996882966");
        input("4666600");
        input("466620");
        input("4666204");
        input("4662640");
        input("46622");
        input("466224");
        input("4662244");
        input("4646602");
        input("464640");
        input("4646404");
        input("4642");
        input("4642642");
        input("46424");
        input("464244");
        input("4642444");
        input("4466620");
        input("446622");
        input("4466224");
        input("4464640");
        input("44642");
        input("446424");
        input("4464244");
        input("4446622");
        input("444642");
        input("4446424");
        input("4444642");
    }

    @Test
    public void test() throws Exception
    {
        for (String expected : correctToRotations.keySet())
        {
            for (String constructor : correctToRotations.get(expected))
            {
                FourHandedSiteswap fourHandedSiteswap = SiteswapFactory.createFHS(constructor);
                softly.assertThat(fourHandedSiteswap.toString()).as("Constructed with: " + constructor).isEqualTo(expected);
            }
        }
    }

    private void input(final String siteswap)
    {
        final int period = siteswap.length();

        final String doubleSiteswap = siteswap + siteswap;

        final String[] rotations = new String[period];

        for (int i = 0; i < period; i++)
        {
            rotations[i] = doubleSiteswap.substring(i, period+i);
        }

        correctToRotations.put(siteswap, rotations);
    }
}