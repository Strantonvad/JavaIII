package testsForHomework;

import homework.CreateArrayAfter4;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestGetArrayAfter4 {
    private Integer[] inArray;
    private Integer[] expectedArray;
    private CreateArrayAfter4 createArrayAfter4;

    public TestGetArrayAfter4(Integer[] inArray, Integer[] expectedArray) {
        this.inArray = inArray;
        this.expectedArray = expectedArray;
    }

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Integer[][][]{
            {{1, 2, 4, 4, 2, 3, 4, 1, 7}, {1, 7}},
            {{6, 0, 5, 4, 2, 8, 5, 0, 9}, {2, 8, 5, 0, 9}},
            {{5, 4, 6, 8, 9, 0, 4, 2, 6, 0, 3}, {2, 6, 0, 3}},
            {{3, 1, 4, 6, 9, 0, 7, 2, 1}, {6, 9, 0, 7, 2, 1}},
            {{2, 3, 8, 9, 1, 0, 2, 1, 4}, {}}
        });
    }

    @Before
    public void init() {
        createArrayAfter4 = new CreateArrayAfter4();
    }

    @Test
    public void testArray() {
        Assert.assertArrayEquals(expectedArray, createArrayAfter4.getArrayAfter4(inArray));
    }

    @Test(expected = RuntimeException.class)
    public void testRuntimeException() {
        Integer[] array = {2, 3, 8, 9, 1, 0, 2, 1};
        Integer[] expectedArray = {};
        Assert.assertArrayEquals(expectedArray, createArrayAfter4.getArrayAfter4(array));
    }

}