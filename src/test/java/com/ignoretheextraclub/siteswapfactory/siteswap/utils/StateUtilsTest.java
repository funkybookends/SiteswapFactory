package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.state;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.array;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;

/**
 Created by caspar on 29/07/17.
 */
public class StateUtilsTest
{
    private static final VanillaState XXX__ = state(true, true, true, false, false);
    private static final VanillaState XX_X_ = state(true, true, false, true, false);
    private static final VanillaState XX__X_ = state(true, true, false, false, true);
    private static final VanillaState ____X = state(false, false, false, false, true);

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void containsARepeatedState() throws Exception
    {
        softly.assertThat(StateUtils.containsARepeatedState(array(XX__X_, XX__X_))).isTrue();
        softly.assertThat(StateUtils.containsARepeatedState(array(XX__X_, ____X))).isFalse();
        softly.assertThat(StateUtils.containsARepeatedState(array(XXX__, XX_X_, XX__X_, ____X))).isFalse();
        softly.assertThat(StateUtils.containsARepeatedState(array(XXX__, XX_X_, XX__X_, ____X, XXX__))).isTrue();
        softly.assertThat(StateUtils.containsARepeatedState(array(XXX__, XX_X_, XX__X_, ____X, XX_X_))).isTrue();
        softly.assertThat(StateUtils.containsARepeatedState(array(XXX__, XX_X_, XX__X_, ____X, XX__X_))).isTrue();
        softly.assertThat(StateUtils.containsARepeatedState(array(XXX__, XX_X_, XX__X_, ____X, ____X))).isTrue();
    }

    @Test
    public void containsGround() throws Exception
    {
        softly.assertThat(StateUtils.containsGround(array(XX__X_, XX__X_))).isFalse();
        softly.assertThat(StateUtils.containsGround(array(XX__X_, ____X))).isFalse();
        softly.assertThat(StateUtils.containsGround(array(XXX__, XX_X_, XX__X_, ____X))).isTrue();
        softly.assertThat(StateUtils.containsGround(array(XXX__, XX_X_, XX__X_, ____X, XXX__))).isTrue();
        softly.assertThat(StateUtils.containsGround(array(XX_X_, XX__X_, ____X, XX_X_))).isFalse();
        softly.assertThat(StateUtils.containsGround(array(XXX__, XX_X_, XX__X_, ____X, XX__X_))).isTrue();
        softly.assertThat(StateUtils.containsGround(array(XX_X_, XX__X_, ____X, ____X))).isFalse();
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