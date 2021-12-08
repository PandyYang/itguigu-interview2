package com.pandy.juc.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: Pandy
 * @create: 2021/12/7
 *
 *
 * Callable接口中的call方法和Runnable接口中的run方法的区别
 * 1.是否有返回值(Runnable接口没有返回值 Callable接口有返回值)
 * 2.是否抛异常(Runnable接口不会抛出异常 Callable接口会抛出异常)
 * 3.落地方法不一样,一个是call() ,一个是run()
 *
 * FutureTask是Future接口的唯一的实现类
 * FutureTask同时实现了Runnable、Future接口。它既可以作为Runnable被线程执行,又可以作为Futrue得到Callable的返回值
 **/
public class CallableDemo {
    public static void main(String[] args) {
        NumThread m = new NumThread();
        FutureTask futureTask = new FutureTask(m);
        new Thread(futureTask).start();
        try {
            Object o = futureTask.get();
            System.out.println("总和是: " + o);
        }catch (Exception e) {
            e.printStackTrace();
        }

        // get( )方法建议放在最后一行,防止线程阻塞(一旦调用了get( )方法,不管是否计算完成都会阻塞)
        // 一个FutureTask,多个线程调用call( )方法只会调用一次
        // 如果需要调用call方法多次,则需要多个FutureTask
        CallADemo a = new CallADemo();
        FutureTask<Integer> futureTaskB = new FutureTask<>(a);
        new Thread(futureTaskB, "线程A").start();
        new Thread(futureTaskB, "线程B").start();
        Integer integer = null;
        try {
            integer = futureTaskB.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("integer = " + integer);
    }
}

class NumThread implements Callable {
    @Override
    public Object call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            sum += i;
        }
        return sum;
    }
}

class CallADemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("调用call方法");
        return 6;
    }
}