package com.xywei.jdk8.juc.completablefutrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *描述场景：小明进餐厅吃饭，点菜后打王者，这时候打王者和厨师炒菜是多线程异步进行的。
 *小明：------点菜，打王者-----------小明吃饭
 *厨师：		炒菜--------------完事
 * @author future
 *
 */
public class SupplyAsynThenCompose {
	
	public static void main(String[] args) {
		
		Tools.printTimeAndThreadInfo("小明进餐厅");
		Tools.printTimeAndThreadInfo("小明点菜，番茄炒蛋");
		
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		
		CompletableFuture<String> chef = CompletableFuture.supplyAsync(()->{
			Tools.printTimeAndThreadInfo("厨师炒菜");
//			Tools.threadSleep(5000l);
			return "番茄炒蛋好了";
		}, executorService).thenCompose(cook ->CompletableFuture.supplyAsync(()->{
			Tools.printTimeAndThreadInfo(String.format("厨师%s", cook));
			Tools.printTimeAndThreadInfo("服务员开始打饭");
			return "服务员打饭完毕" ;
		}, executorService));
		

		Tools.printTimeAndThreadInfo("小明在打王者");
		Tools.printTimeAndThreadInfo(String.format("%s，小明开始吃饭", chef.join()));
		executorService.shutdown();
	}

}
