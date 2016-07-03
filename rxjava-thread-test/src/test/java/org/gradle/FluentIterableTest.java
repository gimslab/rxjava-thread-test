package org.gradle;

import static com.google.common.collect.FluentIterable.from;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

public class FluentIterableTest extends SyncTest {

	@Test
	public void sync() throws InterruptedException, ExecutionException {

		ImmutableList<Future<String>> futures = from(targets).transform(new Function<String, Future<String>>() {
			public Future<String> apply(final String input) {
				return exe.submit(new Callable<String>() {
					public String call() throws Exception {
						return proc(input);
					}
				});
			}
		}).toList();
		
		ImmutableList<String> result = from(futures).transform(new Function<Future<String>, String>() {
			public String apply(Future<String> input) {
				try {
					return input.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				return "ERR";
			}
		}).toList();

		System.out.println("----" + Thread.currentThread());

		System.out.println(result);
		log(startedAt);
	}
}
