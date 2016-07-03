package org.gradle;

import static rx.Observable.from;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

import rx.functions.Func1;

public class RxJavaSyncTest extends SyncTest {

	@Test
	public void sync() throws InterruptedException, ExecutionException {

		List<Future<String>> futures = from(targets).map(new Func1<String, Future<String>>() {
			public Future<String> call(final String t) {
				return exe.submit(new Callable<String>() {
					public String call() throws Exception {
						return proc(t);
					}
				});
			}
		}).toList().toBlocking().single();
		
		List<String> results = from(futures).map(new Func1<Future<String>, String>() {
			public String call(Future<String> t) {
				try {
					return t.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				return "ERR";
			}
		}).toList().toBlocking().single();

		System.out.println("----" + Thread.currentThread());

		System.out.println(results);
		log(startedAt);
	}

}
