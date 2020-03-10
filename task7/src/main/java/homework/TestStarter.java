package homework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestStarter {
    public static void start(Object testClass) {
        Class<?> c = testClass.getClass();
        Method[] methods = c.getDeclaredMethods();

        // call @BeforeSuite methods
        invokeMethods(c, methods, BeforeSuite.class, false);

        // call @Test method
        invokeMethods(c, methods, Test.class, true);

        // call @AfterSuite methods
        invokeMethods(c, methods, AfterSuite.class, false);
    }

    private static void invokeMethods(
        Class<?> c, Method[] methods, Class<? extends Annotation> annotation, Boolean isTestCall) {
        for (Method method : methods) {
            if (method.getAnnotation(annotation) != null) {
                System.out.println(method.getAnnotation(annotation).annotationType().getSimpleName());
                try {
                    if (isTestCall) {
                        ArrayList<Method> methodList = new ArrayList<>();
                        System.out.println(method.getAnnotation(Test.class).priority());
                        methodList.add(method);

                        methodList.sort(
                            (o2, o1) ->
                                Integer.compare(
                                    o1.getAnnotation(Test.class).priority(),
                                    o2.getAnnotation(Test.class).priority()));

                        for (Method value : methodList) {
                            value.invoke(c.getDeclaredConstructor().newInstance());
                        }
                    } else {
                        method.invoke(c.getDeclaredConstructor().newInstance());
                    }
                } catch (IllegalAccessException
                    | InvocationTargetException
                    | NoSuchMethodException
                    | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SomeClass some = new SomeClass();
        start(some);
    }
}
