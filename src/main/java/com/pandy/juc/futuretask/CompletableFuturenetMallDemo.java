package com.pandy.juc.futuretask;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Pandy
 * @create: 2021/12/10
 **/
public class CompletableFuturenetMallDemo {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("padd"),
            new NetMall("tm"),
            new NetMall("tb")
    );

    public static List<String> getPriceNByStep(List<NetMall> list, String productName) {
        return list.stream()
                .map(netMall -> String.format(productName + " in %s price is %.2f",
                        netMall.getMallName(), netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceByAsync(List<NetMall> list, String productName) {
        return list.stream()
                .map(netMall -> CompletableFuture.supplyAsync(() ->
                    String.format(productName + "is %s price is %.2f", netMall.getMallName(),
                            netMall.calcPrice(productName))
                )).collect(Collectors.toList()).stream().map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Stream<NetMall> netMallStream = list.stream().map(s -> s);
        long startTime = System.currentTimeMillis();
        List<String> list1 = getPriceNByStep(list, "mysql");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("----costTime: "+(endTime - startTime) +" 毫秒");

        System.out.println();

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByAsync(list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("----costTime: "+(endTime2 - startTime2) +" 毫秒");
    }


}

class NetMall {
    private String mallName;

    public NetMall(String jd) {
        this.mallName = jd;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}