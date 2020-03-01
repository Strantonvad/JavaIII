import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThread {
    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    static void execute(List<Runnable> tasks) {
        for (Runnable task : tasks) {
            executorService.submit(task);
        }
    }

    public static void main(String[] args) {
        Runnable[] tasks = new Runnable[30];
        for (int i = 0; i < 30; i++) {
            tasks[i] = new Runner(String.valueOf(i + 1));
        }
        execute(Arrays.asList(tasks));
        executorService.shutdown();
    }

    static class Runner implements Runnable {
        private final Object lock = new Object();

        private String msg;

        public Runner(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(msg);
            }
        }
    }
}