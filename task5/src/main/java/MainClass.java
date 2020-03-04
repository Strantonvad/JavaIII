import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {

    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Runnable action = () -> System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT, action);
        Semaphore smp = new Semaphore(CARS_COUNT / 2);

        Race race = new Race(new Road(60), new Tunnel(smp), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        Thread[] threads = new Thread[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cb);
        }

        for (int i = 0; i < cars.length; i++) {
            threads[i] = new Thread(cars[i]);
            threads[i].start();
        }

        for (int i = 0; i < cars.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}