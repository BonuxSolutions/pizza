package pizza;/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.junit.Test;

import java.net.URL;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }

    @Test
    public void testFileUtils() {
        Optional<Pizza> maybePizza =
                Optional
                        .ofNullable(getClass().getClassLoader().getResource("example.in"))
                        .map(URL::getFile)
                        .map(FileUtils::readFile)
                        .map(PizzaParser::parseFromArray);

        String result = "3 5 1 6\n" +
                "TTTTT\n" +
                "TMMMT\n" +
                "TTTTT";

        assertThat(maybePizza.isPresent(), is(true));
        maybePizza.ifPresent(pizza -> assertThat(pizza.toString(), is(result)));
    }

    @Test
    public void testResultFormat() {
        Result result =
                new Result.Builder()
                        .withSlice(Result.Slice.create(Result.Coord.create(0, 0), Result.Coord.create(2, 1)))
                        .withSlice(Result.Slice.create(Result.Coord.create(0, 2), Result.Coord.create(2, 2)))
                        .withSlice(Result.Slice.create(Result.Coord.create(0, 3), Result.Coord.create(2, 4)))
                        .build();

        String expected = "3\n0 0 2 1\n0 2 2 2\n0 3 2 4";

        assertEquals(expected, result.toString());
    }

    @Test
    public void testValid() {
        Optional<Pizza> maybePizza =
                Optional
                        .ofNullable(getClass().getClassLoader().getResource("example.in"))
                        .map(URL::getFile)
                        .map(FileUtils::readFile)
                        .map(PizzaParser::parseFromArray);
        maybePizza.ifPresent(pizza -> {
            PizzaSlicer ps = PizzaSlicer.create(pizza);
            PizzaSlicer.SlicesPerIteration slicesPerIteration = ps.new SlicesPerIteration();
            boolean result =
                    ps.isValid(0, 0, 2, 1, slicesPerIteration) &&
                            ps.isValid(0, 2, 2, 2, slicesPerIteration) &&
                            ps.isValid(0, 3, 2, 4, slicesPerIteration);
            assertEquals(true, result);
        });
    }

    @Test
    public void testPizzaSlicer() {
        Optional<Pizza> maybePizza =
                Optional
                        .ofNullable(getClass().getClassLoader().getResource("example.in"))
                        .map(URL::getFile)
                        .map(FileUtils::readFile)
                        .map(PizzaParser::parseFromArray);

        maybePizza.ifPresent(pizza -> {
            PizzaSlicer ps = PizzaSlicer.create(pizza);
            ps.nextValidSlice();
            assertEquals(1, ps.currentState.size());
            assertEquals(1, ps.currentState.get(0).currentSliceNumber);
        });
    }

    @Test
    public void testGetMinToppings() {
        Optional<Pizza> maybePizza =
                Optional
                        .ofNullable(getClass().getClassLoader().getResource("example.in"))
                        .map(URL::getFile)
                        .map(FileUtils::readFile)
                        .map(PizzaParser::parseFromArray);

        maybePizza.ifPresent(pizza -> {
            assertArrayEquals(
                    new SliceBase[]{
                            SliceBase.create(1, 1),
                            SliceBase.create(1, 2),
                            SliceBase.create(1, 3)
                    },
                    FunctionsAndConstants.minToppings(pizza).toArray());
        });
    }

    @Test
    public void testGetMinToppings2() {
        Optional<Pizza> maybePizza =
                Optional
                        .ofNullable(getClass().getClassLoader().getResource("example2.in"))
                        .map(URL::getFile)
                        .map(FileUtils::readFile)
                        .map(PizzaParser::parseFromArray);

        assertThat(maybePizza.isPresent(), is(true));
        maybePizza.ifPresent(pizza -> {
            assertArrayEquals(
                    new SliceBase[]{
                            SliceBase.create(0, 0),
                            SliceBase.create(0, 1),
                            SliceBase.create(0, 2),
                            SliceBase.create(0, 3),
                            SliceBase.create(0, 4),
                            SliceBase.create(1, 0),
                            SliceBase.create(1, 4)
                    },
                    FunctionsAndConstants.minToppings(pizza).toArray());
        });
    }
}
