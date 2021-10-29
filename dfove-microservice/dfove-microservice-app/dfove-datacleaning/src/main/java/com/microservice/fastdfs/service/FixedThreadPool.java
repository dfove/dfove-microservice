/*
 * Copyright (c) 2021 dfove.com Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microservice.fastdfs.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import common.tools.DateUtil;

@Component
public class FixedThreadPool {

	public static Map<String, ExecutorService> map = new ConcurrentHashMap<>();

	private static final String FIXED_THREAD_POOL_DEFAULT_NAME = "fixed";

	public static ExecutorService newFixedThreadPool(int nThreads) {
		return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

	static {
		map.put(FIXED_THREAD_POOL_DEFAULT_NAME, FixedThreadPool.newFixedThreadPool(2));
	}

	public FixedThreadPool() {

	}

	public static void submit(Runnable runnable) {
		FixedThreadPool.map.get(FIXED_THREAD_POOL_DEFAULT_NAME).submit(runnable);
	}

	public static void main(String[] args) {

		System.out.println("开始了" + DateUtil.dateTime() + "-------------");
		for (int i = 0; i < 100; i++) {

			FixedThreadPool.map.get(FIXED_THREAD_POOL_DEFAULT_NAME).submit(new Runnable() {

				@Override
				public void run() {
					System.out.println("--------" + DateUtil.dateTime());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("当前剩下数量：" + ((ThreadPoolExecutor) FixedThreadPool.map.get(FIXED_THREAD_POOL_DEFAULT_NAME)).getQueue().size());
				}
			});
		}

		System.out.println("结束了" + DateUtil.dateTime() + "-------------");
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 等待任务完成才中止线程池
		shutdown();
	}

	// @PostConstruct//初始化方法
	@PreDestroy // bean关闭前调用
	public static void shutdown() {
		FixedThreadPool.map.get(FIXED_THREAD_POOL_DEFAULT_NAME).shutdown();
	}

	public static Map<String, ExecutorService> getMap() {
		return map;
	}

	public static void setMap(Map<String, ExecutorService> map) {
		FixedThreadPool.map = map;
	}
	
	
}
