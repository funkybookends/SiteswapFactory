package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX__X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.thros;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.getUnchecked;

/**
 Created by caspar on 29/07/17.
 */
public class StateValidationUtilsTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testNoExceptionWhenTrue() throws Exception
    {
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XX__X));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XX__X, XX_X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XX__X, XX_X_, XX__X));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XX__X, XX_X_, XX__X, XX_X_));

        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XXX__X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XXX__X_, XXX_X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XXX__X_, XXX_X_, XXX__X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XXX__X_, XXX_X_, XXX__X_, XXX_X_));
    }

    @Test
    public void testExceptionWhenFalse() throws Exception
    {

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XX__X, XXX_X_)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XX__X, XXX_X_,
                XX__X)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XX__X, XXX_X_,
                XX__X, XX_X_)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XXX__X_, XX_X_)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XXX__X_, XX_X_, XXX__X_)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(StateTestUtils.states(XXX__X_, XX_X_, XXX__X_, XXX_X_)))
              .isInstanceOf(NumObjectsException.class);

    }

    @Test
    public void validateAllStatesConnect() throws Exception
    {
        StateValidationUtils.validateAllStatesConnect(StateTestUtils.states(XXX__), thros(getUnchecked(3)));
        StateValidationUtils.validateAllStatesConnect(StateTestUtils.states(XXX__, XX_X_), thros(getUnchecked(4), getUnchecked(2)));
    }

    @Test
    public void validateAllStatesConnectThrowsException() throws Exception
    {
        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesConnect(StateTestUtils.states(_XXX_), thros(getUnchecked(3))))
            .isInstanceOf(BadThrowException.class);
        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesConnect(StateTestUtils.states(XXX__, XXX__X_), thros(getUnchecked(3), getUnchecked(2))))
            .isInstanceOf(TransitionException.class);
    }

}