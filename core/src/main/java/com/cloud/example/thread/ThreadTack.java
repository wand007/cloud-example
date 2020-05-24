package com.cloud.example.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/*
 * @author ; lidongdong
 * @Description
 * @Date 2018/9/20
 */
public class ThreadTack extends Thread {

    public static void main(String[] args) {
        threadPoolExecutor();
    }


    /**
     * 1、JDK 自带线程池
     * 线程池类为 java.util.concurrent.ThreadPoolExecutor，常用构造方法为：
     * <p>
     * ThreadPoolExecutor(intcorePoolSize, intmaximumPoolSize,
     * <p>
     *                                     longkeepAliveTime, TimeUnitunit,
     * <p>
     *                                     BlockingQueue<Runnable>workQueue,
     * <p>
     *                                        RejectedExecutionHandlerhandler)
     * <p>
     *  
     * <p>
     * corePoolSize：线程池维护线程的最少数量
     * <p>
     * maximumPoolSize：线程池维护线程的最大数量
     * <p>
     * keepAliveTime：线程池维护线程所允许的空闲时间
     * <p>
     * unit：线程池维护线程所允许的空闲时间的单位
     * <p>
     * workQueue：线程池所使用的缓冲队列
     * <p>
     * handler：线程池对拒绝任务的处理策略
     * <p>
     * <p>
     * 2、当一个任务通过execute(Runnable)方法欲添加到线程池时：
     * <p>
     * l  如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
     * <p>
     * l  如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
     * <p>
     * l  如果此时线程池中的数量大于等于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。
     * <p>
     * l  如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。
     * <p>
     * 也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
     * 一个线程池最多能同时处理的任务数量等于maximumPoolSize + workQueue之和
     * 一个线程池中最多能同时存在的线程数量等于maximumPoolSize
     * <p>
     * l  当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
     */
    public static void threadPoolExecutor() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 24, 200, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(6)
                , new ThreadFactoryBuilder().setNameFormat("threadPoolExecutor-example-tools-%d").build());
        for (int i = 0; i < 24; i++) {
            final int num = i;
            pool.execute(() -> {
                System.out.println("正在执行任务：" + num);
                try {
                    Thread.sleep(300000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务" + num + "执行完毕");
            });
            System.out.println("线程池中线程数目：" + pool.getPoolSize() + "，队列中等待执行的任务数目：" +
                    pool.getQueue().size() + "，已执行完别的任务数目：" + pool.getCompletedTaskCount());
        }
    }

    /**
     * <p>
     * 一、ThreadPoolExecutor的重要参数
     * 1、corePoolSize：核心线程数
     * * 核心线程会一直存活，及时没有任务需要执行
     * * 当线程数小于核心线程数时，即使有线程空闲，线程池也会优先创建新线程处理
     * * 设置allowCoreThreadTimeout=true（默认false）时，核心线程会超时关闭
     * <p>
     * 2、queueCapacity：任务队列容量（阻塞队列）
     * * 当核心线程数达到最大时，新任务会放在队列中排队等待执行
     * <p>
     * 3、maxPoolSize：最大线程数
     * * 当线程数>=corePoolSize，且任务队列已满时。线程池会创建新线程来处理任务
     * * 当线程数=maxPoolSize，且任务队列已满时，线程池会拒绝处理任务而抛出异常
     * <p>
     * 4、 keepAliveTime：线程空闲时间
     * * 当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize
     * * 如果allowCoreThreadTimeout=true，则会直到线程数量=0
     * <p>
     * 5、allowCoreThreadTimeout：允许核心线程超时
     * 6、rejectedExecutionHandler：任务拒绝处理器
     * * 两种情况会拒绝处理任务：
     * - 当线程数已经达到maxPoolSize，切队列已满，会拒绝新任务
     * - 当线程池被调用shutdown()后，会等待线程池里的任务执行完毕，再shutdown。如果在调用shutdown()和线程池真正shutdown之间提交任务，会拒绝新任务
     * * 线程池会调用rejectedExecutionHandler来处理这个任务。如果没有设置默认是AbortPolicy，会抛出异常
     * * ThreadPoolExecutor类有几个内部实现类来处理这类情况：
     * - AbortPolicy 丢弃任务，抛运行时异常
     * - CallerRunsPolicy 执行任务
     * - DiscardPolicy 忽视，什么都不会发生
     * - DiscardOldestPolicy 从队列中踢出最先进入队列（最后一个执行）的任务
     * * 实现RejectedExecutionHandler接口，可自定义处理器
     * <p>
     * 二、ThreadPoolExecutor执行顺序
     *  
     * <p>
     *   如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
     * <p>
     *   如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
     * <p>
     *   如果此时线程池中的数量大于等于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。
     * <p>
     *   如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。
     * <p>
     * 也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
     * <p>
     *   当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
     * <p>
     * 三、如何设置参数
     * 1、默认值
     * * corePoolSize=1
     * * queueCapacity=Integer.MAX_VALUE
     * * maxPoolSize=Integer.MAX_VALUE
     * * keepAliveTime=60s
     * * allowCoreThreadTimeout=false
     * * rejectedExecutionHandler=AbortPolicy()
     * <p>
     * 2、如何来设置
     * * 需要根据几个值来决定
     * - tasks ：每秒的任务数，假设为500~1000
     * - taskcost：每个任务花费时间，假设为0.1s
     * - responsetime：系统允许容忍的最大响应时间，假设为1s
     * * 做几个计算
     * - corePoolSize = 每秒需要多少个线程处理？
     * * threadcount = tasks/(1/taskcost) =tasks*taskcout =  (500~1000)*0.1 = 50~100 个线程。corePoolSize设置应该大于50
     * * 根据8020原则，如果80%的每秒任务数小于800，那么corePoolSize设置为80即可
     * - queueCapacity = (coreSizePool/taskcost)*responsetime
     * * 计算可得 queueCapacity = 80/0.1*1 = 80。意思是队列里的线程可以等待1s，超过了的需要新开线程来执行
     * * 切记不能设置为Integer.MAX_VALUE，这样队列会很大，线程数只会保持在corePoolSize大小，当任务陡增时，不能新开线程来执行，响应时间会随之陡增。
     * - maxPoolSize = (max(tasks)- queueCapacity)/(1/taskcost)
     * * 计算可得 maxPoolSize = (1000-80)/10 = 92
     * * （最大任务数-队列容量）/每个线程每秒处理能力 = 最大线程数
     * - rejectedExecutionHandler：根据具体情况来决定，任务不重要可丢弃，任务重要则要利用一些缓冲机制来处理
     * - keepAliveTime和allowCoreThreadTimeout采用默认通常能满足
     * <p>
     */
    public static void threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        //线程池所使用的缓冲队列
        poolTaskExecutor.setQueueCapacity(200);
        //线程池维护线程的最少数量
        poolTaskExecutor.setCorePoolSize(5);
        //线程池维护线程的最大数量
        poolTaskExecutor.setMaxPoolSize(1000);
        //线程池维护线程所允许的空闲时间
        poolTaskExecutor.setKeepAliveSeconds(30000);
        poolTaskExecutor.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("threadPoolTaskExecutor-example-tools-%d").build());
        poolTaskExecutor.initialize();

        for (int i = 0; i < 14; i++) {
            final int num = i;
            poolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("正在执行任务：" + num);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务" + num + "执行完毕");
                }
            });
            ThreadPoolExecutor threadPoolExecutor = poolTaskExecutor.getThreadPoolExecutor();
            System.out.println("线程池中线程数目：" + threadPoolExecutor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    threadPoolExecutor.getQueue().size() + "，已执行完别的任务数目：" + threadPoolExecutor.getCompletedTaskCount());
        }
    }


    /**
     * newCachedThreadPool 可以无限的新建线程，容易造成堆外内存溢出
     */
    static void newCachedThreadPool() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("newCachedThreadPool-example-tools-%d").build());
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
     * <p>
     * 因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字。
     * 其实newFixedThreadPool()在严格上说并不会复用线程，每运行一个Runnable都会通过ThreadFactory创建一个线程。
     */
    static void newFixedThreadPool() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3, new ThreadFactoryBuilder().setNameFormat("newFixedThreadPool-example-tools-%d").build());
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
     * <p>
     * ScheduledThreadPoolExecutor的设计思想是，每一个被调度的任务都会由线程池中一个线程去执行，因此任务是并发执行的，相互之间不会受到干扰。
     * 需要注意的是，只有当任务的执行时间到来时，ScheduedExecutor 才会真正启动一个线程，其余时间 ScheduledExecutor 都是在轮询任务的状态。
     */
    static void newScheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5, new ThreadFactoryBuilder().setNameFormat("newScheduledThreadPool-example-tools-%d").build());
        for (int i = 0; i < 10; i++) {
            final int index = i;

            scheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("delay 3 seconds" + index);

                }
            }, 3, TimeUnit.SECONDS);
        }
    }

    /**
     * 表示延迟1秒后每3秒执行一次。
     */
    static void newAtFixedRate() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5, new ThreadFactoryBuilder().setNameFormat("newAtFixedRate-example-tools-%d").build());
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
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("newSingleThreadExecutor-example-tools-%d").build());

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


}
