package com.pandy.juc.thread.selldemo;

import java.util.concurrent.TimeUnit;

/**
 * @author: Pandy
 * @create: 2021/12/7
 **/
public class SellTicket implements Runnable{

    private int tickets = 100;

    @Override
    public void run() {
        while (true) {
            if (tickets > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "正在出售第" + tickets--+ "张票");
            }
        }
    }
}
