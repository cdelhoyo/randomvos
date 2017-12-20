package com.autentia.randomvos;

import com.autentia.randomvos.example.Company;
import com.autentia.randomvos.example.CompanyType;
import com.autentia.randomvos.internal.FieldInstance;
import com.autentia.randomvos.randomizer.EnumRandomizer;
import com.autentia.randomvos.randomizer.ListRandomizer;
import com.autentia.randomvos.randomizer.Randomizer;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class RandomizerRegistryTest {

    private final ExtendedRandomSettings settings = mock(ExtendedRandomSettings.class);
    private final ExtendedRandom random = mock(ExtendedRandom.class);

    private final RandomizerRegistry sut = new RandomizerRegistry();

    @Before
    public void setupTest() {
        sut.init(settings, random);
    }

    @Test
    public void returnEnumRandomizer() {
        doReturn(0).when(random).nextInt(anyInt());

        Randomizer<CompanyType> result = (Randomizer<CompanyType>) sut.get(CompanyType.class);

        assertThat(result, is(instanceOf(EnumRandomizer.class)));
        assertThat(result.nextRandomValue(), is(CompanyType.SME));
    }

    @Test
    public void returnListRandomizer() {
        doReturn(1).when(random).nextInt(anyInt());

        Randomizer<List<?>> result = (Randomizer<List<?>>) sut.get(List.class);
        List<?> list = result.nextRandomValue();

        assertThat(result, is(instanceOf(ListRandomizer.class)));
        assertThat(list, hasSize(1));
    }

    @Test
    public void returnEnumFieldRandomizer() throws Exception {
        doReturn(0).when(random).nextInt(anyInt());
        FieldInstance field = new FieldInstance(Company.class.getDeclaredField("type"));

        Randomizer<CompanyType> result = (Randomizer<CompanyType>) sut.get(field);

        assertThat(result, is(instanceOf(EnumRandomizer.class)));
        assertThat(result.nextRandomValue(), is(CompanyType.SME));
    }

    @Test
    public void returnListFieldRandomizer() throws Exception {
        doReturn(1).when(random).nextInt(anyInt());
        FieldInstance field = new FieldInstance(Company.class.getDeclaredField("employees"));

        Randomizer<List<?>> result = (Randomizer<List<?>>) sut.get(field);
        List<?> list = result.nextRandomValue();

        assertThat(result, is(instanceOf(ListRandomizer.class)));
        assertThat(list, hasSize(1));
    }
}
