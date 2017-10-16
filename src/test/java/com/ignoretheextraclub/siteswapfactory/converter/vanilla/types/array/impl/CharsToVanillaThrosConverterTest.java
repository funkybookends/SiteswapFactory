package com.ignoretheextraclub.siteswapfactory.converter.vanilla.types.array.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.exceptions.BadThrowException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by caspar on 24/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class CharsToVanillaThrosConverterTest
{

    @Test
    @Parameters
    public void testApply(final char[] input, final VanillaThro[] expected) throws Exception
    {
        Assertions.assertThat(CharsToVanillaThrosConverter.get().apply(input)).isEqualTo(expected);
        assertThat(CharsToVanillaThrosConverter.convertToVanillaThros(input)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{
                new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'},
                new VanillaThro[]{
                    VanillaThro.get('0'), VanillaThro.get('1'),
                    VanillaThro.get('2'), VanillaThro.get('3'),
                    VanillaThro.get('4'), VanillaThro.get('5'),
                    VanillaThro.get('6'), VanillaThro.get('7'),
                    VanillaThro.get('8'), VanillaThro.get('9'),

                    VanillaThro.get('A'), VanillaThro.get('B'),
                    VanillaThro.get('C'), VanillaThro.get('D'),
                    VanillaThro.get('E'), VanillaThro.get('F'),
                    VanillaThro.get('G'), VanillaThro.get('H'),

                    VanillaThro.get('a'), VanillaThro.get('b'),
                    VanillaThro.get('c'), VanillaThro.get('d'),
                    VanillaThro.get('e'), VanillaThro.get('f'),
                    VanillaThro.get('g'), VanillaThro.get('h'),
                }
            },
            new Object[]{new char[]{}, new VanillaThro[]{}},
        };
        // TODO add more
    }

    @Test
    public void testException() throws Exception
    {
        assertThatThrownBy(() -> CharsToVanillaThrosConverter.get().apply(new char[]{'@'}))
            .isInstanceOf(BadThrowException.class)
            .hasMessageContaining("@");
    }
}