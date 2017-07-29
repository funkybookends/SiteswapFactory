package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;

/**
 Created by caspar on 29/07/17.
 */
public class VanillaThroUtilsTest
{
    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testIntToChar() throws Exception
    {
        softly.assertThat(VanillaThroUtils.intToChar(0)).isEqualTo('0');
        softly.assertThat(VanillaThroUtils.intToChar(1)).isEqualTo('1');
        softly.assertThat(VanillaThroUtils.intToChar(2)).isEqualTo('2');
        softly.assertThat(VanillaThroUtils.intToChar(3)).isEqualTo('3');
        softly.assertThat(VanillaThroUtils.intToChar(4)).isEqualTo('4');
        softly.assertThat(VanillaThroUtils.intToChar(5)).isEqualTo('5');
        softly.assertThat(VanillaThroUtils.intToChar(6)).isEqualTo('6');
        softly.assertThat(VanillaThroUtils.intToChar(7)).isEqualTo('7');
        softly.assertThat(VanillaThroUtils.intToChar(8)).isEqualTo('8');
        softly.assertThat(VanillaThroUtils.intToChar(9)).isEqualTo('9');
        softly.assertThat(VanillaThroUtils.intToChar(10)).isEqualTo('A');
        softly.assertThat(VanillaThroUtils.intToChar(11)).isEqualTo('B');
        softly.assertThat(VanillaThroUtils.intToChar(12)).isEqualTo('C');
        softly.assertThat(VanillaThroUtils.intToChar(13)).isEqualTo('D');
        softly.assertThat(VanillaThroUtils.intToChar(14)).isEqualTo('E');
        softly.assertThat(VanillaThroUtils.intToChar(15)).isEqualTo('F');
        softly.assertThat(VanillaThroUtils.intToChar(16)).isEqualTo('G');
        softly.assertThat(VanillaThroUtils.intToChar(17)).isEqualTo('H');
    }

    @Test
    public void testNumObjects() throws Exception
    {
        softly.assertThat(VanillaThroUtils.numObjects(VanillaThroUtils.intArrayToVanillaThrowArray(new int[]{5, 6, 7})))
              .isEqualTo(6);

        softly.assertThat(VanillaThroUtils.numObjects(VanillaThroUtils.intArrayToVanillaThrowArray(new int[]{1})))
              .isEqualTo(1);

        softly.assertThat(VanillaThroUtils.numObjects(VanillaThroUtils.intArrayToVanillaThrowArray(new int[]{9, 7, 2})))
              .isEqualTo(6);

        softly.assertThat(VanillaThroUtils.numObjects(VanillaThroUtils.intArrayToVanillaThrowArray(new int[]{8, 9, 10})))
              .isEqualTo(9);

        softly.assertThat(VanillaThroUtils.numObjects(VanillaThroUtils.intArrayToVanillaThrowArray(new int[]{3, 4, 5})))
              .isEqualTo(4);

        softly.assertThat(VanillaThroUtils.numObjects(VanillaThroUtils.intArrayToVanillaThrowArray(new int[]{5, 6, 7})))
              .isEqualTo(6);

        softly.assertThat(VanillaThroUtils.numObjects(VanillaThroUtils.intArrayToVanillaThrowArray(new int[]{5, 5, 5, 0, 0})))
              .isEqualTo(3);
    }

    @Test
    public void globalToLocal() throws Exception
    {
        softly.assertThat(VanillaThroUtils.globalToLocal(new int[]{9, 7, 5}, 0))
            .isEqualTo(new int[]{9, 5, 7});

        softly.assertThat(VanillaThroUtils.globalToLocal(new int[]{9, 7, 5}, 1))
            .isEqualTo(new int[]{7, 9, 5});

        softly.assertThat(VanillaThroUtils.globalToLocal(new int[]{7, 8, 9, 10, 6}, 0))
            .isEqualTo(new int[]{7, 9, 6, 8, 10});

        softly.assertThat(VanillaThroUtils.globalToLocal(new int[]{7, 8, 9, 10, 6}, 1))
            .isEqualTo(new int[]{8, 10, 7, 9, 6});

        softly.assertThat(VanillaThroUtils.globalToLocal(new int[]{7,8,6,8,6,8,6}, 0))
            .isEqualTo(new int[]{7,6,6,6,8,8,8});

        softly.assertThat(VanillaThroUtils.globalToLocal(new int[]{7,8,6,8,6,8,6}, 1))
            .isEqualTo(new int[]{8,8,8,7,6,6,6});
    }

    @Test
    public void localToGlobal() throws Exception
    {
        softly.assertThat(VanillaThroUtils.localToGlobal(new int[]{9, 5, 7}))
            .isEqualTo(new int[]{9, 7, 5});

        softly.assertThat(VanillaThroUtils.localToGlobal(new int[]{7, 9, 6, 8, 10}))
            .isEqualTo(new int[]{7, 8, 9, 10, 6});

        softly.assertThat(VanillaThroUtils.localToGlobal(new int[]{7,6,6,6,8,8,8}))
              .isEqualTo(new int[]{7,8,6,8,6,8,6});

        softly.assertThat(VanillaThroUtils.localToGlobal(new int[]{8,8,8,7,6,6,6}))
              .isEqualTo(new int[]{8,6,8,6,8,6,7});
    }

    @Test
    public void globalToLocalObject() throws Exception
    {
        softly.assertThat(VanillaThroUtils.globalToLocal(new Integer[]{9, 7, 5}, 0))
            .isEqualTo(new Integer[]{9, 5, 7});

        softly.assertThat(VanillaThroUtils.globalToLocal(new Integer[]{9, 7, 5}, 1))
            .isEqualTo(new Integer[]{7, 9, 5});

        softly.assertThat(VanillaThroUtils.globalToLocal(new Integer[]{7, 8, 9, 10, 6}, 0))
            .isEqualTo(new Integer[]{7, 9, 6, 8, 10});

        softly.assertThat(VanillaThroUtils.globalToLocal(new Integer[]{7, 8, 9, 10, 6}, 1))
            .isEqualTo(new Integer[]{8, 10, 7, 9, 6});
    }

    @Test
    public void localToGlobalObject() throws Exception
    {
        softly.assertThat(VanillaThroUtils.localToGlobal(new Integer[]{9, 5, 7}))
            .isEqualTo(new Integer[]{9, 7, 5});

        softly.assertThat(VanillaThroUtils.localToGlobal(new Integer[]{7, 9, 6, 8, 10}))
            .isEqualTo(new Integer[]{7, 8, 9, 10, 6});
    }

    @Test
    public void testVanillaThrowArrayToIntArray() throws Exception
    {
        softly.assertThat(VanillaThroUtils.vanillaThrowArrayToIntArray(new VanillaThro[]{get(1), get(2), get(3)}))
              .isEqualTo(new int[]{1,2,3});

        softly.assertThat(VanillaThroUtils.vanillaThrowArrayToIntArray(new VanillaThro[]{get(0), get(2), get(9)}))
              .isEqualTo(new int[]{0,2,9});

        softly.assertThat(VanillaThroUtils.vanillaThrowArrayToIntArray(new VanillaThro[]{get(12), get(2), get(3)}))
              .isEqualTo(new int[]{12,2,3});

        softly.assertThat(VanillaThroUtils.vanillaThrowArrayToIntArray(new VanillaThro[]{get(1), get(30), get(3)}))
              .isEqualTo(new int[]{1,30,3});

        softly.assertThat(VanillaThroUtils.vanillaThrowArrayToIntArray(new VanillaThro[]{get(1), get(30), get(3)}))
              .isEqualTo(new int[]{1,30,3});

        softly.assertThat(VanillaThroUtils.vanillaThrowArrayToIntArray(new VanillaThro[]{}))
              .isEqualTo(new int[]{});
    }

    @Test
    public void testVanillaThrowArrayToString() throws Exception
    {
        softly.assertThat(VanillaThroUtils.vanillaThrowArrayToString(new VanillaThro[]{get(1), get(2), get(3)}))
              .isEqualTo("123");

        softly.assertThat(VanillaThroUtils.vanillaThrowArrayToString(new VanillaThro[]{get(0), get(2), get(9)}))
              .isEqualTo("029");

        softly.assertThat(VanillaThroUtils.vanillaThrowArrayToString(new VanillaThro[]{get(12), get(2), get(3)}))
              .isEqualTo("C23");

        softly.assertThat(VanillaThroUtils.vanillaThrowArrayToString(new VanillaThro[]{get(1), get(30), get(3)}))
              .isEqualTo("1U3");
    }
}