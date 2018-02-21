package pizza;/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.junit.Test;

import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static pizza.PizzaParser.possibleSlices;
import static pizza.PizzaParser.toppingBases;

public class AppTest {

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
            boolean result =
                    ps.isValid(0, 0, 2, 1) &&
                            ps.isValid(0, 2, 2, 2) &&
                            ps.isValid(0, 3, 2, 4);
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
            Set<Slice> legalSlices = new HashSet<>();
            Slice slice1 = new Slice.Builder()
                    .withUpperLeft(0, 0)
                    .withLowerRight(2, 1)
                    .build();
            Slice slice2 = new Slice.Builder()
                    .withUpperLeft(0, 2)
                    .withLowerRight(2, 2)
                    .build();
            Slice slice3 = new Slice.Builder()
                    .withUpperLeft(0, 3)
                    .withLowerRight(2, 4)
                    .build();

            legalSlices.add(slice1);
            legalSlices.add(slice2);
            legalSlices.add(slice3);

            PizzaSlicer ps = PizzaSlicer.create(pizza);
            Set<Slice> slices = ps.slicePizza(toppingBases(pizza), possibleSlices(pizza.H));
            Set<Slice> maxArea = ps.withMaxArea(legalSlices);

            assertTrue(slices.containsAll(legalSlices));
            assertEquals(legalSlices, maxArea);
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
                    PizzaParser.toppingBases(pizza).toArray());
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
                    PizzaParser.toppingBases(pizza).toArray());
        });
    }

    @Test
    public void testIntersections() {
        Slice slice1 = new Slice.Builder()
                .withUpperLeft(1, 1)
                .withLowerRight(3, 4)
                .build();

        Slice slice2 = new Slice.Builder()
                .withUpperLeft(1, 1)
                .withLowerRight(3, 4)
                .build();
        assertTrue(slice1.intersects(slice2));

        Slice slice3 = new Slice.Builder()
                .withUpperLeft(2, 1)
                .withLowerRight(4, 3)
                .build();
        assertTrue(slice1.intersects(slice3));

        Slice slice4 = new Slice.Builder()
                .withUpperLeft(3, 1)
                .withLowerRight(5, 4)
                .build();

        assertTrue(slice1.intersects(slice4));

        Slice slice5 = new Slice.Builder()
                .withUpperLeft(0, 0)
                .withLowerRight(3, 4)
                .build();
        assertTrue(slice1.intersects(slice5));

        Slice slice6 = new Slice.Builder()
                .withUpperLeft(1, 3)
                .withLowerRight(1, 4)
                .build();
        Slice slice7 = new Slice.Builder()
                .withUpperLeft(0, 2)
                .withLowerRight(2, 3)
                .build();
        assertTrue(slice6.intersects(slice7));

        Slice slice8 = new Slice.Builder()
                .withUpperLeft(0, 3)
                .withLowerRight(2, 4)
                .build();
        Slice slice9 = new Slice.Builder()
                .withUpperLeft(0, 2)
                .withLowerRight(2, 2)
                .build();
        Slice slice10 = new Slice.Builder()
                .withUpperLeft(0, 0)
                .withLowerRight(2, 1)
                .build();

        assertTrue(!slice8.intersects(slice9) &&
                !slice8.intersects(slice10) &&
                !slice9.intersects(slice10));


    }
}
