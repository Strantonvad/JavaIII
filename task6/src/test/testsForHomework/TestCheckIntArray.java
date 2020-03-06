package testsForHomework;

import homework.CheckIntArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestCheckIntArray {
    private boolean bool;
    private int[] inArray;
    private CheckIntArray checkIntArray;

    public TestCheckIntArray(boolean bool, int[] inArray) {
        this.bool = bool;
        this.inArray = inArray;
    }

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(
            new Object[][] {
                {true, new int[]{1, 1, 1, 4, 4, 1, 4, 4}},
                {false, new int[]{1, 1, 1, 1, 1}},
                {false, new int[]{4, 4, 4, 4}},
                {false, new int[]{1, 4, 4, 1, 1, 4, 3}}
            });
    }

    @Before
    public void init() {
        checkIntArray = new CheckIntArray();
    }

    @Test
    public void negativeTestCase1() {
        Assert.assertEquals(bool, checkIntArray.check(inArray));
    }
}