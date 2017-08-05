package com.ignoretheextraclub.siteswapfactory.siteswap.utils;

import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX__X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.array;
import static org.junit.Assert.fail;

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
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X, XX_X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X, XX_X_, XX__X));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X, XX_X_, XX__X, XX_X_));

        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_, XXX_X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_, XXX_X_, XXX__X_));
        StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XXX__X_, XXX_X_, XXX__X_, XXX_X_));
    }

    @Test
    public void testExceptionWhenFalse() throws Exception
    {

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X, XXX_X_)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X, XXX_X_,
                XX__X)))
              .isInstanceOf(NumObjectsException.class);

        softly.assertThatThrownBy(() -> StateValidationUtils.validateAllStatesHaveTheSameNumberOfObjects(array(XX__X, XXX_X_,
                XX__X, XX_X_)))
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