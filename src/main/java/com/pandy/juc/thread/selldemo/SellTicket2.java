package com.pandy.juc.thread.selldemo;

/**
 * @author: Pandy
 * @create: 2021/12/7
 **/
public class SellTicket2 implements Runnable {
    private int tickets = 100;
    private Object object = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (object) {
                if (tickets > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "正在出售第" + tickets-- + "张票");
            }
        }
    }
}
