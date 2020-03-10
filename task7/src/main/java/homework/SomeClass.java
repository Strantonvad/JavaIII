package homework;

public class SomeClass {

    @BeforeSuite
    public void before() {
        System.out.println("Method \"BEFORE\" was called.");
    }

    @AfterSuite
    public void after() {
        System.out.println("Method \"AFTER\" was called.");
    }

    @Test
    public void calcWithDefaultPriority() {
        System.out.println("Method \"calcWithDefaultPriority\" called!");
    }

    @Test(priority = 7)
    public void oneMoreCalcMethod() {
        System.out.println("Method \"oneMoreCalcMethod\" called!");
    }

    @Test(priority = 5)
    public void calc() {
        System.out.println("Method \"calc\" called!");
    }
}
