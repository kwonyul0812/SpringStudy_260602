import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestExecutorService {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        Long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            final int n = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("번호 : " + n);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        Long elapsed = System.currentTimeMillis() - start;
        System.out.println("총 흐른 시간 : " + elapsed + "ms");
    }
}
