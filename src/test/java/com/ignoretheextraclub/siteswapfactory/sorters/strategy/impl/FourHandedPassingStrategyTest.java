package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;

import static com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.SortingTestUtils.getRotations;

/**
Created by caspar on 15/01/17.
*/
@RunWith(Parameterized.class)
@Ignore
public class FourHandedPassingStrategyTest
{
   @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

   private final String expected;

   public FourHandedPassingStrategyTest(final String expected)
   {
       this.expected = expected;
   }

   @Parameterized.Parameters(name = "Expecting {0}")
   public static Collection<Object[]> data() throws InvalidSiteswapException
   {
       final ArrayList<Object[]> tests = new ArrayList<>();

       // tests.add(new String[]{"777777975"});
       // tests.add(new String[]{"77777975"});
       // tests.add(new String[]{"777779955"});
       tests.add(new String[]{"7777975"});
       // tests.add(new String[]{"77779955"});
       // tests.add(new String[]{"777975"});
       // tests.add(new String[]{"777975975"});
       // tests.add(new String[]{"7779955"});
       tests.add(new String[]{"77867885"});
       tests.add(new String[]{"77867966"});
       tests.add(new String[]{"778686786"});
       tests.add(new String[]{"778686885"});
       tests.add(new String[]{"778686966"});
       tests.add(new String[]{"77868956"});
       tests.add(new String[]{"77868974"});
       tests.add(new String[]{"77869685"});
       tests.add(new String[]{"77869784"});
       tests.add(new String[]{"77869964"});
       tests.add(new String[]{"778884786"});
       tests.add(new String[]{"778884885"});
       tests.add(new String[]{"778884966"});
       tests.add(new String[]{"77975"});
       tests.add(new String[]{"779757975"});
       tests.add(new String[]{"77975975"});
       tests.add(new String[]{"779759955"});
       tests.add(new String[]{"779955"});
       tests.add(new String[]{"779955975"});
       tests.add(new String[]{"78686"});
       tests.add(new String[]{"786885"});
       tests.add(new String[]{"786885786"});
       tests.add(new String[]{"786885885"});
       tests.add(new String[]{"786885966"});
       tests.add(new String[]{"786888972"});
       tests.add(new String[]{"786889485"});
       tests.add(new String[]{"786889782"});
       tests.add(new String[]{"786889962"});
       tests.add(new String[]{"786895686"});
       tests.add(new String[]{"786895884"});
       tests.add(new String[]{"786897486"});
       tests.add(new String[]{"786897882"});
       tests.add(new String[]{"786899484"});
       tests.add(new String[]{"786899682"});
       tests.add(new String[]{"786966"});
       tests.add(new String[]{"786966786"});
       tests.add(new String[]{"786966885"}); // James disagrees(786966885)
       tests.add(new String[]{"786966966"});
       tests.add(new String[]{"786968586"});
       tests.add(new String[]{"786968946"});
       tests.add(new String[]{"788586786"});
       tests.add(new String[]{"788586885"});
       tests.add(new String[]{"788586966"});
       tests.add(new String[]{"78858956"});
       tests.add(new String[]{"78858974"});
       tests.add(new String[]{"78859685"});
       tests.add(new String[]{"788946786"});
       tests.add(new String[]{"788946885"});
       tests.add(new String[]{"788946966"});
       tests.add(new String[]{"79667885"});
       tests.add(new String[]{"796686786"});
       tests.add(new String[]{"796686885"});
       tests.add(new String[]{"796686966"});
       tests.add(new String[]{"79668956"});
       tests.add(new String[]{"79668974"});
       tests.add(new String[]{"79669685"});
       tests.add(new String[]{"79669784"}); //XXX
       tests.add(new String[]{"79669964"});
       tests.add(new String[]{"796884786"});
       tests.add(new String[]{"796884885"});
       tests.add(new String[]{"796884966"});
       tests.add(new String[]{"7975"});
       tests.add(new String[]{"797579955"});
       tests.add(new String[]{"7975975"});
       tests.add(new String[]{"79759955"});
       tests.add(new String[]{"79955"});
       tests.add(new String[]{"79955975"});
       tests.add(new String[]{"799559955"});
       tests.add(new String[]{"888972885"});
       tests.add(new String[]{"889485885"});
       tests.add(new String[]{"889782885"});
       tests.add(new String[]{"889962885"}); // ??????????????
       tests.add(new String[]{"895686885"}); //?????
       tests.add(new String[]{"895884885"});
       tests.add(new String[]{"897486885"});
       // tests.add(new String[]{"89748956"});
       tests.add(new String[]{"897882885"});
       tests.add(new String[]{"899484885"});
       tests.add(new String[]{"899682885"});
       tests.add(new String[]{"966885"});
       tests.add(new String[]{"966885885"});
       // tests.add(new String[]{"966888972"});
       tests.add(new String[]{"966889485"});
       tests.add(new String[]{"966889782"});
       tests.add(new String[]{"966889962"});
       tests.add(new String[]{"966895686"});
       tests.add(new String[]{"966895884"});
       tests.add(new String[]{"966897486"});
       // tests.add(new String[]{"966897882"});
       tests.add(new String[]{"966899484"});
       tests.add(new String[]{"966899682"});
       tests.add(new String[]{"966966885"});
       tests.add(new String[]{"968586885"});
       tests.add(new String[]{"968586966"});
       tests.add(new String[]{"96858956"});
       // tests.add(new String[]{"96858974"});
       tests.add(new String[]{"968946885"});
       tests.add(new String[]{"968946966"});
       tests.add(new String[]{"975"});
       // tests.add(new String[]{"9759955"});
       tests.add(new String[]{"97847885"});
       tests.add(new String[]{"978486786"});
       tests.add(new String[]{"978486885"});
       tests.add(new String[]{"978486966"});
       tests.add(new String[]{"97848956"});
       tests.add(new String[]{"97848974"});
       tests.add(new String[]{"97849685"});
       // tests.add(new String[]{"97849964"});
       tests.add(new String[]{"978882786"});
       tests.add(new String[]{"978882885"});
       tests.add(new String[]{"978882966"});
       tests.add(new String[]{"99647885"});
       tests.add(new String[]{"996486786"});
       tests.add(new String[]{"996486885"});
       tests.add(new String[]{"996486966"});
       tests.add(new String[]{"99648956"});
       tests.add(new String[]{"99648974"});
       tests.add(new String[]{"99649685"});
       tests.add(new String[]{"996882786"});
       tests.add(new String[]{"996882885"});
       tests.add(new String[]{"996882966"});
       // tests.add(new String[]{"9A678"});
       // tests.add(new String[]{"88A72"});

       return tests;
   }

   @Test
   public void isSameAsExpected() throws Exception
   {
       final FourHandedSiteswap expectedFhs = SiteswapFactory.getFourHandedSiteswap(new SiteswapRequest(expected, false, null, null, NoStartingStrategy.get()));

       for (final String constructor : getRotations(expected))
       {
           final FourHandedSiteswap result = SiteswapFactory.getFourHandedSiteswap(new SiteswapRequest(constructor, false, null, null, HighestThrowFirstStrategy.get()));
           softly.assertThat(result.toString()).as(//
             "Constructed with: " + constructor + ". " +
             "\nPreferred ex : " + Arrays.stream(expectedFhs.getStates()).map(VanillaState::excitedness).map(Object::toString).collect(Collectors.joining(", ")) +
             "\nPreferred Seq: " + Arrays.toString(expectedFhs.getStates()) +
             "\nActual Seq   : " + Arrays.toString(result.getStates()) +
             "\nActual ex    : " + Arrays.stream(result.getStates()).map(VanillaState::excitedness).map(Object::toString).collect(Collectors.joining(", "))//
           ).isEqualTo(expected);
       }
   }
}