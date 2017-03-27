package com.hjc.herol.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjc.herol.core.GameServer;

public class ThreadViewer {
	public static Logger log = LoggerFactory.getLogger(ThreadViewer.class);
	public static Thread t = null;

	public static void start() {
		if (t != null) {
			return;
		}
		t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (GameServer.shutdown) {
						view();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		}, "ThreadViewer");
		t.setDaemon(true);
		t.start();
	}

	public static void view() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;
		// traverse the ThreadGroup tree to the top
		while (group != null) {
			topGroup = group;
			group = group.getParent();
		}
		// Create a destination array that is about
		// twice as big as needed to be very confident
		// that none are clipped.
		int estimatedSize = topGroup.activeCount() * 2;
		Thread[] slackList = new Thread[estimatedSize];
		// Load the thread references into the oversized
		// array. The actual number of threads loaded
		// is returned.
		int actualSize = topGroup.enumerate(slackList);
		// copy into a list that is the exact size
		if (log != null) {
			// log.info("----dump thread----");
		}
		for (int i = 0; i < actualSize; i++) {
			Thread t = slackList[i];
			if (t == null || t.isDaemon())
				continue;
			if (log != null) {
				// log.info("{}-{}-daemon {}", t.getId(), t.getName(),
				// t.isDaemon());
			}
		}
	}

	public static void interrupt() {
		if (t.isAlive()) {
			t.interrupt();
		}
	}
}
