package com.sengou.qihang.pagetmpl.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author maple
 * @date 2020/7/23
 */
@Component
public class ThreadUtils {

    private static final ExecutorService es = Executors.newFixedThreadPool(10);

    public static void execute(Runnable runnable){
        es.submit(runnable);
    }
}
