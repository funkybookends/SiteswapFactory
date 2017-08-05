package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX__X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.____X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.array;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;

/**
 Created by caspar on 29/07/17.
 */
public class StateUtilsTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void containsARepeatedState() throws Exception
    {
        softly.assertThat(StateUtils.containsARepeatedState(array(XX__X, XX__X))).isTrue();
        softly.assertThat(StateUtils.containsARepeatedState(array(XX__X, ____X))).isFalse();
        softly.assertThat(StateUtils.containsARepeatedState(array(XXX__, XX_X_, XX__X, ____X))).isFalse();
        softly.assertThat(StateUtils.containsARepeatedState(array(XXX__, XX_X_, XX__X, ____X, XXX__))).isTrue();
        softly.assertThat(StateUtils.containsARepeatedState(array(XXX__, XX_X_, XX__X, ____X, XX_X_))).isTrue();
        softly.assertThat(StateUtils.containsARepeatedState(array(XXX__, XX_X_, XX__X, ____X, XX__X))).isTrue();
        softly.assertThat(StateUtils.containsARepeatedState(array(XXX__, XX_X_, XX__X, ____X, ____X))).isTrue();
    }

    @Test
    public void containsGround() throws Exception
    {
        softly.assertThat(StateUtils.containsGround(array(XX__X, XX__X))).isFalse();
        softly.assertThat(StateUtils.containsGround(array(XX__X, ____X))).isFalse();
        softly.assertThat(StateUtils.containsGround(array(XXX__, XX_X_, XX__X, ____X))).isTrue();
        softly.assertThat(StateUtils.containsGround(array(XXX__, XX_X_, XX__X, ____X, XXX__))).isTrue();
        softly.assertThat(StateUtils.containsGround(array(XX_X_, XX__X, ____X, XX_X_))).isFalse();
        softly.assertThat(StateUtils.containsGround(array(XXX__, XX_X_, XX__X, ____X, XX__X))).isTrue();
        softly.assertThat(StateUtils.containsGround(array(XX_X_, XX__X, ____X, ____X))).isFalse();
    }

    @Test
    public void getAllStates() throws Exception
    {
        softly.assertThat(StateUtils.getAllStates(XXX__, new Thro[]{get(3)})).isEqualTo(array(XXX__));
        softly.assertThat(StateUtils.getAllStates(XXX__, new Thro[]{get(4), get(2)})).isEqualTo(array(XXX__, XX_X_));
        // TODO Add more
    }

    @Test
    public void getAllThrows() throws Exception
    {
        softly.assertThat(StateUtils.getAllThrows(array(XXX__), true)).isEqualTo(new Thro[]{get(3)});
        softly.assertThat(StateUtils.getAllThrows(array(XXX__, XX_X_), true)).isEqualTo(new Thro[]{get(4), get(2)});
        // TODO Add more
    }
}