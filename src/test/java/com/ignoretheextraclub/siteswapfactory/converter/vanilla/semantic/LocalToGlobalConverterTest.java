package com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic;

import com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by caspar on 14/09/17.
 */
public class LocalToGlobalConverterTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void localToGlobal() throws Exception
    {
        final LocalToGlobalConverter.IntConverter conveter = LocalToGlobalConverter.IntConverter.get();

        softly.assertThat(conveter.apply(StateTestUtils.thros(9, 5, 7)))
              .isEqualTo(StateTestUtils.thros(9, 7, 5));

        softly.assertThat(conveter.apply(StateTestUtils.thros(7, 9, 6, 8, 10)))
              .isEqualTo(StateTestUtils.thros(7, 8, 9, 10, 6));

        softly.assertThat(conveter.apply(StateTestUtils.thros(7, 6, 6, 6, 8, 8, 8)))
              .isEqualTo(StateTestUtils.thros(7, 8, 6, 8, 6, 8, 6));

        softly.assertThat(conveter.apply(StateTestUtils.thros(8, 8, 8, 7, 6, 6, 6)))
              .isEqualTo(StateTestUtils.thros(8, 6, 8, 6, 8, 6, 7));
    }
}