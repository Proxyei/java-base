package com.xywei.jdk8.juc.completablefutrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 场景： 小明吃饭完毕之后，到外面等公交车，如果1路到就坐1路，如果2路到就坐2路
 * <br/>
 * 互斥，那个线程先完成就使用哪个结果！
 * @author future
 *
 */
public class CompletableFutrueApplyToEither {

	public static void main(String[] args) {

		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

		CompletableFuture<String> bus = CompletableFuture.supplyAsync(() -> {
			Tools.threadSleep(3000);
			Tools.printTimeAndThreadInfo("1路公交车来了");
			Tools.threadSleep(200);
			return "1路公交车";
		}).applyToEither(CompletableFuture.supplyAsync(() -> {
			Tools.threadSleep(3000);
			Tools.printTimeAndThreadInfo("2路公交车来了");
			Tools.threadSleep(1000);
			return "2路公交车";
		}), whichOne -> whichOne);

		Tools.printTimeAndThreadInfo(String.format("小明坐车：%s", bus.join()));
		fixedThreadPool.shutdown();

	}
}
