package com.xywei.jdk8.juc.completablefutrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.IntConsumer;

/**
 * 场景： 小明吃饭完毕之后，到外面等公交车，如果1路到就坐1路，如果2路到就坐2路 <br/>
 * 做的第一辆车发生意外，只能处理异常：重新坐车！
 * 
 * @author future
 *
 */
public class CompletableFutrueApplyToEitherException {

	public static void main(String[] args) {

		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

		CompletableFuture<String> bus = CompletableFuture.supplyAsync(() -> {
			Tools.threadSleep(3000);
			Tools.printTimeAndThreadInfo("1路公交车来了");
			Tools.threadSleep(200);
			return "1路公交车";
		}).exceptionally(e -> {
			return "1路有意外！" + e.getMessage();
		}).applyToEither(CompletableFuture.supplyAsync(() -> {
			Tools.threadSleep(200);
			Tools.printTimeAndThreadInfo("2路公交车来了");
			Tools.threadSleep(3000);
			return "2路公交车";
		}), whichOne -> {
			Tools.printTimeAndThreadInfo("最先到达的车子情况：" + whichOne);
			return whichOne;
		}).exceptionally(e -> {
			Tools.printTimeAndThreadInfo("发生意外: " + e.getMessage());
			return "叫出租车了！";
		});

		Tools.printTimeAndThreadInfo(String.format("小明坐车：%s", bus.join()));
		fixedThreadPool.shutdown();

	}
}
