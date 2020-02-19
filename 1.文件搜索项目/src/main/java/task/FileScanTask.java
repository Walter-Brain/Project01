package task;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User:S-
 * Date:2020/1/5-9:06
 * Version: 1.0
 **/

public class FileScanTask {
    private final ExecutorService pool = Executors.newFixedThreadPool(4);
    //            new ThreadPoolExecutor();
    private static AtomicInteger COUNT = new AtomicInteger();
    private static final CountDownLatch LATCH = new CountDownLatch(1);

    private FileScanCallback callback;

    public FileScanTask(FileScanCallback callback) {
        this.callback = callback;
    }

    public void startScan(File root) {
        COUNT.incrementAndGet();

        pool.execute(new Runnable() {
            @Override
            public void run() {
                list(root);
            }
        });
    }

    public void waitFinish() throws InterruptedException {
        try {
            LATCH.await();
        } finally {
            //中断所有线程
            pool.shutdown();//调用每个线程的interrupt（）中断
//            POOL.shutdownNow();//调用每个线程的stop()关闭
        }
    }


    private void list(File dir) {
        if (!Thread.interrupted()) {
            try {
                callback.execute(dir);
//                System.out.println(dir.getPath());
                if (dir.isDirectory()) {
                    File[] children = dir.listFiles();
                    if (children != null && children.length > 0) {
                        for (File child : children) {
                            //启动子线程执行子文件夹的扫描任务
                            if (child.isDirectory()) {
                                COUNT.incrementAndGet();
                                pool.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        list(child);
                                    }
                                });
                            } else {
                                callback.execute(child);
                            }
                        }
                    }
                }
            } finally {
//            synchronized (POOL) {
//                COUNT--;
//                if (COUNT == 0) {
//                    POOL.notifyAll();
                if (COUNT.decrementAndGet() == 0) {
                    LATCH.countDown();
                }
            }
            System.out.println(dir.getPath());
        }
    }

//    public static void main(String[] args) throws InterruptedException {
//        FileScanTask task = new FileScanTask();
//        task.startScan(new File("F:\\比特课件"));
//
//        synchronized (task) {
//            task.wait();
//        }
//        System.out.println("执行完毕");
//    }
}
