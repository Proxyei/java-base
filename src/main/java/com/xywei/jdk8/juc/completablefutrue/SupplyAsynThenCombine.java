package com.xywei.jdk8.juc.completablefutrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 场景：小明点菜后，厨师和服务员分别同时开始炒菜和煮饭，这时候小明打王者，完成了炒菜和煮饭后，服务器打饭给小明，小明这时候开始吃饭
 * 
 * @author future
 *
 */
public class SupplyAsynThenCombine {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(5);
		Tools.printTimeAndThreadInfo("小明进入餐厅，开始点菜，番茄炒蛋");
		CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
			Tools.threadSleep(1000);
			Tools.printTimeAndThreadInfo("厨师炒菜");
			Tools.threadSleep(1000);
			return "番茄炒蛋!";
		}, pool).thenCombine(CompletableFuture.supplyAsync(() -> {
			Tools.threadSleep(5000);
			Tools.printTimeAndThreadInfo("同时，服务员蒸饭");
			Tools.threadSleep(3000);
			return "蒸饭好了!";
		}, pool), (cook, waiter) -> {
			Tools.printTimeAndThreadInfo("厨师完成炒饭，服务员完成蒸饭，此时，服务员打饭");
			return "cook: " + cook + "waiter: " + waiter;
		});
		
		Tools.printTimeAndThreadInfo("小明在打王者！");
		System.out.println(task1.join());
		pool.shutdown();
	}

}
