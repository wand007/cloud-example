package com.cloud.example.thread;

import java.util.concurrent.*;

/*
 * @author ; lidongdong
 * @Description 
 * @Date 2018/9/20
 */
public class ThreadTack extends Thread{


    //写一个类执行十次随机时间休眠
    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                int time = (int) (Math.random() * 1000);
                Thread.sleep(time);
                System.out.println("当前线程是  " + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建基础线程
     */
    static void newThread() {
        try {
            ThreadTack thread = new ThreadTack();
            thread.setName("new Thread");
            thread.start();
            for (int i = 0; i < 10; i++) {
                final int index = i;
                int time = (int) (Math.random() * 1000);
                Thread.sleep(time);
                System.out.println("当前线程是  " + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * newCachedThreadPool 可以无线的新建线程，容易造成堆外内存溢出
     */
    static void newCachedThreadPool() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        for (int i = 0; i < 10; i++) {

            final int index = i;

            try {

                Thread.sleep(index * 1000);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {

                    System.out.println(index);

                }

            });

        }
    }

    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
     *
     * 因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字。
     * 其实newFixedThreadPool()在严格上说并不会复用线程，每运行一个Runnable都会通过ThreadFactory创建一个线程。
     */
    static void newFixedThreadPool() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        new ThreadPoolExecutor(2, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 10; i++) {

            final int index = i;

            fixedThreadPool.execute(new Runnable() {


                @Override
                public void run() {

                    try {

                        System.out.println(index);

                        Thread.sleep(2000);

                    } catch (InterruptedException e) {


                        e.printStackTrace();

                    }

                }

            });

        }
    }

    /**
     * 创建一个定长线程池，支持定时及周期性任务执行
     *
     * ScheduledThreadPoolExecutor的设计思想是，每一个被调度的任务都会由线程池中一个线程去执行，因此任务是并发执行的，相互之间不会受到干扰。
     * 需要注意的是，只有当任务的执行时间到来时，ScheduedExecutor 才会真正启动一个线程，其余时间 ScheduledExecutor 都是在轮询任务的状态。
     */
    static void newScheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 10; i++) {

            final int index = i;

            scheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {

                    System.out.println("delay 3 seconds"+index);

                }

            }, 3, TimeUnit.SECONDS);
        }

    }

    /**
     * 表示延迟1秒后每3秒执行一次。
     */
    static void newAtFixedRate() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

//        new ThreadPoolExecutor(1, 1,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {


            @Override
            public void run() {

                System.out.println("delay 1 seconds, and excute every 3 seconds");

            }

        }, 1, 3, TimeUnit.SECONDS);
    }

    /**
     * 创建一个单线程化的线程池
     */
    static void newSingleThreadExecutor() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {

            final int index = i;

            singleThreadExecutor.execute(new Runnable() {


                @Override
                public void run() {

                    try {

                        System.out.println(index);

                        Thread.sleep(2000);

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }

                }

            });

        }
    }

    public static void main(String[] args) {
        newFixedThreadPool();
    }

}
