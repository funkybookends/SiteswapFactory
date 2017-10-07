//package com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl;
//
//import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
//import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
//import org.assertj.core.api.JUnitSoftAssertions;
//import org.junit.Ignore;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//import static com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.SortingTestUtils.getRotations;
//
///**
// Created by caspar on 15/01/17.
// */
//@RunWith(Parameterized.class)
//@Ignore("Needs new more stable, better implementation, current sufficient for now")
//public class FourHandedPassingStrategyTest
//{
//    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();
//
//    private final String expected;
//
//    public FourHandedPassingStrategyTest(final String expected, final String result)
//    {
//        this.expected = expected;
//    }
//
//    @Parameterized.Parameters(name = "{0} -> {1}")
//    public static Collection<Object[]> data() throws InvalidSiteswapException
//    {
//        final ArrayList<Object[]> tests = new ArrayList<>();
//
//        tests.add(new String[]{"777777975", SiteswapFactory.createFHS("777777975").toString()});
//        tests.add(new String[]{"77777975", SiteswapFactory.createFHS("77777975").toString()});
//        tests.add(new String[]{"777779955", SiteswapFactory.createFHS("777779955").toString()});
//        tests.add(new String[]{"7777975", SiteswapFactory.createFHS("7777975").toString()});
//        tests.add(new String[]{"77779955", SiteswapFactory.createFHS("77779955").toString()});
//        tests.add(new String[]{"777975", SiteswapFactory.createFHS("777975").toString()});
//        tests.add(new String[]{"777975975", SiteswapFactory.createFHS("777975975").toString()});
//        tests.add(new String[]{"7779955", SiteswapFactory.createFHS("7779955").toString()});
//        tests.add(new String[]{"77867885", SiteswapFactory.createFHS("77867885").toString()});
//        tests.add(new String[]{"77867966", SiteswapFactory.createFHS("77867966").toString()});
//        tests.add(new String[]{"778686786", SiteswapFactory.createFHS("778686786").toString()});
//        tests.add(new String[]{"778686885", SiteswapFactory.createFHS("778686885").toString()});
//        tests.add(new String[]{"778686966", SiteswapFactory.createFHS("778686966").toString()});
//        tests.add(new String[]{"77868956", SiteswapFactory.createFHS("77868956").toString()}); // ?
//        tests.add(new String[]{"77868974", SiteswapFactory.createFHS("77868974").toString()});
//        tests.add(new String[]{"77869685", SiteswapFactory.createFHS("77869685").toString()});
//        tests.add(new String[]{"77869784", SiteswapFactory.createFHS("77869784").toString()});
//        tests.add(new String[]{"77869964", SiteswapFactory.createFHS("77869964").toString()});
//        tests.add(new String[]{"778884786", SiteswapFactory.createFHS("778884786").toString()});
//        tests.add(new String[]{"778884885", SiteswapFactory.createFHS("778884885").toString()});
//        tests.add(new String[]{"778884966", SiteswapFactory.createFHS("778884966").toString()});
//        tests.add(new String[]{"77975", SiteswapFactory.createFHS("77975").toString()});
//        tests.add(new String[]{"779757975", SiteswapFactory.createFHS("779757975").toString()});
//        tests.add(new String[]{"77975975", SiteswapFactory.createFHS("77975975").toString()});
//        tests.add(new String[]{"779759955", SiteswapFactory.createFHS("779759955").toString()});
//        tests.add(new String[]{"779955", SiteswapFactory.createFHS("779955").toString()});
//        tests.add(new String[]{"779955975", SiteswapFactory.createFHS("779955975").toString()});
//        tests.add(new String[]{"78686", SiteswapFactory.createFHS("78686").toString()});
//        tests.add(new String[]{"786885", SiteswapFactory.createFHS("786885").toString()});
//        tests.add(new String[]{"786885786", SiteswapFactory.createFHS("786885786").toString()});
//        tests.add(new String[]{"786885885", SiteswapFactory.createFHS("786885885").toString()});
//        tests.add(new String[]{"786885966", SiteswapFactory.createFHS("786885966").toString()});
//        tests.add(new String[]{"786888972", SiteswapFactory.createFHS("786888972").toString()});
//        tests.add(new String[]{"786889485", SiteswapFactory.createFHS("786889485").toString()});
//        tests.add(new String[]{"786889782", SiteswapFactory.createFHS("786889782").toString()});
//        tests.add(new String[]{"786889962", SiteswapFactory.createFHS("786889962").toString()});
//        tests.add(new String[]{"786895686", SiteswapFactory.createFHS("786895686").toString()});
//        tests.add(new String[]{"786895884", SiteswapFactory.createFHS("786895884").toString()});
//        tests.add(new String[]{"786897486", SiteswapFactory.createFHS("786897486").toString()});
//        tests.add(new String[]{"786897882", SiteswapFactory.createFHS("786897882").toString()});
//        tests.add(new String[]{"786899484", SiteswapFactory.createFHS("786899484").toString()});
//        tests.add(new String[]{"786899682", SiteswapFactory.createFHS("786899682").toString()});
//        tests.add(new String[]{"786966", SiteswapFactory.createFHS("786966").toString()});
//        tests.add(new String[]{"786966786", SiteswapFactory.createFHS("786966786").toString()});
//        tests.add(new String[]{"786966885", SiteswapFactory.createFHS("786966885").toString()}); // James disagrees(786966885)
//        tests.add(new String[]{"786966966", SiteswapFactory.createFHS("786966966").toString()});
//        tests.add(new String[]{"786968586", SiteswapFactory.createFHS("786968586").toString()});
//        tests.add(new String[]{"786968946", SiteswapFactory.createFHS("786968946").toString()});
//        tests.add(new String[]{"788586786", SiteswapFactory.createFHS("788586786").toString()});
//        tests.add(new String[]{"788586885", SiteswapFactory.createFHS("788586885").toString()});
//        tests.add(new String[]{"788586966", SiteswapFactory.createFHS("788586966").toString()});
//        tests.add(new String[]{"78858956", SiteswapFactory.createFHS("78858956").toString()});
//        tests.add(new String[]{"78858974", SiteswapFactory.createFHS("78858974").toString()});
//        tests.add(new String[]{"78859685", SiteswapFactory.createFHS("78859685").toString()});
//        tests.add(new String[]{"788946786", SiteswapFactory.createFHS("788946786").toString()});
//        tests.add(new String[]{"788946885", SiteswapFactory.createFHS("788946885").toString()});
//        tests.add(new String[]{"788946966", SiteswapFactory.createFHS("788946966").toString()});
//        tests.add(new String[]{"79667885", SiteswapFactory.createFHS("79667885").toString()});
//        tests.add(new String[]{"796686786", SiteswapFactory.createFHS("796686786").toString()});
//        tests.add(new String[]{"796686885", SiteswapFactory.createFHS("796686885").toString()});
//        tests.add(new String[]{"796686966", SiteswapFactory.createFHS("796686966").toString()});
//        tests.add(new String[]{"79668956", SiteswapFactory.createFHS("79668956").toString()});
//        tests.add(new String[]{"79668974", SiteswapFactory.createFHS("79668974").toString()});
//        tests.add(new String[]{"79669685", SiteswapFactory.createFHS("79669685").toString()});
//        tests.add(new String[]{"79669784", SiteswapFactory.createFHS("79669784").toString()}); //XXX
//        tests.add(new String[]{"79669964", SiteswapFactory.createFHS("79669964").toString()});
//        tests.add(new String[]{"796884786", SiteswapFactory.createFHS("796884786").toString()});
//        tests.add(new String[]{"796884885", SiteswapFactory.createFHS("796884885").toString()});
//        tests.add(new String[]{"796884966", SiteswapFactory.createFHS("796884966").toString()});
//        tests.add(new String[]{"7975", SiteswapFactory.createFHS("7975").toString()});
//        tests.add(new String[]{"797579955", SiteswapFactory.createFHS("797579955").toString()});
//        tests.add(new String[]{"7975975", SiteswapFactory.createFHS("7975975").toString()});
//        tests.add(new String[]{"79759955", SiteswapFactory.createFHS("79759955").toString()});
//        tests.add(new String[]{"79955", SiteswapFactory.createFHS("79955").toString()});
//        tests.add(new String[]{"79955975", SiteswapFactory.createFHS("79955975").toString()});
//        tests.add(new String[]{"799559955", SiteswapFactory.createFHS("799559955").toString()});
//        tests.add(new String[]{"888972885", SiteswapFactory.createFHS("888972885").toString()});
//        tests.add(new String[]{"889485885", SiteswapFactory.createFHS("889485885").toString()});
//        tests.add(new String[]{"889782885", SiteswapFactory.createFHS("889782885").toString()});
//        tests.add(new String[]{"889962885", SiteswapFactory.createFHS("889962885").toString()}); // ??????????????
//        tests.add(new String[]{"895686885", SiteswapFactory.createFHS("895686885").toString()}); //?????
//        tests.add(new String[]{"895884885", SiteswapFactory.createFHS("895884885").toString()});
//        tests.add(new String[]{"897486885", SiteswapFactory.createFHS("897486885").toString()});
//        tests.add(new String[]{"89748956", SiteswapFactory.createFHS("89748956").toString()});
//        tests.add(new String[]{"897882885", SiteswapFactory.createFHS("897882885").toString()});
//        tests.add(new String[]{"899484885", SiteswapFactory.createFHS("899484885").toString()});
//        tests.add(new String[]{"899682885", SiteswapFactory.createFHS("899682885").toString()});
//        tests.add(new String[]{"966885", SiteswapFactory.createFHS("966885").toString()});
//        tests.add(new String[]{"966885885", SiteswapFactory.createFHS("966885885").toString()});
//        tests.add(new String[]{"966888972", SiteswapFactory.createFHS("966888972").toString()});
//        tests.add(new String[]{"966889485", SiteswapFactory.createFHS("966889485").toString()});
//        tests.add(new String[]{"966889782", SiteswapFactory.createFHS("966889782").toString()});
//        tests.add(new String[]{"966889962", SiteswapFactory.createFHS("966889962").toString()});
//        tests.add(new String[]{"966895686", SiteswapFactory.createFHS("966895686").toString()});
//        tests.add(new String[]{"966895884", SiteswapFactory.createFHS("966895884").toString()});
//        tests.add(new String[]{"966897486", SiteswapFactory.createFHS("966897486").toString()});
//        tests.add(new String[]{"966897882", SiteswapFactory.createFHS("966897882").toString()});
//        tests.add(new String[]{"966899484", SiteswapFactory.createFHS("966899484").toString()});
//        tests.add(new String[]{"966899682", SiteswapFactory.createFHS("966899682").toString()});
//        tests.add(new String[]{"966966885", SiteswapFactory.createFHS("966966885").toString()});
//        tests.add(new String[]{"968586885", SiteswapFactory.createFHS("968586885").toString()});
//        tests.add(new String[]{"968586966", SiteswapFactory.createFHS("968586966").toString()});
//        tests.add(new String[]{"96858956", SiteswapFactory.createFHS("96858956").toString()});
//        tests.add(new String[]{"96858974", SiteswapFactory.createFHS("96858974").toString()});
//        tests.add(new String[]{"968946885", SiteswapFactory.createFHS("968946885").toString()});
//        tests.add(new String[]{"968946966", SiteswapFactory.createFHS("968946966").toString()});
//        tests.add(new String[]{"975", SiteswapFactory.createFHS("975").toString()});
//        tests.add(new String[]{"9759955", SiteswapFactory.createFHS("9759955").toString()});
//        tests.add(new String[]{"97847885", SiteswapFactory.createFHS("97847885").toString()});
//        tests.add(new String[]{"978486786", SiteswapFactory.createFHS("978486786").toString()});
//        tests.add(new String[]{"978486885", SiteswapFactory.createFHS("978486885").toString()});
//        tests.add(new String[]{"978486966", SiteswapFactory.createFHS("978486966").toString()});
//        tests.add(new String[]{"97848956", SiteswapFactory.createFHS("97848956").toString()});
//        tests.add(new String[]{"97848974", SiteswapFactory.createFHS("97848974").toString()});
//        tests.add(new String[]{"97849685", SiteswapFactory.createFHS("97849685").toString()});
//        tests.add(new String[]{"97849964", SiteswapFactory.createFHS("97849964").toString()});
//        tests.add(new String[]{"978882786", SiteswapFactory.createFHS("978882786").toString()});
//        tests.add(new String[]{"978882885", SiteswapFactory.createFHS("978882885").toString()});
//        tests.add(new String[]{"978882966", SiteswapFactory.createFHS("978882966").toString()});
//        tests.add(new String[]{"99647885", SiteswapFactory.createFHS("99647885").toString()});
//        tests.add(new String[]{"996486786", SiteswapFactory.createFHS("996486786").toString()});
//        tests.add(new String[]{"996486885", SiteswapFactory.createFHS("996486885").toString()});
//        tests.add(new String[]{"996486966", SiteswapFactory.createFHS("996486966").toString()});
//        tests.add(new String[]{"99648956", SiteswapFactory.createFHS("99648956").toString()});
//        tests.add(new String[]{"99648974", SiteswapFactory.createFHS("99648974").toString()});
//        tests.add(new String[]{"99649685", SiteswapFactory.createFHS("99649685").toString()});
//        tests.add(new String[]{"996882786", SiteswapFactory.createFHS("996882786").toString()});
//        tests.add(new String[]{"996882885", SiteswapFactory.createFHS("996882885").toString()});
//        tests.add(new String[]{"996882966", SiteswapFactory.createFHS("996882966").toString()});
//        tests.add(new String[]{"9A678", SiteswapFactory.createFHS("9A678").toString()});
//        tests.add(new String[]{"88A72", SiteswapFactory.createFHS("88A72").toString()});
//
//        return tests;
//    }
//
//    @Test
//    public void isSameAsExpected() throws Exception
//    {
//        final FourHandedSiteswap expectedFhs = SiteswapFactory.createFHS(expected, NoStartingStrategy.get(), true);
//        for (final String constructor : getRotations(expected))
//        {
//            final FourHandedSiteswap result = SiteswapFactory.createFHS(constructor);
//            softly.assertThat(result.toString()).as(//
//              "Constructed with: " + constructor + ". " +
//              "\nPreferred ex : " + Arrays.stream(expectedFhs.getStates()).map(VanillaState::excitedness).map(Object::toString).collect(Collectors.joining(", ")) +
//              "\nPreferred Seq: " + Arrays.toString(expectedFhs.getStates()) +
//              "\nActual Seq   : " + Arrays.toString(result.getStates()) +
//              "\nActual ex    : " + Arrays.stream(result.getStates()).map(VanillaState::excitedness).map(Object::toString).collect(Collectors.joining(", "))//
//            ).isEqualTo(expected);
//        }
//    }
//}