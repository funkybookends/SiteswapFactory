package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.array;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.state;
import static org.junit.Assert.fail;

/**
 Created by caspar on 29/07/17.
 */
public class StateValidationUtilsTest
{
    private static final VanillaState XXX__ = state(true, true, true, false, false);
    private static final VanillaState XX_X_ = state(true, true, false, true, false);
    private static final VanillaState XX__X_ = state(true, true, false, false, true);
    private static final VanillaState ____X = state(false, false, false, false, true);

    private static final VanillaState XXXX__ = state(true, true, true, true, false, false);
    private static final VanillaState XXX_X_ = state(true, true, true, false, true, false);
    private static final VanillaState XXX__X_ = state(true, true, true, false, false, true);
    private static final VanillaState X____X = state(true, false, false, false, false, true);

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testNoExceptionWhenTrue() throws Exception
    {
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X_, XX_X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X_, XX_X_, XX__X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X_, XX_X_, XX__X_, XX_X_));

        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_, XXX_X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_, XXX_X_, XXX__X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_, XXX_X_, XXX__X_, XXX_X_));
    }

    @Test
    public void testExceptionWhenFalse() throws Exception
    {

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X_, XXX_X_)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X_, XXX_X_, XX__X_)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X_, XXX_X_, XX__X_, XX_X_)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_, XX_X_)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_, XX_X_, XXX__X_)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_, XX_X_, XXX__X_, XXX_X_)))
              .isInstanceOf(NumObjectsException.class);

    }

    @Test
    @Ignore("Not implemented yet")
    public void validateAllStatesConnect() throws Exception
    {
        fail("Test not yet implemented");
    }
}