package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;

import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 07/10/17.
 */
public class VanillaStateBuilderTest
{
    @Test
    public void testABadThrowCannotBeThrown() throws Exception
    {
        final VanillaStateBuilder builder = new VanillaStateBuilder(2, 2);

        builder.thenThrow(get(2));

        assertThatThrownBy(() -> builder.thenThrow(get(1)))
            .isInstanceOf(BadThrowException.class)
            .hasMessageContaining("[1]");
    }

    @Test
    public void testThatAThrowLargerThanMaxThrowCannotBeThrown() throws Exception
    {
        final VanillaStateBuilder builder = new VanillaStateBuilder(2, 2);

        builder.thenThrow(get(2));

        assertThatThrownBy(() -> builder.thenThrow(get(5)))
            .isInstanceOf(BadThrowException.class)
            .hasMessageContaining("[5]");
    }

    @Test
    public void testWhenGivenTooManyObjectsThrowsException() throws Exception
    {
        final VanillaStateBuilder builder = new VanillaStateBuilder(5, 3);

        builder.thenThrow(get(4));
        builder.thenThrow(get(4));
        builder.thenThrow(get(4));

        assertThatThrownBy(() -> builder.thenThrow(get(4)))
            .isInstanceOf(NumObjectsException.class)
            .hasMessageContaining("3"); // the expected number of objects

    }

    @Test
    public void testWhenThrowingBallAlreadyThrownHandlesItCorrectly() throws Exception
    {
        final VanillaStateBuilder builder = new VanillaStateBuilder(5, 1);

        builder.thenThrow(get(1));
        builder.thenThrow(get(1));
        builder.thenThrow(get(1));
        builder.thenThrow(get(1));
        builder.thenThrow(get(1));

        assertThat(builder.getGivenObjects()).isEqualTo(1);
        assertThat(builder.getState()).isEqualTo(new VanillaState(new boolean[]{true, false, false, false, false}));

    }
}