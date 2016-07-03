package org.gradle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;

public class SyncTest {

	long startedAt = System.currentTimeMillis();

	final ExecutorService exe = Executors.newFixedThreadPool(5);

	List<String> targets = Lists.newArrayList("aaa", "bbb", "ccc", "ddd", "eee");

	String proc(String t) {
		System.out.println("proc.." + t + Thread.currentThread());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return t + "<<<";
	}

	void log(long startedAt) {
		System.out.println("time elapsed = " + (System.currentTimeMillis() - startedAt));
	}
}
