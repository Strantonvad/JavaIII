public class ABCClass {
    //  Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок –
    // ABСABСABС). Используйте wait/notify/notifyAll.

    static class Lock {
        volatile int lock = 1;
    }

    public static void main(String[] args) {
        int iterations = 5;
        Lock lock = new Lock();

        Thread t1 =
            new Thread(
                () -> {
                    synchronized (lock) {
                        for (int i = 0; i < iterations; i++) {
                            while (lock.lock != 1) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.print("A");
                            lock.lock = 2;
                            lock.notifyAll();
                        }
                    }
                });
        Thread t2 =
            new Thread(
                () -> {
                    synchronized (lock) {
                        for (int i = 0; i < iterations; i++) {
                            while (lock.lock != 2) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.print("B");
                            lock.lock = 3;
                            lock.notifyAll();
                        }
                    }
                });
        Thread t3 =
            new Thread(
                () -> {
                    synchronized (lock) {
                        for (int i = 0; i < iterations; i++) {
                            while (lock.lock != 3) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.print("C");
                            lock.lock = 1;
                            lock.notifyAll();
                        }
                    }
                });

        t1.start();
        t2.start();
        t3.start();
    }
}
