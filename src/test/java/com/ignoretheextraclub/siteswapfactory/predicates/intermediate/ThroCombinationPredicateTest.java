/*
 * Copyright 2018 Caspar Nonclercq or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import java.util.Arrays;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StartingStateAndThrosToGeneralPathConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.compound.StringToVanillaThrosConverter;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXX___;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro.get;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ThroCombinationPredicateTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testThatFirstAndLastElementsMustNotBeNull() throws Exception
    {
        softly.assertThatThrownBy(() -> new ThroCombinationPredicate(null, get(3)))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new ThroCombinationPredicate(get(3), null))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new ThroCombinationPredicate((VanillaThro) null))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new ThroCombinationPredicate(null, get(3), get(5), get(1)))
            .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new ThroCombinationPredicate(get(3), get(5), get(1), null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Parameters
    public void testSequenceSimple(final State start, final String sequence, final Thro[] predicate, final boolean contains)
    {
        final VanillaThro[] thros = StringToVanillaThrosConverter.convert(sequence);
        final GeneralPath states = StartingStateAndThrosToGeneralPathConverter.getSequence(start, thros);

        final ThroCombinationPredicate throCombinationPredicate = new ThroCombinationPredicate(predicate);

        final String description = new StringBuilder().append("Sequence: ").append(sequence)
            .append(". Converted to states: ").append(Arrays.toString(states.getStates()))
            .append(". Predicate: ").append(predicate)
            .append(". Expected: ").append(contains)
            .toString();

        assertThat(throCombinationPredicate.test(states)).as(description).isEqualTo(contains);
        assertThat(throCombinationPredicate.negate().test(states)).isNotEqualTo(contains);
    }

    public Object parametersForTestSequenceSimple()
    {
        return new Object[]{
            new Object[]{XXX__, "33", new Thro[]{get(3)}, true},
            new Object[]{XXX__, "33", new Thro[]{get(5)}, false},
            new Object[]{XXX__, "42", new Thro[]{get(4)}, true},
            new Object[]{XXX__, "42", new Thro[]{get(4)}, true},
            new Object[]{XXX__, "33342333", new Thro[]{get(4)}, true},
            new Object[]{XXX__, "42", new Thro[]{get(5)}, false},
            new Object[]{XXX__, "33342333", new Thro[]{get(2)}, true},
            new Object[]{XXX__, "33342333", new Thro[]{get(4), get(2)}, true},
            new Object[]{XXX__, "33342333", new Thro[]{get(4), get(3), get(2)}, false},
            new Object[]{XXX__, "33342333", new Thro[]{get(4), null, get(2)}, false},
            new Object[]{XXX__, "33342333", new Thro[]{get(4), null, get(3)}, true},
            new Object[]{XXX__, "33342333", new Thro[]{get(4), get(2), get(3)}, true},
            new Object[]{XXX__, "33342333", new Thro[]{get(3), get(4), get(2), get(3)}, true},
            new Object[]{XXXXXXX__, "975", new Thro[]{get(5)}, true},
            new Object[]{XXXXXXX__, "975", new Thro[]{get(9), null, get(5)}, true},
            new Object[]{XXXXXXX__, "9999999007777777779757777", new Thro[]{get(9), null, get(5)}, true},
            new Object[]{XXXXXXX___, "999999900777777777A7747777", new Thro[]{get(9), null, get(5)}, false},
            new Object[]{XXXXXXX___, "999999900777777777A7747777", new Thro[]{get(10), null, null, get(4)}, true},
            new Object[]{XXXXXXX___, "999999900777777777A7747777", new Thro[]{get(10), get(7), null, get(4)}, true},
            new Object[]{XXXXXXX___, "999999900777777777A7747777", new Thro[]{get(10), get(6), null, get(4)}, false},
            new Object[]{XXXXXXX___, "999999900777777777A7747777", new Thro[]{get(10), null, get(7), get(4)}, true},
            new Object[]{XXXXXXX___, "999999900777777777A7747777", new Thro[]{get(10), null, get(7), get(5)}, false},
            // TODO create more
        };
    }


}