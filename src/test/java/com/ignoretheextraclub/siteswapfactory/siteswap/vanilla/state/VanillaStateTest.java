package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IteratorUtils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ignoretheextraclub.siteswapfactory.generator.state.VanillaStateGenerator;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXX___;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXX____;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_X___;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_X_X__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX___;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_XX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_X_X_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX__X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_XX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_XX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X_X_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X__XX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.X____X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XXXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._XX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils._X_XX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.__XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.state;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by caspar on 15/01/17.
 */
public class VanillaStateTest
{
	private static final Logger LOG = LoggerFactory.getLogger(VanillaStateTest.class);

	public static final VanillaThro _0 = get(0);
	public static final VanillaThro _1 = get(1);
	public static final VanillaThro _2 = get(2);
	public static final VanillaThro _3 = get(3);
	public static final VanillaThro _4 = get(4);
	public static final VanillaThro _5 = get(5);

	@Rule
	public JUnitSoftAssertions softly = new JUnitSoftAssertions();

	@Test
	public void canThrow() throws Exception
	{
		softly.assertThat(XXX__.canThrow()).isTrue();
		softly.assertThat(_XXX_.canThrow()).isFalse();
	}

	@Test
	public void getAvailableThrows() throws Exception
	{
		assertThat(IteratorUtils.toList(XXX__.getAvailableThrows()))
			.contains(_3, _4, _5)
			.doesNotContain(_0, _1, _2)
			.hasSize(27);

		assertThat(IteratorUtils.toList(_XXX_.getAvailableThrows()))
			.contains(_0)
			.doesNotContain(_1, _2, _3, _4, _5)
			.hasSize(1);
	}

	@Test
	public void getMaxThrow() throws Exception
	{
		softly.assertThat(XXX__.getMaxThrow()).isSameAs(get(VanillaThro.MAX_THROW));
		softly.assertThat(_XXX_.getMaxThrow()).isSameAs(get(VanillaThro.MAX_THROW));
	}

	@Test
	public void getNumObjects() throws Exception
	{
		softly.assertThat(XXX__.getNumObjects()).isEqualTo(3);
		softly.assertThat(_XXX_.getNumObjects()).isEqualTo(3);
	}

	@Test
	public void isGroundState() throws Exception
	{
		softly.assertThat(XXX__.isGroundState()).isTrue();
		softly.assertThat(_XXX_.isGroundState()).isFalse();
	}

	@Test
	public void getThrow() throws Exception
	{
		softly.assertThat(_XXX_.getThrow(XXX__)).isEqualTo(_0);
		softly.assertThat(XXX__.getThrow(XXX__)).isEqualTo(_3);
		softly.assertThat(XXX__.getThrow(XX_X_)).isEqualTo(_4);
		softly.assertThat(XX_X_.getThrow(XXX__)).isEqualTo(_2);
	}

	@Test
	public void automatedTestGetThro() throws Exception
	{
		VanillaStateGenerator.getAllStates(5, 8)
			.forEach(state -> state.getAvailableThrows()
				.forEachRemaining(thro -> softly.assertThat(state.getThrow(state.thro(thro))).isEqualTo(thro)));
	}

	@Test
	public void excitedness() throws Exception
	{
		softly.assertThat(XXX__.excitedness()).isLessThan(_XXX_.excitedness());
	}

	@Test
	public void getGroundState() throws Exception
	{
		softly.assertThat(VanillaStateGenerator.getGroundState(3)).isEqualTo(XXX__);
	}

	@Test
	public void testToString() throws Exception
	{
		Assert.assertEquals("XXX", XXX__.toString());
		Assert.assertEquals("_XXX", _XXX_.toString());
	}

	@Test
	public void equals() throws Exception
	{
		Assert.assertEquals(XXX__, XXX__);
		Assert.assertEquals(_XXX_, _XXX_);
		Assert.assertEquals(XXX__, state(true, true, true, false, false));
		Assert.assertEquals(_XXX_, state(false, true, true, true, false));

		Assert.assertNotEquals(XXX__, _XXX_);
		Assert.assertNotEquals(_XXX_, XXX__);
	}

	@Test
	public void testThro() throws Exception
	{
		softly.assertThat(_XXX_.thro(_0)).isEqualTo(XXX__);
		softly.assertThat(XXX__.thro(_3)).isEqualTo(XXX__);
		softly.assertThat(XXXXX_X.thro(get(5))).isEqualTo(XXXXXX);
	}

	@Test
	public void getNextStates() throws Exception
	{
		assertThat(IteratorUtils.toList(XXX__.getNextStates()))
			.contains(XXX__, XX_X_, XX__X)
			.hasSize(27);

		assertThat(IteratorUtils.toList(_XXX_.getNextStates()))
			.contains(XXX__)
			.hasSize(1);

		assertThat(IteratorUtils.toList(_XXX__.getNextStates()))
			.contains(XXX___)
			.hasSize(1);

		softly.assertThat(XXXXX_X.getNextStates()).contains(XXXXXX);
	}

	@Test
	public void canTransition() throws Exception
	{
		softly.assertThat(_XXX_.canTransition(XXX__)).isTrue();
		softly.assertThat(XXX__.canTransition(_XXX_)).isFalse();
		softly.assertThat(XX_X_.canTransition(__XXX)).isFalse();
		softly.assertThat(XX_X_.canTransition(X_XXX)).isFalse();

		softly.assertThat(XXXXX_X.canTransition(XXXXXX)).isTrue();
	}

	@Test
	public void testUndo() throws Exception
	{
		softly.assertThat(XX_X_.undo(get(4))).isEqualTo(XXX__);
		softly.assertThat(__XXX.undo(get(5))).isEqualTo(X__XX);
		softly.assertThat(XX_X_.undo(get(0))).isEqualTo(_XX_X);
	}

	@Test
	public void testEqualsMethod() throws Exception
	{
		Assert.assertTrue(state(true, false, false, true).equals(state(true, false, false, true)));
	}

	@Test
	public void testExcitedness() throws Exception
	{
		final List<VanillaState> collect = VanillaStateGenerator.getAllStates(3, 5)
			.sorted(Comparator.comparing(State::excitedness))
			.collect(Collectors.toList());

		assertThat(collect).containsExactly(
			XXX__,
			XX_X_,
			X_XX_,
			_XXX_,
			XX__X,
			X_X_X,
			_XX_X,
			X__XX,
			_X_XX,
			__XXX);
	}

	@Test
	public void testGetNumObjects() throws Exception
	{
		softly.assertThat(XXX__.getNumObjects()).isEqualTo(3);
		softly.assertThat(XXX_.getNumObjects()).isEqualTo(3);
		softly.assertThat(XX_X.getNumObjects()).isEqualTo(3);
		softly.assertThat(X_XX.getNumObjects()).isEqualTo(3);
		softly.assertThat(_XXX.getNumObjects()).isEqualTo(3);
		softly.assertThat(XXX__.getNumObjects()).isEqualTo(3);
		softly.assertThat(XX_X_.getNumObjects()).isEqualTo(3);
		softly.assertThat(XX__X.getNumObjects()).isEqualTo(3);
		softly.assertThat(X_XX_.getNumObjects()).isEqualTo(3);
		softly.assertThat(X_X_X.getNumObjects()).isEqualTo(3);
		softly.assertThat(X__XX.getNumObjects()).isEqualTo(3);
		softly.assertThat(_XXX_.getNumObjects()).isEqualTo(3);
		softly.assertThat(_XX_X.getNumObjects()).isEqualTo(3);
		softly.assertThat(_X_XX.getNumObjects()).isEqualTo(3);
		softly.assertThat(__XXX.getNumObjects()).isEqualTo(3);
		softly.assertThat(_XXX__.getNumObjects()).isEqualTo(3);
		softly.assertThat(XXX___.getNumObjects()).isEqualTo(3);

		softly.assertThat(XXX_X.getNumObjects()).isEqualTo(4);
		softly.assertThat(XXXX_.getNumObjects()).isEqualTo(4);
		softly.assertThat(XX_XX.getNumObjects()).isEqualTo(4);
		softly.assertThat(X_XXX.getNumObjects()).isEqualTo(4);
		softly.assertThat(_XXXX.getNumObjects()).isEqualTo(4);
		softly.assertThat(XXXX__.getNumObjects()).isEqualTo(4);
		softly.assertThat(X____X.getNumObjects()).isEqualTo(2);
		softly.assertThat(XXX_X_.getNumObjects()).isEqualTo(4);
		softly.assertThat(XXX__X.getNumObjects()).isEqualTo(4);
		softly.assertThat(XX_X_X_X_.getNumObjects()).isEqualTo(5);
		softly.assertThat(XXX_X_X__.getNumObjects()).isEqualTo(5);
		softly.assertThat(XXXX_X___.getNumObjects()).isEqualTo(5);
		softly.assertThat(XXXXX____.getNumObjects()).isEqualTo(5);
		softly.assertThat(XXXXXXX__.getNumObjects()).isEqualTo(7);
		softly.assertThat(XXXXXXX___.getNumObjects()).isEqualTo(7);
		softly.assertThat(XXXXXXXX__.getNumObjects()).isEqualTo(8);
	}
}