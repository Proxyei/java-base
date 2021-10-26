package com.xywei.jdk8.juc.completablefutrue;

import java.util.StringJoiner;

/**
 * 
 * @author future
 *
 */
public class Tools {

	public static void threadSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void printTimeAndThreadInfo(String info) {
		String reuslt = new StringJoiner("\t|\t")
				.add(String.valueOf(System.currentTimeMillis()))
				.add(String.valueOf(Thread.currentThread().getId()))
				.add(String.valueOf(Thread.currentThread().getName()))
				.add(info)
				.toString();
		System.out.println(reuslt);
	}
}
